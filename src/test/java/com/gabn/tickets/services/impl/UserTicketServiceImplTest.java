package com.gabn.tickets.services.impl;

import java.util.List;

import org.instancio.Instancio;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.enums.TicketStatusEnum;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.Ticket;
import com.gabn.tickets.repositories.TicketRepository;

@ExtendWith(MockitoExtension.class)
class UserTicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private UserTicketServiceImpl userTicketService;

    @Test
    void test_GetUserTickets_ShouldReturn_Page_When_HasData() throws EntityNotFoundException {
        Page<Ticket> ticketPage = new PageImpl<>(List.of(Instancio.create(Ticket.class)));

        when(ticketRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(ticketPage);

        CriteriaDomain criteriaDomain = CriteriaDomain.builder()
            .userId(1L)
            .status(TicketStatusEnum.OPENED.name())
            .page(1)
            .limit(5)
            .build();

        assertAll(
            () -> userTicketService.getUserTickets(criteriaDomain)
        );

        verify(ticketRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void test_GetUserTickets_ShouldThrow_EntityNotFoundException_When_EmptyPage() {
        Page<Ticket> ticketPage = new PageImpl<>(List.of());

        when(ticketRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(ticketPage);

        CriteriaDomain criteriaDomain = CriteriaDomain.builder()
            .userId(1L)
            .status(TicketStatusEnum.OPENED.name())
            .page(1)
            .limit(5)
            .build();

        assertThrows(
            EntityNotFoundException.class,
            () -> userTicketService.getUserTickets(criteriaDomain)
        );

        verify(ticketRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}


