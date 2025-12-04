package com.gabn.tickets.controllers.docs;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.CreateTicketDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.TicketDTO;
import com.gabn.tickets.dtos.UpdateTicketDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Tickets")
public interface ITicketDocs {

    @Operation(summary = "Creates a ticket")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Ticket created",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<BaseResponseDTO<TicketDTO>> createTicket(@RequestBody CreateTicketDTO createTicketDTO);

    @Operation(summary = "Gets a ticket by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Gets a Ticket",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<BaseResponseDTO<TicketDTO>> getTicketById(Long id) throws EntityNotFoundException;

    @Operation(summary = "Get a ticket by uuid")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Gets a Ticket",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<BaseResponseDTO<TicketDTO>> getTicketByUuid(String uuid) throws EntityNotFoundException;

    @Operation(summary = "Gets a ticket list by status")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Gets a Ticket list",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<PageResponseDTO<List<TicketDTO>>> getTicketByStatus(
        CriteriaDTO criteriaDTO
    ) throws EntityNotFoundException;

    @Operation(summary = "Updates a ticket")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Ticket updated",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<BaseResponseDTO<TicketDTO>> updateTicketById(
        Long id, @RequestBody UpdateTicketDTO updateTicketDTO
    ) throws EntityNotFoundException;

    @Operation(summary = "Deletes a ticket")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Ticket deleted",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = TicketDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "412",
                description = "Precondition failed",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            ),
            @ApiResponse(
                responseCode = "503",
                description = "Service unavailable",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = BaseResponseDTO.class
                        )
                    )
                }
            )
        }
    )
    ResponseEntity<BaseResponseDTO<TicketDTO>> deleteTicketById(Long id) throws EntityNotFoundException;
}
