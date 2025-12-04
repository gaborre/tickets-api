package com.gabn.tickets.configs;

import lombok.Getter;
import org.modelmapper.ModelMapper;

public final class ModelMapperConfig {
    @Getter
    private static final ModelMapper instance;

    static {
        instance = new ModelMapper();
        instance.getConfiguration().setAmbiguityIgnored(true);
    }

    private ModelMapperConfig() {}
}
