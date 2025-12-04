package com.gabn.tickets.constants;

public interface JwtConstants {
    String BEARER_START_STRING = "Bearer ";
    Long VALIDITY_TIME_IN_MILLISECONDS = 5 * 60 * 1000L; // 5 minutes
    int JWT_PREFIX_SIZE = 7;
    String SPRING_SECURITY_USERNAME = "username";
    String SPRING_SECURITY_PASSWORD = "1234";
}
