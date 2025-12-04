package com.gabn.tickets.services;

import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

public interface IUserTicketService {

    Page<TicketDomain> getUserTickets(CriteriaDomain criteriaDomain) throws EntityNotFoundException;
}
