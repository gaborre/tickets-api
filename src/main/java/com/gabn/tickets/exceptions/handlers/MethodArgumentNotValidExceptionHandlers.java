package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.gabn.tickets.constants.ErrorTypeConstants.ARGUMENTS_NOT_VALID_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@RestControllerAdvice
@ResponseBody
public class MethodArgumentNotValidExceptionHandlers implements BaseHandlerException<MethodArgumentNotValidException> {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponseDTO<Object> handleException(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult().getFieldErrors()
            .stream()
            .map((FieldError fieldError) -> fieldError.getField().concat(":")
                .concat(Optional.ofNullable(fieldError.getDefaultMessage()).orElse(StringUtils.EMPTY)))
            .collect(Collectors.joining(","));

        return BaseResponseDTO.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .error(buildErrorDTO(ARGUMENTS_NOT_VALID_TYPE, errors))
            .build();
    }
}
