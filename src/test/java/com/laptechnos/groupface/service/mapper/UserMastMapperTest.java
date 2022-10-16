package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMastMapperTest {

    private UserMastMapper userMastMapper;

    @BeforeEach
    public void setUp() {
        userMastMapper = new UserMastMapperImpl();
    }
}
