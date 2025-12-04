package com.gabn.tickets.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.gabn.tickets.constants.JwtConstants.JWT_PREFIX_SIZE;
import static com.gabn.tickets.constants.JwtConstants.SPRING_SECURITY_PASSWORD;
import static com.gabn.tickets.constants.JwtConstants.SPRING_SECURITY_USERNAME;

@Slf4j
@Component
public class SSORequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public SSORequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization Header: {}", requestTokenHeader);

        if (jwtUtil.isJwtToken(requestTokenHeader)) {
            final String jwtToken = requestTokenHeader.substring(JWT_PREFIX_SIZE);
            log.info("jwtToken: {}", jwtToken);
            try {
                if (
                    Objects.isNull(SecurityContextHolder.getContext().getAuthentication())
                        && jwtUtil.validateToken(jwtToken)
                ) {
                    final UsernamePasswordAuthenticationToken authenticateUser = authenticateUser();
                    authenticateUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticateUser);
                }
            } catch (IllegalArgumentException | ExpiredJwtException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticateUser() {
        final UserDetails userDetails = new SessionUser(SPRING_SECURITY_USERNAME, SPRING_SECURITY_PASSWORD);

        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }
}
