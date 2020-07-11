package com.adjust.api.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adjust.api.web.rest.TestUtil;

public class AdjustMealDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjustMealDTO.class);
        AdjustMealDTO adjustMealDTO1 = new AdjustMealDTO();
        adjustMealDTO1.setId(1L);
        AdjustMealDTO adjustMealDTO2 = new AdjustMealDTO();
        assertThat(adjustMealDTO1).isNotEqualTo(adjustMealDTO2);
        adjustMealDTO2.setId(adjustMealDTO1.getId());
        assertThat(adjustMealDTO1).isEqualTo(adjustMealDTO2);
        adjustMealDTO2.setId(2L);
        assertThat(adjustMealDTO1).isNotEqualTo(adjustMealDTO2);
        adjustMealDTO1.setId(null);
        assertThat(adjustMealDTO1).isNotEqualTo(adjustMealDTO2);
    }
}
