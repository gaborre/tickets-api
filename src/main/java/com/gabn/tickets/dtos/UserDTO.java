package com.gabn.tickets.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static com.gabn.tickets.constants.UserValidationMessage.FIRST_NAME_MAX_LENGTH;
import static com.gabn.tickets.constants.UserValidationMessage.FIRST_NAME_VALID;
import static com.gabn.tickets.constants.UserValidationMessage.LAST_NAME_MAX_LENGTH;
import static com.gabn.tickets.constants.UserValidationMessage.LAST_NAME_VALID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -808459355952505903L;

    @Schema(hidden = true)
    private Long id;

    @Schema(hidden = true)
    private String uuid;

    @NotBlank(message = FIRST_NAME_VALID)
    @Size(min = 3, max = 50, message = FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @NotBlank(message = LAST_NAME_VALID)
    @Size(min = 3, max = 50, message = LAST_NAME_MAX_LENGTH)
    private String lastName;

    @Schema(hidden = true)
    private String createdOn;

    @Schema(hidden = true)
    @JsonInclude(Include.NON_NULL)
    private String updatedOn;
}
