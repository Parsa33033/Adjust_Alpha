package com.adjust.api.service;

import com.adjust.api.domain.AdjustMeal;
import com.adjust.api.repository.AdjustMealRepository;
import com.adjust.api.service.dto.AdjustMealDTO;
import com.adjust.api.service.mapper.AdjustMealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AdjustMeal}.
 */
@Service
@Transactional
public class AdjustMealService {

    private final Logger log = LoggerFactory.getLogger(AdjustMealService.class);

    private final AdjustMealRepository adjustMealRepository;

    private final AdjustMealMapper adjustMealMapper;

    public AdjustMealService(AdjustMealRepository adjustMealRepository, AdjustMealMapper adjustMealMapper) {
        this.adjustMealRepository = adjustMealRepository;
        this.adjustMealMapper = adjustMealMapper;
    }

    /**
     * Save a adjustMeal.
     *
     * @param adjustMealDTO the entity to save.
     * @return the persisted entity.
     */
    public AdjustMealDTO save(AdjustMealDTO adjustMealDTO) {
        log.debug("Request to save AdjustMeal : {}", adjustMealDTO);
        AdjustMeal adjustMeal = adjustMealMapper.toEntity(adjustMealDTO);
        adjustMeal = adjustMealRepository.save(adjustMeal);
        return adjustMealMapper.toDto(adjustMeal);
    }

    /**
     * Get all the adjustMeals.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AdjustMealDTO> findAll() {
        log.debug("Request to get all AdjustMeals");
        return adjustMealRepository.findAll().stream()
            .map(adjustMealMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one adjustMeal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdjustMealDTO> findOne(Long id) {
        log.debug("Request to get AdjustMeal : {}", id);
        return adjustMealRepository.findById(id)
            .map(adjustMealMapper::toDto);
    }

    /**
     * Delete the adjustMeal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdjustMeal : {}", id);
        adjustMealRepository.deleteById(id);
    }
}
