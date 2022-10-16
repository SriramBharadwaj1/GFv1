package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoPostAprvlUsrsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoPostAprvlUsrs.class);
        AutoPostAprvlUsrs autoPostAprvlUsrs1 = new AutoPostAprvlUsrs();
        autoPostAprvlUsrs1.setId(1L);
        AutoPostAprvlUsrs autoPostAprvlUsrs2 = new AutoPostAprvlUsrs();
        autoPostAprvlUsrs2.setId(autoPostAprvlUsrs1.getId());
        assertThat(autoPostAprvlUsrs1).isEqualTo(autoPostAprvlUsrs2);
        autoPostAprvlUsrs2.setId(2L);
        assertThat(autoPostAprvlUsrs1).isNotEqualTo(autoPostAprvlUsrs2);
        autoPostAprvlUsrs1.setId(null);
        assertThat(autoPostAprvlUsrs1).isNotEqualTo(autoPostAprvlUsrs2);
    }
}
