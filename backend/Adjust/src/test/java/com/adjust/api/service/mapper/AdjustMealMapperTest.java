package com.adjust.api.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdjustMealMapperTest {

    private AdjustMealMapper adjustMealMapper;

    @BeforeEach
    public void setUp() {
        adjustMealMapper = new AdjustMealMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(adjustMealMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(adjustMealMapper.fromId(null)).isNull();
    }
}
