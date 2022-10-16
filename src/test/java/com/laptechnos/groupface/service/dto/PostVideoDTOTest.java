package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostVideoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostVideoDTO.class);
        PostVideoDTO postVideoDTO1 = new PostVideoDTO();
        postVideoDTO1.setId(1L);
        PostVideoDTO postVideoDTO2 = new PostVideoDTO();
        assertThat(postVideoDTO1).isNotEqualTo(postVideoDTO2);
        postVideoDTO2.setId(postVideoDTO1.getId());
        assertThat(postVideoDTO1).isEqualTo(postVideoDTO2);
        postVideoDTO2.setId(2L);
        assertThat(postVideoDTO1).isNotEqualTo(postVideoDTO2);
        postVideoDTO1.setId(null);
        assertThat(postVideoDTO1).isNotEqualTo(postVideoDTO2);
    }
}
