package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostPicsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostPicsDTO.class);
        PostPicsDTO postPicsDTO1 = new PostPicsDTO();
        postPicsDTO1.setId(1L);
        PostPicsDTO postPicsDTO2 = new PostPicsDTO();
        assertThat(postPicsDTO1).isNotEqualTo(postPicsDTO2);
        postPicsDTO2.setId(postPicsDTO1.getId());
        assertThat(postPicsDTO1).isEqualTo(postPicsDTO2);
        postPicsDTO2.setId(2L);
        assertThat(postPicsDTO1).isNotEqualTo(postPicsDTO2);
        postPicsDTO1.setId(null);
        assertThat(postPicsDTO1).isNotEqualTo(postPicsDTO2);
    }
}
