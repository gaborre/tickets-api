package com.gabn.tickets.domains;

import com.gabn.tickets.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDomain {
    private Long id;
    private String uuid;
    private String description;
    private UserDomain user;
    private TicketStatusEnum status;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
