package com.gabn.tickets.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> extends BaseResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 6100679791611312855L;

    private PaginationDTO pagination;
}
