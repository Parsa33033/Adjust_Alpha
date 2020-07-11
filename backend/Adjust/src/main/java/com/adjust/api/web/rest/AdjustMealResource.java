package com.adjust.api.web.rest;

import com.adjust.api.service.AdjustMealService;
import com.adjust.api.web.rest.errors.BadRequestAlertException;
import com.adjust.api.service.dto.AdjustMealDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adjust.api.domain.AdjustMeal}.
 */
@RestController
@RequestMapping("/api")
public class AdjustMealResource {

    private final Logger log = LoggerFactory.getLogger(AdjustMealResource.class);

    private static final String ENTITY_NAME = "adjustMeal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjustMealService adjustMealService;

    public AdjustMealResource(AdjustMealService adjustMealService) {
        this.adjustMealService = adjustMealService;
    }

    /**
     * {@code POST  /adjust-meals} : Create a new adjustMeal.
     *
     * @param adjustMealDTO the adjustMealDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjustMealDTO, or with status {@code 400 (Bad Request)} if the adjustMeal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjust-meals")
    public ResponseEntity<AdjustMealDTO> createAdjustMeal(@RequestBody AdjustMealDTO adjustMealDTO) throws URISyntaxException {
        log.debug("REST request to save AdjustMeal : {}", adjustMealDTO);
        if (adjustMealDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjustMeal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjustMealDTO result = adjustMealService.save(adjustMealDTO);
        return ResponseEntity.created(new URI("/api/adjust-meals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjust-meals} : Updates an existing adjustMeal.
     *
     * @param adjustMealDTO the adjustMealDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjustMealDTO,
     * or with status {@code 400 (Bad Request)} if the adjustMealDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjustMealDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjust-meals")
    public ResponseEntity<AdjustMealDTO> updateAdjustMeal(@RequestBody AdjustMealDTO adjustMealDTO) throws URISyntaxException {
        log.debug("REST request to update AdjustMeal : {}", adjustMealDTO);
        if (adjustMealDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjustMealDTO result = adjustMealService.save(adjustMealDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustMealDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjust-meals} : get all the adjustMeals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustMeals in body.
     */
    @GetMapping("/adjust-meals")
    public List<AdjustMealDTO> getAllAdjustMeals() {
        log.debug("REST request to get all AdjustMeals");
        return adjustMealService.findAll();
    }

    /**
     * {@code GET  /adjust-meals/:id} : get the "id" adjustMeal.
     *
     * @param id the id of the adjustMealDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjustMealDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjust-meals/{id}")
    public ResponseEntity<AdjustMealDTO> getAdjustMeal(@PathVariable Long id) {
        log.debug("REST request to get AdjustMeal : {}", id);
        Optional<AdjustMealDTO> adjustMealDTO = adjustMealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjustMealDTO);
    }

    /**
     * {@code DELETE  /adjust-meals/:id} : delete the "id" adjustMeal.
     *
     * @param id the id of the adjustMealDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjust-meals/{id}")
    public ResponseEntity<Void> deleteAdjustMeal(@PathVariable Long id) {
        log.debug("REST request to delete AdjustMeal : {}", id);
        adjustMealService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
