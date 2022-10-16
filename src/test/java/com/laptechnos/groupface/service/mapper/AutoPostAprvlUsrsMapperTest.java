package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoPostAprvlUsrsMapperTest {

    private AutoPostAprvlUsrsMapper autoPostAprvlUsrsMapper;

    @BeforeEach
    public void setUp() {
        autoPostAprvlUsrsMapper = new AutoPostAprvlUsrsMapperImpl();
    }
}
