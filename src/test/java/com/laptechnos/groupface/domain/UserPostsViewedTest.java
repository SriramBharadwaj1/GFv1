package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPostsViewedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPostsViewed.class);
        UserPostsViewed userPostsViewed1 = new UserPostsViewed();
        userPostsViewed1.setId(1L);
        UserPostsViewed userPostsViewed2 = new UserPostsViewed();
        userPostsViewed2.setId(userPostsViewed1.getId());
        assertThat(userPostsViewed1).isEqualTo(userPostsViewed2);
        userPostsViewed2.setId(2L);
        assertThat(userPostsViewed1).isNotEqualTo(userPostsViewed2);
        userPostsViewed1.setId(null);
        assertThat(userPostsViewed1).isNotEqualTo(userPostsViewed2);
    }
}
