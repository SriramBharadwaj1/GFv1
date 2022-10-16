package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupUserDTO.class);
        GroupUserDTO groupUserDTO1 = new GroupUserDTO();
        groupUserDTO1.setId(1L);
        GroupUserDTO groupUserDTO2 = new GroupUserDTO();
        assertThat(groupUserDTO1).isNotEqualTo(groupUserDTO2);
        groupUserDTO2.setId(groupUserDTO1.getId());
        assertThat(groupUserDTO1).isEqualTo(groupUserDTO2);
        groupUserDTO2.setId(2L);
        assertThat(groupUserDTO1).isNotEqualTo(groupUserDTO2);
        groupUserDTO1.setId(null);
        assertThat(groupUserDTO1).isNotEqualTo(groupUserDTO2);
    }
}
