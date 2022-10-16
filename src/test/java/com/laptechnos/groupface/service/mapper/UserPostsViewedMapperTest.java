package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPostsViewedMapperTest {

    private UserPostsViewedMapper userPostsViewedMapper;

    @BeforeEach
    public void setUp() {
        userPostsViewedMapper = new UserPostsViewedMapperImpl();
    }
}
