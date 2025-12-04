package com.gabn.tickets.exceptions.handlers;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.exceptions.BaseHandlerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import static com.gabn.tickets.constants.ErrorTypeConstants.WEB_EXCHANGE_BIND_TYPE;
import static com.gabn.tickets.mappers.ResponseDTOMapper.buildErrorDTO;

@Slf4j
@RestControllerAdvice
@ResponseBody
public class WebExchangeBindExceptionHandler implements BaseHandlerException<WebExchangeBindException> {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public BaseResponseDTO<Object> handleException(WebExchangeBindException exception) {
        log.info("WebExchangeBindException errors: {}", exception.getFieldErrors());

        return BaseResponseDTO.builder()
            .code(exception.getStatusCode().value())
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .error(buildErrorDTO(WEB_EXCHANGE_BIND_TYPE, exception.getMessage()))
            .build();
    }
}
