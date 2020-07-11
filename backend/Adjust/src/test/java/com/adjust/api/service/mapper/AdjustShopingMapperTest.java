package com.adjust.api.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdjustShopingMapperTest {

    private AdjustShopingMapper adjustShopingMapper;

    @BeforeEach
    public void setUp() {
        adjustShopingMapper = new AdjustShopingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adjustShopingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adjustShopingMapper.fromId(null)).isNull();
    }
}
