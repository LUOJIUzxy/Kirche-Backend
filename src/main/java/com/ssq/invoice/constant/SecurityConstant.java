package com.ssq.invoice.constant;

public class SecurityConstant {
    public static final String JWT_ISSUER = "ssq_invoice";

    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds, depend on company
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token Cannot be verified";
    public static final String WLS_LLC = "WLS, LLC"; // issued by which company (Wls, LLC)
    public static final String WLS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    public static final String[] PUBLIC_URLS = {"/auth/**"}; // allowed pages without authentication
    //public static final String[] PUBLIC_URLS = {"**"};

    // TODO: 22.05.2022 save the activation code in database 
    public static final String ACTIVATION_CODE = "12345";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String BASIC = "Basic";

    public static final String TOKEN_EXPIRED = "Failure at verify JWT Signature due to expired token";
    public static final String TOKEN_NOT_EXIST = "JWT does not exist";

    public static final String UNMATCHED_SIGNATURE = "Unmatched Signature";


}
