package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrgDetailsMapperTest {

    private OrgDetailsMapper orgDetailsMapper;

    @BeforeEach
    public void setUp() {
        orgDetailsMapper = new OrgDetailsMapperImpl();
    }
}
