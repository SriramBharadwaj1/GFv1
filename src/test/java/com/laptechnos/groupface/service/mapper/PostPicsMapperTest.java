package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostPicsMapperTest {

    private PostPicsMapper postPicsMapper;

    @BeforeEach
    public void setUp() {
        postPicsMapper = new PostPicsMapperImpl();
    }
}
