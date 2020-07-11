package com.adjust.api.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.adjust.api.web.rest.TestUtil;

public class AdjustShopingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjustShopingDTO.class);
        AdjustShopingDTO adjustShopingDTO1 = new AdjustShopingDTO();
        adjustShopingDTO1.setId(1L);
        AdjustShopingDTO adjustShopingDTO2 = new AdjustShopingDTO();
        assertThat(adjustShopingDTO1).isNotEqualTo(adjustShopingDTO2);
        adjustShopingDTO2.setId(adjustShopingDTO1.getId());
        assertThat(adjustShopingDTO1).isEqualTo(adjustShopingDTO2);
        adjustShopingDTO2.setId(2L);
        assertThat(adjustShopingDTO1).isNotEqualTo(adjustShopingDTO2);
        adjustShopingDTO1.setId(null);
        assertThat(adjustShopingDTO1).isNotEqualTo(adjustShopingDTO2);
    }
}
