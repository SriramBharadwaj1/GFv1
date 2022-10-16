package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPostsViewedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPostsViewedDTO.class);
        UserPostsViewedDTO userPostsViewedDTO1 = new UserPostsViewedDTO();
        userPostsViewedDTO1.setId(1L);
        UserPostsViewedDTO userPostsViewedDTO2 = new UserPostsViewedDTO();
        assertThat(userPostsViewedDTO1).isNotEqualTo(userPostsViewedDTO2);
        userPostsViewedDTO2.setId(userPostsViewedDTO1.getId());
        assertThat(userPostsViewedDTO1).isEqualTo(userPostsViewedDTO2);
        userPostsViewedDTO2.setId(2L);
        assertThat(userPostsViewedDTO1).isNotEqualTo(userPostsViewedDTO2);
        userPostsViewedDTO1.setId(null);
        assertThat(userPostsViewedDTO1).isNotEqualTo(userPostsViewedDTO2);
    }
}
