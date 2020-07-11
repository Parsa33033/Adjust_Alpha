package com.adjust.api.service;

import com.adjust.api.domain.AdjustShoping;
import com.adjust.api.repository.AdjustShopingRepository;
import com.adjust.api.service.dto.AdjustShopingDTO;
import com.adjust.api.service.mapper.AdjustShopingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdjustShoping}.
 */
@Service
@Transactional
public class AdjustShopingService {

    private final Logger log = LoggerFactory.getLogger(AdjustShopingService.class);

    private final AdjustShopingRepository adjustShopingRepository;

    private final AdjustShopingMapper adjustShopingMapper;

    public AdjustShopingService(AdjustShopingRepository adjustShopingRepository, AdjustShopingMapper adjustShopingMapper) {
        this.adjustShopingRepository = adjustShopingRepository;
        this.adjustShopingMapper = adjustShopingMapper;
    }

    /**
     * Save a adjustShoping.
     *
     * @param adjustShopingDTO the entity to save.
     * @return the persisted entity.
     */
    public AdjustShopingDTO save(AdjustShopingDTO adjustShopingDTO) {
        log.debug("Request to save AdjustShoping : {}", adjustShopingDTO);
        AdjustShoping adjustShoping = adjustShopingMapper.toEntity(adjustShopingDTO);
        adjustShoping = adjustShopingRepository.save(adjustShoping);
        return adjustShopingMapper.toDto(adjustShoping);
    }

    /**
     * Get all the adjustShopings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdjustShopingDTO> findAll() {
        log.debug("Request to get all AdjustShopings");
        return adjustShopingRepository.findAll().stream()
            .map(adjustShopingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one adjustShoping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdjustShopingDTO> findOne(Long id) {
        log.debug("Request to get AdjustShoping : {}", id);
        return adjustShopingRepository.findById(id)
            .map(adjustShopingMapper::toDto);
    }

    /**
     * Delete the adjustShoping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdjustShoping : {}", id);
        adjustShopingRepository.deleteById(id);
    }
}
