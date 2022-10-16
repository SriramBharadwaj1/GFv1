package com.laptechnos.groupface.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.laptechnos.groupface.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserActivity.class);
        UserActivity userActivity1 = new UserActivity();
        userActivity1.setId(1L);
        UserActivity userActivity2 = new UserActivity();
        userActivity2.setId(userActivity1.getId());
        assertThat(userActivity1).isEqualTo(userActivity2);
        userActivity2.setId(2L);
        assertThat(userActivity1).isNotEqualTo(userActivity2);
        userActivity1.setId(null);
        assertThat(userActivity1).isNotEqualTo(userActivity2);
    }
}
