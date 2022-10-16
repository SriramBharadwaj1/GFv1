package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemarksMapperTest {

    private RemarksMapper remarksMapper;

    @BeforeEach
    public void setUp() {
        remarksMapper = new RemarksMapperImpl();
    }
}
