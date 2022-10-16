package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CasteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CasteDTO.class);
        CasteDTO casteDTO1 = new CasteDTO();
        casteDTO1.setId(1L);
        CasteDTO casteDTO2 = new CasteDTO();
        assertThat(casteDTO1).isNotEqualTo(casteDTO2);
        casteDTO2.setId(casteDTO1.getId());
        assertThat(casteDTO1).isEqualTo(casteDTO2);
        casteDTO2.setId(2L);
        assertThat(casteDTO1).isNotEqualTo(casteDTO2);
        casteDTO1.setId(null);
        assertThat(casteDTO1).isNotEqualTo(casteDTO2);
    }
}
