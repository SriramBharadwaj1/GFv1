package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoPostAprvlUsrsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoPostAprvlUsrsDTO.class);
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO1 = new AutoPostAprvlUsrsDTO();
        autoPostAprvlUsrsDTO1.setId(1L);
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO2 = new AutoPostAprvlUsrsDTO();
        assertThat(autoPostAprvlUsrsDTO1).isNotEqualTo(autoPostAprvlUsrsDTO2);
        autoPostAprvlUsrsDTO2.setId(autoPostAprvlUsrsDTO1.getId());
        assertThat(autoPostAprvlUsrsDTO1).isEqualTo(autoPostAprvlUsrsDTO2);
        autoPostAprvlUsrsDTO2.setId(2L);
        assertThat(autoPostAprvlUsrsDTO1).isNotEqualTo(autoPostAprvlUsrsDTO2);
        autoPostAprvlUsrsDTO1.setId(null);
        assertThat(autoPostAprvlUsrsDTO1).isNotEqualTo(autoPostAprvlUsrsDTO2);
    }
}
