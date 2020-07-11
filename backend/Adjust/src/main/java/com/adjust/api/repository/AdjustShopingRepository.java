package com.adjust.api.repository;

import com.adjust.api.domain.AdjustShoping;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdjustShoping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjustShopingRepository extends JpaRepository<AdjustShoping, Long> {
}
