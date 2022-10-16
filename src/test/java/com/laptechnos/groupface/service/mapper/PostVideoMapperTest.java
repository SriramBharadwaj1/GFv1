package com.laptechnos.groupface.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostVideoMapperTest {

    private PostVideoMapper postVideoMapper;

    @BeforeEach
    public void setUp() {
        postVideoMapper = new PostVideoMapperImpl();
    }
}
