package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupUserMapperTest {

    private GroupUserMapper groupUserMapper;

    @BeforeEach
    public void setUp() {
        groupUserMapper = new GroupUserMapperImpl();
    }
}
