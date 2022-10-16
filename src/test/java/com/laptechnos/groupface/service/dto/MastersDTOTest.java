package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MastersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MastersDTO.class);
        MastersDTO mastersDTO1 = new MastersDTO();
        mastersDTO1.setId(1L);
        MastersDTO mastersDTO2 = new MastersDTO();
        assertThat(mastersDTO1).isNotEqualTo(mastersDTO2);
        mastersDTO2.setId(mastersDTO1.getId());
        assertThat(mastersDTO1).isEqualTo(mastersDTO2);
        mastersDTO2.setId(2L);
        assertThat(mastersDTO1).isNotEqualTo(mastersDTO2);
        mastersDTO1.setId(null);
        assertThat(mastersDTO1).isNotEqualTo(mastersDTO2);
    }
}
