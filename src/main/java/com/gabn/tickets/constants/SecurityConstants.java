package com.gabn.tickets.constants;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

public interface SecurityConstants {

    String[] AUTH_WHITE_LIST = {
        "/api-docs/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/actuator",
        "/actuator/health",
        "/actuator/refresh",
        "/h2-console/**",
        "/login/**",
        "/api/v1/admin/get-valid-jwt"
    };
    List<String> ALLOWED_METHODS = Arrays.asList(
        HttpMethod.HEAD.name(), HttpMethod.GET.name(), HttpMethod.POST.name(),
        HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.PATCH.name()
    );
}
