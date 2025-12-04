package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@RestControllerAdvice
@ResponseBody
public class EntityNotFoundExceptionHandler implements BaseHandlerException<EntityNotFoundException> {

    @Override
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public BaseResponseDTO<Object> handleException(EntityNotFoundException exception) {
        return BaseResponseDTO.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(HttpStatus.NOT_FOUND.getReasonPhrase())
            .error(buildErrorDTO(exception.getType(), exception.getMessage()))
            .build();
    }
}
