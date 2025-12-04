package com.gabn.tickets.services;

import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UpdateTicketDomain;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

public interface ITicketService {

    TicketDomain createTicket(CreateTicketDomain createTicketDomain);

    TicketDomain getTicketById(Long id) throws EntityNotFoundException;

    TicketDomain getTicketByUuid(String uuid) throws EntityNotFoundException;

    Page<TicketDomain> getTicketByStatus(CriteriaDomain criteriaDomain) throws EntityNotFoundException;

    TicketDomain updateTicketById(UpdateTicketDomain updateTicketDomain) throws EntityNotFoundException;

    TicketDomain deleteTicketById(Long id) throws EntityNotFoundException;
}
