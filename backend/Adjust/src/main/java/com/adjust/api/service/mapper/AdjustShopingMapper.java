package com.adjust.api.service.mapper;


import com.adjust.api.domain.*;
import com.adjust.api.service.dto.AdjustShopingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdjustShoping} and its DTO {@link AdjustShopingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdjustShopingMapper extends EntityMapper<AdjustShopingDTO, AdjustShoping> {



    default AdjustShoping fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdjustShoping adjustShoping = new AdjustShoping();
        adjustShoping.setId(id);
        return adjustShoping;
    }
}
