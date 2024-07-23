package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.FreeTimeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FreeTimeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreeTime.class);
        FreeTime freeTime1 = getFreeTimeSample1();
        FreeTime freeTime2 = new FreeTime();
        assertThat(freeTime1).isNotEqualTo(freeTime2);

        freeTime2.setId(freeTime1.getId());
        assertThat(freeTime1).isEqualTo(freeTime2);

        freeTime2 = getFreeTimeSample2();
        assertThat(freeTime1).isNotEqualTo(freeTime2);
    }
}
