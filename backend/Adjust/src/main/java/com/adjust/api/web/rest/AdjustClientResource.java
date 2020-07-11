package com.adjust.api.web.rest;

import com.adjust.api.domain.AdjustClient;
import com.adjust.api.domain.AdjustShoping;
import com.adjust.api.repository.AdjustClientRepository;
import com.adjust.api.security.SecurityUtils;
import com.adjust.api.service.AdjustClientService;
import com.adjust.api.service.dto.AdjustShopingDTO;
import com.adjust.api.service.dto.AdjustTokensDTO;
import com.adjust.api.service.mapper.AdjustClientMapper;
import com.adjust.api.web.rest.errors.BadRequestAlertException;
import com.adjust.api.service.dto.AdjustClientDTO;

import com.sun.mail.iap.Response;
import com.zaxxer.hikari.util.ClockSource;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.adjust.api.domain.AdjustClient}.
 */
@RestController
@RequestMapping("/api")
public class AdjustClientResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AdjustClientResource.class);

    private static final String ENTITY_NAME = "adjustClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjustClientService adjustClientService;

    private final AdjustClientRepository adjustClientRepository;

    private final AdjustClientMapper adjustClientMapper;

    private final AdjustShopingResource adjustShopingResource;
    private final AdjustTokensResource adjustTokensResource;

    public AdjustClientResource(AdjustClientService adjustClientService, AdjustClientRepository adjustClientRepository,
                                AdjustClientMapper adjustClientMapper, AdjustShopingResource adjustShopingResource,
                                AdjustTokensResource adjustTokensResource) {
        this.adjustClientService = adjustClientService;
        this.adjustClientRepository = adjustClientRepository;
        this.adjustClientMapper = adjustClientMapper;
        this.adjustShopingResource = adjustShopingResource;
        this.adjustTokensResource = adjustTokensResource;
    }

    /**
     * {@code POST  /adjust-clients} : Create a new adjustClient.
     *
     * @param adjustClientDTO the adjustClientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjustClientDTO, or with status {@code 400 (Bad Request)} if the adjustClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjust-clients")
    public ResponseEntity<AdjustClientDTO> createAdjustClient(@RequestBody AdjustClientDTO adjustClientDTO) throws URISyntaxException {
        log.debug("REST request to save AdjustClient : {}", adjustClientDTO);
        if (adjustClientDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjustClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjustClientDTO result = adjustClientService.save(adjustClientDTO);
        return ResponseEntity.created(new URI("/api/adjust-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjust-clients} : Updates an existing adjustClient.
     *
     * @param adjustClientDTO the adjustClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjustClientDTO,
     * or with status {@code 400 (Bad Request)} if the adjustClientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjustClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjust-clients")
    public ResponseEntity<AdjustClientDTO> updateAdjustClient(@RequestBody AdjustClientDTO adjustClientDTO) throws URISyntaxException {
        return adjustClientOpp(adjustClientDTO);
    }

    /**
     * {@code PUT  /adjust-clients} : Updates an existing adjustClient from client app.
     *
     * @param adjustClientDTO the adjustClientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjustClientDTO,
     * or with status {@code 400 (Bad Request)} if the adjustClientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjustClientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client/app/adjust-clients")
    public ResponseEntity<AdjustClientDTO> updateAdjustClientByApp(@RequestBody AdjustClientDTO adjustClientDTO) throws URISyntaxException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTOUpdatee = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        adjustClientDTO.setScore(null);
        adjustClientDTO.setToken(null);
        if (adjustClientDTO.getBirthDate() != null)
            adjustClientDTO.setBirthDate(adjustClientDTO.getBirthDate().plusDays(1));
        AdjustClientDTO adjustClientDTOUpdated = (AdjustClientDTO) ClassUpdater.updateClass(adjustClientDTO, adjustClientDTOUpdatee);
        return adjustClientOpp(adjustClientDTOUpdated);
    }

    /**
     * buy a token for client by client app
     *
     * @param adjustTokenItemId
     * @return
     */
    @PostMapping("/client/app/buy-token")
    public ResponseEntity<Double> buyToken(@RequestBody String adjustTokenItemId) {
        AdjustTokensDTO adjustTokensDTO = adjustTokensResource.getAdjustTokens(Long.valueOf(adjustTokenItemId)).getBody();
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        adjustClientDTO.setToken(adjustClientDTO.getToken() + adjustTokensDTO.getToken());
        AdjustClientDTO result = adjustClientService.save(adjustClientDTO);
        return ResponseEntity.ok(result.getToken());
    }

    private ResponseEntity<AdjustClientDTO> adjustClientOpp(AdjustClientDTO adjustClientDTO) {
        log.debug("REST request to update AdjustClient : {}", adjustClientDTO);
        if (adjustClientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjustClientDTO result = adjustClientService.save(adjustClientDTO);
        return ResponseEntity.ok().header("charset", "UTF-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjust-clients} : get all the adjustClients.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustClients in body.
     */
    @GetMapping("/adjust-clients")
    public List<AdjustClientDTO> getAllAdjustClients(@RequestParam(required = false) String filter) {
        if ("conversation-is-null".equals(filter)) {
            log.debug("REST request to get all AdjustClients where conversation is null");
            return adjustClientService.findAllWhereConversationIsNull();
        }
        log.debug("REST request to get all AdjustClients");
        return adjustClientService.findAll();
    }

    /**
     * {@code GET  /adjust-clients/:id} : get the "id" adjustClient.
     *
     * @param id the id of the adjustClientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjustClientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjust-clients/{id}")
    public ResponseEntity<AdjustClientDTO> getAdjustClient(@PathVariable Long id) {
        log.debug("REST request to get AdjustClient : {}", id);
        Optional<AdjustClientDTO> adjustClientDTO = adjustClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjustClientDTO);
    }

    @GetMapping("/client/app/adjust-clients")
    public ResponseEntity<AdjustClientDTO> getAdjustClientByClientApp() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        return ResponseEntity.ok().header("charset", "UTF-8")
            .header("charset", "UTF-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTO.getId().toString()))
            .body(adjustClientDTO);
    }

    /**
     * {@code DELETE  /adjust-clients/:id} : delete the "id" adjustClient.
     *
     * @param id the id of the adjustClientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjust-clients/{id}")
    public ResponseEntity<Void> deleteAdjustClient(@PathVariable Long id) {
        log.debug("REST request to delete AdjustClient : {}", id);
        adjustClientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


}
