package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FreeTimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreeTimeDTO.class);
        FreeTimeDTO freeTimeDTO1 = new FreeTimeDTO();
        freeTimeDTO1.setId(1L);
        FreeTimeDTO freeTimeDTO2 = new FreeTimeDTO();
        assertThat(freeTimeDTO1).isNotEqualTo(freeTimeDTO2);
        freeTimeDTO2.setId(freeTimeDTO1.getId());
        assertThat(freeTimeDTO1).isEqualTo(freeTimeDTO2);
        freeTimeDTO2.setId(2L);
        assertThat(freeTimeDTO1).isNotEqualTo(freeTimeDTO2);
        freeTimeDTO1.setId(null);
        assertThat(freeTimeDTO1).isNotEqualTo(freeTimeDTO2);
    }
}
