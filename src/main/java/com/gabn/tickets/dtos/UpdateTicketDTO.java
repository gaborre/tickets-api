package com.gabn.tickets.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static com.gabn.tickets.constants.TicketValidationMessage.DESCRIPTION_VALID;
import static com.gabn.tickets.constants.TicketValidationMessage.STATUS_VALID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 980731677701324337L;

    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = DESCRIPTION_VALID)
    private String description;

    @NotBlank(message = STATUS_VALID)
    private String status;
}