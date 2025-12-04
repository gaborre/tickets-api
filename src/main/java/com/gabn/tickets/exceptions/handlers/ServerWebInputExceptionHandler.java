package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import static com.gabn.tickets.constants.ErrorTypeConstants.SERVER_WEB_INPUT_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@RestControllerAdvice
@ResponseBody
public class ServerWebInputExceptionHandler implements BaseHandlerException<ServerWebInputException> {

    @Override
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(ServerWebInputException.class)
    public BaseResponseDTO<Object> handleException(ServerWebInputException exception) {
        return BaseResponseDTO
            .builder()
            .code(HttpStatus.PRECONDITION_FAILED.value())
            .message(HttpStatus.PRECONDITION_FAILED.getReasonPhrase())
            .error(buildErrorDTO(SERVER_WEB_INPUT_TYPE, exception.getMessage()))
            .build();
    }
}
