package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.gabn.tickets.constants.ErrorTypeConstants.UNAUTHORIZED_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@RestControllerAdvice
@ResponseBody
public class AuthenticationExceptionHandler implements BaseHandlerException<AuthenticationException> {

    @Override
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseResponseDTO<Object> handleException(AuthenticationException exception) {
        return BaseResponseDTO
            .builder()
            .code(HttpStatus.UNAUTHORIZED.value())
            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .error(buildErrorDTO(UNAUTHORIZED_TYPE, exception.getMessage()))
            .build();
    }
}
