package com.gabn.tickets.services.impl;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UpdateTicketDomain;
import com.gabn.tickets.enums.TicketStatusEnum;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.models.Ticket;
import com.gabn.tickets.repositories.TicketRepository;
import com.gabn.tickets.services.ITicketService;
import com.gabn.tickets.specifications.TicketSpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gabn.tickets.constants.CachingConstants.TICKETS_API_CACHE_TAG;
import static com.gabn.tickets.constants.TicketValidationMessage.TICKET_ENTITY_TYPE;
import static com.gabn.tickets.constants.TicketValidationMessage.TICKET_NOT_FOUND;
import static com.gabn.tickets.mappers.TicketMapper.mapToTicketDomain;

@Service
public class TicketServiceImpl implements ITicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @CacheEvict(
        cacheNames = TICKETS_API_CACHE_TAG,
        allEntries = true
    )
    @Transactional
    @Override
    public TicketDomain createTicket(CreateTicketDomain createTicketDomain) {
        return ModelMapperConfig.getInstance().map(
            ticketRepository.save(
                ModelMapperConfig.getInstance().map(
                    mapToTicketDomain(createTicketDomain), Ticket.class)
            ),
            TicketDomain.class
        );
    }

    @Transactional(readOnly = true)
    @Override
    public TicketDomain getTicketById(Long id) throws EntityNotFoundException {
        return ModelMapperConfig.getInstance().map(
            ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND)),
            TicketDomain.class
        );
    }

    @Transactional(readOnly = true)
    @Override
    public TicketDomain getTicketByUuid(String uuid) throws EntityNotFoundException {
        return ModelMapperConfig.getInstance().map(
            ticketRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND)),
            TicketDomain.class
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TicketDomain> getTicketByStatus(CriteriaDomain criteriaDomain) throws EntityNotFoundException {
        Page<Ticket> ticketPage = ticketRepository.findAll(
            TicketSpecification.getFilterByStatus(criteriaDomain.getStatus()),
            PageRequest.of(criteriaDomain.getPage() - 1, criteriaDomain.getLimit())
        );
        if (ticketPage.isEmpty()) {
            throw new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND);
        }

        return ticketPage
            .map((Ticket ticket) -> ModelMapperConfig.getInstance().map(ticket, TicketDomain.class));
    }

    @CacheEvict(
        cacheNames = TICKETS_API_CACHE_TAG,
        allEntries = true
    )
    @Transactional
    @Override
    public TicketDomain updateTicketById(UpdateTicketDomain updateTicketDomain) throws EntityNotFoundException {
        Ticket existingTicket = ticketRepository.findById(updateTicketDomain.getId())
            .orElseThrow(() -> new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND));

        existingTicket.setDescription(updateTicketDomain.getDescription());
        existingTicket.setStatus(TicketStatusEnum.fromString(updateTicketDomain.getStatus()));

        return ModelMapperConfig.getInstance().map(ticketRepository.save(existingTicket), TicketDomain.class);
    }

    @CacheEvict(
        cacheNames = TICKETS_API_CACHE_TAG,
        allEntries = true
    )
    @Transactional
    @Override
    public TicketDomain deleteTicketById(Long id) throws EntityNotFoundException {
        Ticket existingTicket = ticketRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(TICKET_ENTITY_TYPE, TICKET_NOT_FOUND));

        ticketRepository.deleteById(id);

        return ModelMapperConfig.getInstance().map(existingTicket, TicketDomain.class);
    }
}
