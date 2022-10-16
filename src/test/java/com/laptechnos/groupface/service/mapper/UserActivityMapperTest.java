package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserActivityMapperTest {

    private UserActivityMapper userActivityMapper;

    @BeforeEach
    public void setUp() {
        userActivityMapper = new UserActivityMapperImpl();
    }
}
