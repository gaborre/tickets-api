package com.gabn.tickets.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDomain {
    private String status;
    private Long userId;
    private Integer page;
    private Integer limit;
}
