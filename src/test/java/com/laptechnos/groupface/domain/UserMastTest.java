package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserMastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMast.class);
        UserMast userMast1 = new UserMast();
        userMast1.setId(1L);
        UserMast userMast2 = new UserMast();
        userMast2.setId(userMast1.getId());
        assertThat(userMast1).isEqualTo(userMast2);
        userMast2.setId(2L);
        assertThat(userMast1).isNotEqualTo(userMast2);
        userMast1.setId(null);
        assertThat(userMast1).isNotEqualTo(userMast2);
    }
}
