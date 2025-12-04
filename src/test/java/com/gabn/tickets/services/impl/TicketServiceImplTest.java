package com.gabn.tickets.services.impl;

import java.util.List;
import java.util.Optional;

import org.instancio.Instancio;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UpdateTicketDomain;
import com.gabn.tickets.enums.TicketStatusEnum;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.Ticket;
import com.gabn.tickets.repositories.TicketRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void test_CreateTicket_ShouldBe_Created_When_DataIsValid() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(Instancio.create(Ticket.class));

        CreateTicketDomain createTicketDomain = Instancio.create(CreateTicketDomain.class);

        assertAll(
            () -> ticketService.createTicket(createTicketDomain)
        );

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void test_GetTicketById_ShouldReturn_Ticket_When_Exists() throws EntityNotFoundException {
        Ticket ticket = Instancio.create(Ticket.class);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        TicketDomain result = ticketService.getTicketById(1L);

        assertAll(
            () -> assertEquals(ticket.getId(), result.getId())
        );

        verify(ticketRepository, times(1)).findById(anyLong());
    }

    @Test
    void test_GetTicketById_ShouldThrow_EntityNotFoundException_When_NotExists() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> ticketService.getTicketById(1L)
        );

        verify(ticketRepository, times(1)).findById(anyLong());
    }

    @Test
    void test_GetTicketByUuid_ShouldReturn_Ticket_When_Exists() throws EntityNotFoundException {
        Ticket ticket = Instancio.create(Ticket.class);

        when(ticketRepository.findByUuid(anyString())).thenReturn(Optional.of(ticket));

        TicketDomain result = ticketService.getTicketByUuid("uuid-1234");

        assertAll(
            () -> assertEquals(ticket.getUuid(), result.getUuid())
        );

        verify(ticketRepository, times(1)).findByUuid(anyString());
    }

    @Test
    void test_GetTicketByUuid_ShouldThrow_EntityNotFoundException_When_NotExists() {
        when(ticketRepository.findByUuid(anyString())).thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> ticketService.getTicketByUuid("uuid-1234")
        );

        verify(ticketRepository, times(1)).findByUuid(anyString());
    }

    @Test
    void test_GetTicketByStatus_ShouldReturn_Page_When_HasData() throws EntityNotFoundException {
        Page<Ticket> ticketPage = new PageImpl<>(List.of(Instancio.create(Ticket.class)));

        when(ticketRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(ticketPage);

        CriteriaDomain criteriaDomain = CriteriaDomain.builder()
            .status(TicketStatusEnum.OPENED.name())
            .page(1)
            .limit(5)
            .build();

        assertAll(
            () -> ticketService.getTicketByStatus(criteriaDomain)
        );

        verify(ticketRepository, times(1))
            .findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void test_GetTicketByStatus_ShouldThrow_EntityNotFoundException_When_EmptyPage() {
        Page<Ticket> ticketPage = new PageImpl<>(List.of());

        when(ticketRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(ticketPage);

        CriteriaDomain criteriaDomain = CriteriaDomain.builder()
            .status(TicketStatusEnum.OPENED.name())
            .page(1)
            .limit(5)
            .build();

        assertThrows(
            EntityNotFoundException.class,
            () -> ticketService.getTicketByStatus(criteriaDomain)
        );

        verify(ticketRepository, times(1))
            .findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void test_UpdateTicketById_ShouldBe_Ok_When_TicketExists() throws EntityNotFoundException {
        Ticket existingTicket = Instancio.create(Ticket.class);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(existingTicket));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        UpdateTicketDomain updateTicketDomain = Instancio.create(UpdateTicketDomain.class);
        updateTicketDomain.setId(1L);

        assertAll(
            () -> ticketService.updateTicketById(updateTicketDomain)
        );

        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void test_UpdateTicketById_ShouldThrow_EntityNotFoundException_When_TicketDoesNotExist() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        UpdateTicketDomain updateTicketDomain = Instancio.create(UpdateTicketDomain.class);
        updateTicketDomain.setId(1L);

        assertThrows(
            EntityNotFoundException.class,
            () -> ticketService.updateTicketById(updateTicketDomain)
        );

        verify(ticketRepository, times(1)).findById(anyLong());
    }

    @Test
    void test_DeleteTicketById_ShouldReturn_Ticket_When_Exists() throws EntityNotFoundException {
        Ticket existingTicket = Instancio.create(Ticket.class);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(existingTicket));

        TicketDomain result = ticketService.deleteTicketById(1L);

        assertAll(
            () -> assertEquals(existingTicket.getId(), result.getId())
        );

        verify(ticketRepository, times(1)).findById(anyLong());
        verify(ticketRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void test_DeleteTicketById_ShouldThrow_EntityNotFoundException_When_TicketDoesNotExist() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
            EntityNotFoundException.class,
            () -> ticketService.deleteTicketById(1L)
        );

        verify(ticketRepository, times(1)).findById(anyLong());
    }
}


