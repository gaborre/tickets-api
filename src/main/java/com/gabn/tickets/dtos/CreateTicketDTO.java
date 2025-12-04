package com.gabn.tickets.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static com.gabn.tickets.constants.TicketValidationMessage.DESCRIPTION_VALID;
import static com.gabn.tickets.constants.TicketValidationMessage.STATUS_VALID;
import static com.gabn.tickets.constants.TicketValidationMessage.USER_VALID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3163709077574979070L;

    @NotBlank(message = DESCRIPTION_VALID)
    private String description;

    @NotNull(message = USER_VALID)
    private Long userId;

    @NotBlank(message = STATUS_VALID)
    private String status;
}
