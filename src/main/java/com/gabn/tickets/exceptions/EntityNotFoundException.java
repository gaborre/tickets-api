package com.gabn.tickets.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 7307435042738331723L;

    private final String type;

    public EntityNotFoundException(String type, String message) {
        super(message);
        this.type = type;
    }
}
