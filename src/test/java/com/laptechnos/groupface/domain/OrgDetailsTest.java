package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgDetails.class);
        OrgDetails orgDetails1 = new OrgDetails();
        orgDetails1.setId(1L);
        OrgDetails orgDetails2 = new OrgDetails();
        orgDetails2.setId(orgDetails1.getId());
        assertThat(orgDetails1).isEqualTo(orgDetails2);
        orgDetails2.setId(2L);
        assertThat(orgDetails1).isNotEqualTo(orgDetails2);
        orgDetails1.setId(null);
        assertThat(orgDetails1).isNotEqualTo(orgDetails2);
    }
}
