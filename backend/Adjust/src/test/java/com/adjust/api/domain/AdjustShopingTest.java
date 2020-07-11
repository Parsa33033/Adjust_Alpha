package com.adjust.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adjust.api.web.rest.TestUtil;

public class AdjustShopingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjustShoping.class);
        AdjustShoping adjustShoping1 = new AdjustShoping();
        adjustShoping1.setId(1L);
        AdjustShoping adjustShoping2 = new AdjustShoping();
        adjustShoping2.setId(adjustShoping1.getId());
        assertThat(adjustShoping1).isEqualTo(adjustShoping2);
        adjustShoping2.setId(2L);
        assertThat(adjustShoping1).isNotEqualTo(adjustShoping2);
        adjustShoping1.setId(null);
        assertThat(adjustShoping1).isNotEqualTo(adjustShoping2);
    }
}
