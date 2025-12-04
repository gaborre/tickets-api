package com.gabn.tickets.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2306263327501565171L;

    @NotBlank(message = "Subject must not be blank")
    private String subject;

    @NotBlank(message = "Audience must not be blank")
    private String audience;
}
