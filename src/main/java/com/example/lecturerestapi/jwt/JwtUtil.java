package com.example.lecturerestapi.jwt;

import com.example.lecturerestapi.admin.entity.AdminRole;
import com.example.lecturerestapi.exception.ErrorType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";  // 사용자 권한 값의 Key
    public static final String BEARER_PREFIX = "Bearer ";   // Token 식별자
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 토큰을 생성하는 메소드.
     * @param email 사용자 식별자값
     * @param adminRole 관리자 권한
     * @return 생성된 토큰
     */
    public String createToken(String email, AdminRole adminRole) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, adminRole)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * JWT 를 Cookie 에 저장하는 메소드
     * @param token Cookie 에 저장할 토큰
     * @param res HTTP 응답 객체
     */
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException();
        }
    }

    /**
     * JWT 토큰에서 Bearer 접두사를 제거하고 실제 토큰 값을 반환하는 메소드.
     * @param tokenValue Bearer 접두사를 포함한 JWT 토큰
     * @return Bearer 접두사를 제거한 실제 JWT 토큰
     * @throws NullPointerException Bearer 접두사가 없거나 유효하지 않은 토큰 값일 경우 예외 발생
     */
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new NullPointerException(ErrorType.NOT_FOUND_TOKEN.getMessage());
    }

    /**
     * 주어진 JWT 토큰의 유효성을 검증하는 메소드.
     * 이 메소드는 JWT 토큰을 파싱하고 서명을 검증하여 토큰이 유효한지 확인한다.
     * 만약 토큰이 유효하지 않다면 false 를 반환한다.
     * @param token 검증할 JWT 토큰
     * @return 토큰이 유효한 경우 true, 그렇지 않은 경우 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            return false;
        }
    }

    /**
     * JWT 토큰에서 사용자 정보를 가져오는 메소드.
     * 이 메소드는 JWT 토큰을 파싱하고 그 안에 포함된 claims 를 반환한다.
     * @param token 사용자 정보를 포함하고 있는 JWT
     * @return JWT 토큰에서 추출한 사용자 정보가 담긴 Claims
     */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * HttpServletRequest 에서 JWT 토큰이 담긴 쿠키 값을 가져오는 메소드.
     * 쿠키 값이 URL 인코딩 되어 있으므로 이를 디코딩하여 반환한다.
     * @param req JWT 토큰을 포함하고 있는 HttpServletRequest 객체
     * @return 쿠키에서 추출한 JWT 토큰 반환. 해당 쿠키가 없거나 디코딩 중 에러가 발생한 경우 null 반환
     */
    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
