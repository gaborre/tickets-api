package com.gabn.tickets.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 4857536787648892904L;

    private Integer code;
    private String message;

    @JsonInclude(Include.NON_NULL)
    private T data;

    @JsonInclude(Include.NON_NULL)
    private ErrorDTO error;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(Include.NON_NULL)
    public static class ErrorDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 7098515603304356831L;

        @JsonInclude(Include.NON_NULL)
        private String type;

        @JsonInclude(Include.NON_NULL)
        private String description;
    }
}
