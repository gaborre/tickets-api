package com.gabn.tickets.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public final class ObjectMapperConfig {
    private static final ObjectMapper instance;

    static {
        instance = new ObjectMapper();
        instance.registerModule(new JavaTimeModule());
        instance.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        instance.configure(FAIL_ON_EMPTY_BEANS, false);
        instance.configure(ACCEPT_FLOAT_AS_INT, false);
        instance.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ObjectMapperConfig() {}

    public static ObjectMapper getInstance() {
        return instance;
    }
}
