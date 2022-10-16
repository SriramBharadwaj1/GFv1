package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostVideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostVideo.class);
        PostVideo postVideo1 = new PostVideo();
        postVideo1.setId(1L);
        PostVideo postVideo2 = new PostVideo();
        postVideo2.setId(postVideo1.getId());
        assertThat(postVideo1).isEqualTo(postVideo2);
        postVideo2.setId(2L);
        assertThat(postVideo1).isNotEqualTo(postVideo2);
        postVideo1.setId(null);
        assertThat(postVideo1).isNotEqualTo(postVideo2);
    }
}
