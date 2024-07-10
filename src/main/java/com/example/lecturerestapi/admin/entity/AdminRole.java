package com.example.lecturerestapi.admin.entity;

import lombok.Getter;

@Getter
public enum AdminRole {
    MANAGER(Authority.MANAGER),
    STAFF(Authority.STAFF);

    private final String authority;

    AdminRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String STAFF = "ROLE_STAFF";
    }
}
