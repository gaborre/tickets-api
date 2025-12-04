package com.gabn.tickets.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5311640724226260742L;

    @JsonProperty("page")
    private int page;

    @JsonProperty("perPage")
    private int perPage;

    @JsonProperty("lastPage")
    private int lastPage;

    @JsonProperty("total")
    private Long total;
}
