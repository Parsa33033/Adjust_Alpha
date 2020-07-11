package com.adjust.api.repository;

import com.adjust.api.domain.AdjustMeal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdjustMeal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjustMealRepository extends JpaRepository<AdjustMeal, Long> {
}
