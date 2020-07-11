package com.adjust.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adjust.api.web.rest.TestUtil;

public class AdjustMealTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjustMeal.class);
        AdjustMeal adjustMeal1 = new AdjustMeal();
        adjustMeal1.setId(1L);
        AdjustMeal adjustMeal2 = new AdjustMeal();
        adjustMeal2.setId(adjustMeal1.getId());
        assertThat(adjustMeal1).isEqualTo(adjustMeal2);
        adjustMeal2.setId(2L);
        assertThat(adjustMeal1).isNotEqualTo(adjustMeal2);
        adjustMeal1.setId(null);
        assertThat(adjustMeal1).isNotEqualTo(adjustMeal2);
    }
}
