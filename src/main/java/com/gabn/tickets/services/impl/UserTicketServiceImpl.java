package com.gabn.tickets.services.impl;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.Ticket;
import com.gabn.tickets.repositories.TicketRepository;
import com.gabn.tickets.services.IUserTicketService;
import com.gabn.tickets.specifications.TicketSpecification;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gabn.tickets.constants.CachingConstants.TICKETS_API_CACHE_TAG;
import static com.gabn.tickets.constants.CachingConstants.USER_TICKETS_KEY;
import static com.gabn.tickets.constants.TicketValidationMessage.TICKET_ENTITY_TYPE;
import static com.gabn.tickets.constants.TicketValidationMessage.TICKET_NOT_FOUND;

@Service
public class UserTicketServiceImpl implements IUserTicketService {
    private final TicketRepository ticketRepository;

    @Getter
    @Value("${cache.tickets.enabled}")
    private boolean cacheEnabled;

    public UserTicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Cacheable(
        cacheNames = TICKETS_API_CACHE_TAG,
        key = USER_TICKETS_KEY,
        condition = "#root.target.isCacheEnabled()",
        unless = "#result == null || #result.isEmpty()"
    )
    @Transactional(readOnly = true)
    @Override
    public Page<TicketDomain> getUserTickets(CriteriaDomain criteriaDomain) throws EntityNotFoundException {
        Page<Ticket> ticketPage = ticketRepository.findAll(
            TicketSpecification.getFilterByUserIdAndStatus(criteriaDomain.getUserId(), criteriaDomain.getStatus()),
            PageRequest.of(criteriaDomain.getPage() - 1, criteriaDomain.getLimit())
        );
        if (ticketPage.isEmpty()) {
            throw new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND);
        }

        return ticketPage
            .map((Ticket ticket) -> ModelMapperConfig.getInstance().map(ticket, TicketDomain.class));
    }
}
