package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MastersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Masters.class);
        Masters masters1 = new Masters();
        masters1.setId(1L);
        Masters masters2 = new Masters();
        masters2.setId(masters1.getId());
        assertThat(masters1).isEqualTo(masters2);
        masters2.setId(2L);
        assertThat(masters1).isNotEqualTo(masters2);
        masters1.setId(null);
        assertThat(masters1).isNotEqualTo(masters2);
    }
}
