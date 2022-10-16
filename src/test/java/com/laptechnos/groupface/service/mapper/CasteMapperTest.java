package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CasteMapperTest {

    private CasteMapper casteMapper;

    @BeforeEach
    public void setUp() {
        casteMapper = new CasteMapperImpl();
    }
}
