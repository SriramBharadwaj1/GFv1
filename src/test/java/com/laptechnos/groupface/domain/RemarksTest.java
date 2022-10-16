package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemarksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remarks.class);
        Remarks remarks1 = new Remarks();
        remarks1.setId(1L);
        Remarks remarks2 = new Remarks();
        remarks2.setId(remarks1.getId());
        assertThat(remarks1).isEqualTo(remarks2);
        remarks2.setId(2L);
        assertThat(remarks1).isNotEqualTo(remarks2);
        remarks1.setId(null);
        assertThat(remarks1).isNotEqualTo(remarks2);
    }
}
