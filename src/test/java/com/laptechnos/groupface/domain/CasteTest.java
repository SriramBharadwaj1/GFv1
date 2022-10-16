package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CasteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caste.class);
        Caste caste1 = new Caste();
        caste1.setId(1L);
        Caste caste2 = new Caste();
        caste2.setId(caste1.getId());
        assertThat(caste1).isEqualTo(caste2);
        caste2.setId(2L);
        assertThat(caste1).isNotEqualTo(caste2);
        caste1.setId(null);
        assertThat(caste1).isNotEqualTo(caste2);
    }
}
