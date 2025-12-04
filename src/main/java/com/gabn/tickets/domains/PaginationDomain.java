package com.gabn.tickets.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDomain {
    private int page;
    private int perPage;
    private int lastPage;
    private Long total;
}
