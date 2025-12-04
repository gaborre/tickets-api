package com.gabn.tickets.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 252174865931762875L;

    @Schema(hidden = true)
    private Long id;

    @Schema(hidden = true)
    private String uuid;

    @NotBlank
    private String description;
    private UserDTO user;
    private String status;

    @Schema(hidden = true)
    private String createdOn;

    @Schema(hidden = true)
    @JsonInclude(Include.NON_NULL)
    private String updatedOn;
}
