package com.laptechnos.groupface.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgDetailsDTO.class);
        OrgDetailsDTO orgDetailsDTO1 = new OrgDetailsDTO();
        orgDetailsDTO1.setId(1L);
        OrgDetailsDTO orgDetailsDTO2 = new OrgDetailsDTO();
        assertThat(orgDetailsDTO1).isNotEqualTo(orgDetailsDTO2);
        orgDetailsDTO2.setId(orgDetailsDTO1.getId());
        assertThat(orgDetailsDTO1).isEqualTo(orgDetailsDTO2);
        orgDetailsDTO2.setId(2L);
        assertThat(orgDetailsDTO1).isNotEqualTo(orgDetailsDTO2);
        orgDetailsDTO1.setId(null);
        assertThat(orgDetailsDTO1).isNotEqualTo(orgDetailsDTO2);
    }
}
