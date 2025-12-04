package com.gabn.tickets.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6952732810591287424L;

    private String status;
    private Long userId;

    @Min(value = 1)
    private Integer page = 1;

    @Min(value = 5)
    @Max(value = 20)
    private Integer limit = 5;
}
