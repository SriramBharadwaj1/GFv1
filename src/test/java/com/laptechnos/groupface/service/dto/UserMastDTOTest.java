package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserMastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMastDTO.class);
        UserMastDTO userMastDTO1 = new UserMastDTO();
        userMastDTO1.setId(1L);
        UserMastDTO userMastDTO2 = new UserMastDTO();
        assertThat(userMastDTO1).isNotEqualTo(userMastDTO2);
        userMastDTO2.setId(userMastDTO1.getId());
        assertThat(userMastDTO1).isEqualTo(userMastDTO2);
        userMastDTO2.setId(2L);
        assertThat(userMastDTO1).isNotEqualTo(userMastDTO2);
        userMastDTO1.setId(null);
        assertThat(userMastDTO1).isNotEqualTo(userMastDTO2);
    }
}
