package com.gabn.tickets.controllers.docs;

import com.gabn.tickets.dtos.BaseResponseDTO;
import com.gabn.tickets.dtos.CreateTicketDTO;
import com.gabn.tickets.dtos.CriteriaDTO;
import com.gabn.tickets.dtos.PageResponseDTO;
import com.gabn.tickets.dtos.TicketDTO;
import com.gabn.tickets.dtos.UserDTO;
import com.gabn.tickets.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Users")
public interface IUserDocs {

    @Operation(summary = "Creates an user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "User created",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = UserDTO.class
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
    ResponseEntity<BaseResponseDTO<UserDTO>> createUser(@RequestBody UserDTO userDTO);

    @Operation(summary = "Gets an user by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Gets an user",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = UserDTO.class
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
    ResponseEntity<BaseResponseDTO<UserDTO>> getUserById(Long id) throws EntityNotFoundException;

    @Operation(summary = "Get an user by uuid")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "User returned",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = UserDTO.class
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
    ResponseEntity<BaseResponseDTO<UserDTO>> getUserByUuid(String uuid) throws EntityNotFoundException;

    @Operation(summary = "Gets an user list")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "User list returned",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = UserDTO.class
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
    ResponseEntity<PageResponseDTO<List<UserDTO>>> getUsers(CriteriaDTO criteriaDTO) throws EntityNotFoundException;

    @Operation(summary = "Updates an user")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "User updated",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            implementation = UserDTO.class
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
    ResponseEntity<BaseResponseDTO<UserDTO>> updateUserById(
        Long id, @RequestBody UserDTO userDTO
    ) throws EntityNotFoundException;
}
