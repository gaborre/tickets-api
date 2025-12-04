package com.gabn.tickets.controllers.docs;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.TicketDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User Tickets")
public interface IUserTicketDocs {

    @Operation(summary = "Gets a ticket list by user id and status")
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
    ResponseEntity<PageResponseDTO<List<TicketDTO>>> getTicketByUserIdAndStatus(
        Long userId, CriteriaDTO criteriaDTO
    ) throws EntityNotFoundException;
}
