package com.gabn.tickets.mappers;

import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UserDomain;
import com.gabn.tickets.enums.TicketStatusEnum;

public final class TicketMapper {

    private TicketMapper() {}

    public static TicketDomain mapToTicketDomain(CreateTicketDomain createTicketDomain) {
        return TicketDomain.builder()
            .description(createTicketDomain.getDescription())
            .user(
                UserDomain.builder()
                    .id(createTicketDomain.getUserId())
                    .build()
            )
            .status(TicketStatusEnum.fromString(createTicketDomain.getStatus()))
            .build();
    }
}
