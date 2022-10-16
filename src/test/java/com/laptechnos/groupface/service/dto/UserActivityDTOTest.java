package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserActivityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivityDTO.class);
        UserActivityDTO userActivityDTO1 = new UserActivityDTO();
        userActivityDTO1.setId(1L);
        UserActivityDTO userActivityDTO2 = new UserActivityDTO();
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
        userActivityDTO2.setId(userActivityDTO1.getId());
        assertThat(userActivityDTO1).isEqualTo(userActivityDTO2);
        userActivityDTO2.setId(2L);
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
        userActivityDTO1.setId(null);
        assertThat(userActivityDTO1).isNotEqualTo(userActivityDTO2);
    }
}
