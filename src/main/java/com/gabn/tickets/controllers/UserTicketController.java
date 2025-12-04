package com.gabn.tickets.controllers;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.controllers.docs.IUserTicketDocs;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.TicketDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.services.IUserTicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gabn.tickets.mappers.ResponseDTOMapper.buildPaginationDTO;
import static com.gabn.tickets.mappers.ResponseDTOMapper.toPageResponseDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
    path = "/api/v1/users",
    produces = APPLICATION_JSON_VALUE
)
public class UserTicketController implements IUserTicketDocs {
    private final IUserTicketService userTicketService;

    public UserTicketController(IUserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @GetMapping(
        path = "/{userId}/tickets"
    )
    @Override
    public ResponseEntity<PageResponseDTO<List<TicketDTO>>> getTicketByUserIdAndStatus(
        @PathVariable(name = "userId") Long userId,
        @Valid CriteriaDTO criteriaDTO
    ) throws EntityNotFoundException {
        criteriaDTO.setUserId(userId);

        Page<TicketDomain> ticketDomainPage = userTicketService.getUserTickets(
            ModelMapperConfig.getInstance().map(criteriaDTO, CriteriaDomain.class)
        );
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toPageResponseDTO(
                    HttpStatus.OK,
                    ticketDomainPage.getContent()
                        .stream()
                        .map((TicketDomain ticketDomain) ->
                            ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class)
                        )
                        .toList(),
                    buildPaginationDTO(criteriaDTO, ticketDomainPage)
                )
            );
    }
}
