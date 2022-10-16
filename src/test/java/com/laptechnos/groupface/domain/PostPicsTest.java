package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostPicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostPics.class);
        PostPics postPics1 = new PostPics();
        postPics1.setId(1L);
        PostPics postPics2 = new PostPics();
        postPics2.setId(postPics1.getId());
        assertThat(postPics1).isEqualTo(postPics2);
        postPics2.setId(2L);
        assertThat(postPics1).isNotEqualTo(postPics2);
        postPics1.setId(null);
        assertThat(postPics1).isNotEqualTo(postPics2);
    }
}
