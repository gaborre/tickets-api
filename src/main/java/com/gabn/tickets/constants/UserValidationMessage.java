package com.gabn.tickets.constants;

public interface UserValidationMessage {
    String FIRST_NAME_VALID = "Los nombres son requeridos";
    String LAST_NAME_VALID = "Los apellidos son requeridos";
    String FIRST_NAME_MAX_LENGTH = "Los nombres deben tener mínimo 3 y máximo 50 caracteres";
    String LAST_NAME_MAX_LENGTH = "Los apellidos deben tener mínimo 3 y máximo 50 caracteres";
    String USER_NOT_FOUND = "Usuario no encontrado";
    String USER_ENTITY_TYPE = "User";
}
