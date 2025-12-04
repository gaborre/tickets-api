package com.gabn.tickets.enums;

import java.util.Arrays;

public enum TicketStatusEnum {
    OPENED,
    CLOSED;

    public static TicketStatusEnum fromString(String status) {
        return Arrays.stream(TicketStatusEnum.values())
            .filter(ticketStatus -> ticketStatus.name().equalsIgnoreCase(status))
            .findFirst()
            .orElse(OPENED);
    }
}
