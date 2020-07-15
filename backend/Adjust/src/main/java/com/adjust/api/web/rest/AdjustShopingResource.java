package com.adjust.api.web.rest;

import com.adjust.api.service.AdjustShopingService;
import com.adjust.api.service.CartService;
import com.adjust.api.service.OrderService;
import com.adjust.api.service.ShopingItemService;
import com.adjust.api.service.dto.*;
import com.adjust.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adjust.api.domain.AdjustShoping}.
 */
@RestController
@RequestMapping("/api")
public class AdjustShopingResource {

    private final Logger log = LoggerFactory.getLogger(AdjustShopingResource.class);

    private static final String ENTITY_NAME = "adjustShoping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjustShopingService adjustShopingService;



    public AdjustShopingResource(AdjustShopingService adjustShopingService) {
        this.adjustShopingService = adjustShopingService;
    }

    /**
     * {@code POST  /adjust-shopings} : Create a new adjustShoping.
     *
     * @param adjustShopingDTO the adjustShopingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjustShopingDTO, or with status {@code 400 (Bad Request)} if the adjustShoping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjust-shopings")
    public ResponseEntity<AdjustShopingDTO> createAdjustShoping(@RequestBody AdjustShopingDTO adjustShopingDTO) throws URISyntaxException {
        log.debug("REST request to save AdjustShoping : {}", adjustShopingDTO);
        if (adjustShopingDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjustShoping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjustShopingDTO result = adjustShopingService.save(adjustShopingDTO);
        return ResponseEntity.created(new URI("/api/adjust-shopings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjust-shopings} : Updates an existing adjustShoping.
     *
     * @param adjustShopingDTO the adjustShopingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjustShopingDTO,
     * or with status {@code 400 (Bad Request)} if the adjustShopingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjustShopingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjust-shopings")
    public ResponseEntity<AdjustShopingDTO> updateAdjustShoping(@RequestBody AdjustShopingDTO adjustShopingDTO) throws URISyntaxException {
        log.debug("REST request to update AdjustShoping : {}", adjustShopingDTO);
        if (adjustShopingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjustShopingDTO result = adjustShopingService.save(adjustShopingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustShopingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjust-shopings} : get all the adjustShopings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustShopings in body.
     */
    @GetMapping("/adjust-shopings")
    public List<AdjustShopingDTO> getAllAdjustShopings() {
        log.debug("REST request to get all AdjustShopings");
        return adjustShopingService.findAll();
    }


    /**
     * {@code GET  /adjust-shopings/:id} : get the "id" adjustShoping.
     *
     * @param id the id of the adjustShopingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjustShopingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjust-shopings/{id}")
    public ResponseEntity<AdjustShopingDTO> getAdjustShoping(@PathVariable Long id) {
        log.debug("REST request to get AdjustShoping : {}", id);
        Optional<AdjustShopingDTO> adjustShopingDTO = adjustShopingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjustShopingDTO);
    }

    /**
     * {@code DELETE  /adjust-shopings/:id} : delete the "id" adjustShoping.
     *
     * @param id the id of the adjustShopingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjust-shopings/{id}")
    public ResponseEntity<Void> deleteAdjustShoping(@PathVariable Long id) {
        log.debug("REST request to delete AdjustShoping : {}", id);
        adjustShopingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
