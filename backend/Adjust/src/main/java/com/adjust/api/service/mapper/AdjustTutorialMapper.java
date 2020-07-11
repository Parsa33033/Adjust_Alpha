package com.adjust.api.service.mapper;


import com.adjust.api.domain.*;
import com.adjust.api.service.dto.AdjustTutorialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdjustTutorial} and its DTO {@link AdjustTutorialDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdjustClientMapper.class})
public interface AdjustTutorialMapper extends EntityMapper<AdjustTutorialDTO, AdjustTutorial> {

    @Mapping(source = "client.id", target = "clientId")
    AdjustTutorialDTO toDto(AdjustTutorial adjustTutorial);

    @Mapping(source = "clientId", target = "client")
    AdjustTutorial toEntity(AdjustTutorialDTO adjustTutorialDTO);

    default AdjustTutorial fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdjustTutorial adjustTutorial = new AdjustTutorial();
        adjustTutorial.setId(id);
        return adjustTutorial;
    }
}
