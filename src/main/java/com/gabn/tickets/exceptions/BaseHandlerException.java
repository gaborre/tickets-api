package com.gabn.tickets.exceptions;


import com.gabn.tickets.dtos.BaseResponseDTO;

public interface BaseHandlerException<T extends Throwable> {

    BaseResponseDTO<Object> handleException(T exception);
}
