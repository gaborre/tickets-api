package com.gabn.tickets.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketDomain {
    private Long id;
    private String uuid;
    private String description;
    private Long userId;
    private String status;
}
