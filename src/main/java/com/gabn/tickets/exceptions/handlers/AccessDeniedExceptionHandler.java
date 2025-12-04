package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.gabn.tickets.constants.ErrorTypeConstants.FORBIDDEN_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;


@RestControllerAdvice
public class AccessDeniedExceptionHandler implements BaseHandlerException<AccessDeniedException> {

    @Override
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponseDTO<Object> handleException(AccessDeniedException exception) {
        return BaseResponseDTO
            .builder()
            .code(HttpStatus.FORBIDDEN.value())
            .message(HttpStatus.FORBIDDEN.getReasonPhrase())
            .error(buildErrorDTO(FORBIDDEN_TYPE, exception.getMessage()))
            .build();
    }
}
