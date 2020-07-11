package com.adjust.api.repository;

import com.adjust.api.domain.AdjustProgram;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdjustProgram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjustProgramRepository extends JpaRepository<AdjustProgram, Long> {
}
