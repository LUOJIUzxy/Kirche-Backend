package com.ssq.invoice.constant;

import lombok.Getter;

import static com.ssq.invoice.constant.Authority.*;

@Getter
public enum Role {
    ROLE_GROUP_ADMIN(GROUP_ADMIN_AUTHORITIES),
    ROLE_GROUP_READ(GROUP_READ_AUTHORITIES),
    ROLE_COMPANY_ADMIN(COMPANY_ADMIN_AUTHORITIES),

    ROLE_COMPANY_READ(COMPANY_READ_AUTHORITIES),

    ROLE_NORMAL_USER(NORMAL_USER_AUTHORITIES);


    private String[] authorities;

    // constructor
    Role(String... authorities) {
        this.authorities = authorities;
    }

}
