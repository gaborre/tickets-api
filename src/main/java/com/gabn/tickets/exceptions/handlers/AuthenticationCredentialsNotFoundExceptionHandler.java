package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.gabn.tickets.constants.ErrorTypeConstants.UNAUTHORIZED_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@RestControllerAdvice
@ResponseBody
public class AuthenticationCredentialsNotFoundExceptionHandler
    implements BaseHandlerException<AuthenticationCredentialsNotFoundException> {

    @Override
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public BaseResponseDTO<Object> handleException(
        AuthenticationCredentialsNotFoundException exception
    ) {
        return BaseResponseDTO
            .builder()
            .code(HttpStatus.UNAUTHORIZED.value())
            .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .error(buildErrorDTO(UNAUTHORIZED_TYPE, exception.getMessage()))
            .build();
    }
}
