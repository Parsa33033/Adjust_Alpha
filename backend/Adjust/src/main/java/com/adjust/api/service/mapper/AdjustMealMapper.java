package com.adjust.api.service.mapper;


import com.adjust.api.domain.*;
import com.adjust.api.service.dto.AdjustMealDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdjustMeal} and its DTO {@link AdjustMealDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdjustMealMapper extends EntityMapper<AdjustMealDTO, AdjustMeal> {



    default AdjustMeal fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdjustMeal adjustMeal = new AdjustMeal();
        adjustMeal.setId(id);
        return adjustMeal;
    }
}
