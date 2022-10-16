package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemarksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemarksDTO.class);
        RemarksDTO remarksDTO1 = new RemarksDTO();
        remarksDTO1.setId(1L);
        RemarksDTO remarksDTO2 = new RemarksDTO();
        assertThat(remarksDTO1).isNotEqualTo(remarksDTO2);
        remarksDTO2.setId(remarksDTO1.getId());
        assertThat(remarksDTO1).isEqualTo(remarksDTO2);
        remarksDTO2.setId(2L);
        assertThat(remarksDTO1).isNotEqualTo(remarksDTO2);
        remarksDTO1.setId(null);
        assertThat(remarksDTO1).isNotEqualTo(remarksDTO2);
    }
}
