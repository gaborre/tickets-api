package com.gabn.tickets.controllers;

import com.gabn.tickets.configs.ModelMapperConfig;
import com.gabn.tickets.controllers.docs.ITicketDocs;
import com.gabn.tickets.domains.CreateTicketDomain;
import com.gabn.tickets.domains.CriteriaDomain;
import com.gabn.tickets.domains.TicketDomain;
import com.gabn.tickets.domains.UpdateTicketDomain;
import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.CreateTicketDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.TicketDTO;
import com.gabn.tickets.dtos.UpdateTicketDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import com.gabn.tickets.services.ITicketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gabn.tickets.mappers.ResponseDTOMapper.buildPaginationDTO;
import static com.gabn.tickets.mappers.ResponseDTOMapper.toBaseResponseDTO;
import static com.gabn.tickets.mappers.ResponseDTOMapper.toPageResponseDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(
    path = "/api/v1/tickets",
    produces = APPLICATION_JSON_VALUE
)
public class TicketController implements ITicketDocs {
    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(
        consumes = APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<BaseResponseDTO<TicketDTO>> createTicket(
        @Valid @RequestBody CreateTicketDTO createTicketDTO
    ) {
        TicketDomain ticketDomain = ticketService.createTicket(
            ModelMapperConfig.getInstance().map(createTicketDTO, CreateTicketDomain.class)
        );
        return ResponseEntity.status(HttpStatus.CREATED.value())
            .body(
                toBaseResponseDTO(
                    HttpStatus.CREATED, ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class)
                )
            );
    }

    @GetMapping(
        path = "/{id}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<TicketDTO>> getTicketById(
        @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        TicketDomain ticketDomain = ticketService.getTicketById(id);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class))
            );
    }

    @GetMapping(
        path = "/uuid/{uuid}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<TicketDTO>> getTicketByUuid(
        @PathVariable(name = "uuid") String uuid
    ) throws EntityNotFoundException {
        TicketDomain ticketDomain = ticketService.getTicketByUuid(uuid);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class))
            );
    }

    @GetMapping
    @Override
    public ResponseEntity<PageResponseDTO<List<TicketDTO>>> getTicketByStatus(
        @Valid CriteriaDTO criteriaDTO
    ) throws EntityNotFoundException {
        Page<TicketDomain> ticketDomainPage = ticketService.getTicketByStatus(
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

    @PutMapping(
        path = "/{id}",
        consumes = APPLICATION_JSON_VALUE
    )
    @Override
    public ResponseEntity<BaseResponseDTO<TicketDTO>> updateTicketById(
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody UpdateTicketDTO updateTicketDTO
    ) throws EntityNotFoundException {
        updateTicketDTO.setId(id);
        TicketDomain ticketDomain = ticketService.updateTicketById(
            ModelMapperConfig.getInstance().map(updateTicketDTO, UpdateTicketDomain.class)
        );
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class))
            );
    }

    @DeleteMapping(
        path = "/{id}"
    )
    @Override
    public ResponseEntity<BaseResponseDTO<TicketDTO>> deleteTicketById(
        @PathVariable(name = "id") Long id
    ) throws EntityNotFoundException {
        TicketDomain ticketDomain = ticketService.deleteTicketById(id);
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                toBaseResponseDTO(HttpStatus.OK, ModelMapperConfig.getInstance().map(ticketDomain, TicketDTO.class))
            );
    }
}
