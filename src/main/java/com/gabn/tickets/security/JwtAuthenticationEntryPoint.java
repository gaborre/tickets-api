package com.gabn.tickets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.BaseResponseDTO.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serial;
import java.io.Serializable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Serial
    private static final long serialVersionUID = 7454060110785033734L;

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        BaseResponseDTO<String> responseResult = BaseResponseDTO.<String>builder()
            .code(HttpStatus.UNAUTHORIZED.value())
            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .error(
                ErrorDTO
                    .builder()
                    .type(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                    .description(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                    .build()
            )
            .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, responseResult);
        out.flush();
    }
}
