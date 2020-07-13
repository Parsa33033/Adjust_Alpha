package com.adjust.api.web.rest;

import com.adjust.api.domain.*;
import com.adjust.api.repository.AdjustClientRepository;
import com.adjust.api.repository.AdjustTutorialRepository;
import com.adjust.api.repository.TutorialRepository;
import com.adjust.api.security.SecurityUtils;
import com.adjust.api.security.jwt.JWTFilter;
import com.adjust.api.security.jwt.TokenProvider;
import com.adjust.api.service.*;
import com.adjust.api.service.dto.*;
import com.adjust.api.service.mapper.AdjustClientMapper;
import com.adjust.api.service.mapper.AdjustTutorialMapper;
import com.adjust.api.service.mapper.TutorialMapper;
import com.adjust.api.service.mapper.TutorialVideoMapper;
import com.adjust.api.web.rest.errors.BadRequestAlertException;

import com.sun.mail.iap.Response;
import com.zaxxer.hikari.util.ClockSource;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.jsonwebtoken.JwtParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.zalando.fauxpas.ThrowingConsumer;
import org.zalando.fauxpas.ThrowingFunction;

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
import java.util.function.Consumer;
import java.util.stream.Collectors;
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

    private final TokenProvider tokenProvider;

    private final AdjustClientService adjustClientService;

    private final AdjustClientRepository adjustClientRepository;

    private final AdjustClientMapper adjustClientMapper;

    private final AdjustShopingResource adjustShopingResource;

    private final AdjustTokensResource adjustTokensResource;

    private final AdjustTutorialService adjustTutorialService;
    private final AdjustTutorialVideoService adjustTutorialVideoService;

    private final TutorialService tutorialService;
    private final TutorialVideoService tutorialVideoService;
    private final TutorialRepository tutorialRepository;
    private final TutorialMapper tutorialMapper;
    private final TutorialVideoMapper tutorialVideoMapper;




    public AdjustClientResource(TokenProvider tokenProvider, AdjustClientService adjustClientService, AdjustClientRepository adjustClientRepository,
                                AdjustClientMapper adjustClientMapper, AdjustShopingResource adjustShopingResource,
                                AdjustTokensResource adjustTokensResource, AdjustTutorialService adjustTutorialService,
                                TutorialRepository tutorialRepository, AdjustTutorialVideoService adjustTutorialVideoService,
                                TutorialService tutorialService, TutorialVideoService tutorialVideoService, TutorialMapper tutorialMapper,
                                TutorialVideoMapper tutorialVideoMapper) {
        this.tokenProvider = tokenProvider;
        this.adjustClientService = adjustClientService;
        this.adjustClientRepository = adjustClientRepository;
        this.adjustClientMapper = adjustClientMapper;
        this.adjustShopingResource = adjustShopingResource;
        this.adjustTokensResource = adjustTokensResource;
        this.adjustTutorialService = adjustTutorialService;
        this.tutorialRepository = tutorialRepository;
        this.adjustTutorialVideoService = adjustTutorialVideoService;
        this.tutorialService = tutorialService;
        this.tutorialVideoService = tutorialVideoService;
        this.tutorialMapper = tutorialMapper;
        this.tutorialVideoMapper = tutorialVideoMapper;
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

    @PostMapping("/client/app/buy-tutorial")
    public ResponseEntity<TutorialDTO> buyVideo(@RequestBody Long videoId, HttpServletResponse response) throws Exception {
        response.setHeader("charset", "utf-8");
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        AdjustTutorialDTO adjustTutorialDTO = adjustTutorialService.findOne(videoId).get();

        if (adjustClientDTO.getToken() < adjustTutorialDTO.getTokenPrice()) {
            throw new Exception("client does not have sufficient tokens!");
        }

        List<TutorialDTO> tutorialDTOList = tutorialRepository.findTutorialsByClient(adjustClientRepository.findAdjustClientByUsername(userLogin).get()).stream().map(tutorialMapper::toDto).collect(Collectors.toList());

        boolean clientHasTutorial = tutorialDTOList.stream().filter((e) -> e.getVideoId() == adjustTutorialDTO.getVideoId()).collect(Collectors.toList()).iterator().hasNext();
        if (clientHasTutorial) {
            throw new Exception("client has tutorial already!");
        }

        AdjustTutorialVideoDTO adjustTutorialVideoDTO = adjustTutorialVideoService.findOne(adjustTutorialDTO.getVideoId()).get();

        TutorialVideoDTO tutorialVideoDTO = new TutorialVideoDTO();
        tutorialVideoDTO.setContent(adjustTutorialVideoDTO.getContent());
        tutorialVideoDTO.setContentContentType(adjustTutorialVideoDTO.getContentContentType());

        tutorialVideoDTO = tutorialVideoService.save(tutorialVideoDTO);

        TutorialDTO tutorialDTO = new TutorialDTO();
        tutorialDTO.setClientId(adjustClientDTO.getId());
        tutorialDTO.setDescription(adjustTutorialDTO.getDescription());
        tutorialDTO.setText(adjustTutorialDTO.getText());
        tutorialDTO.setThumbnail(adjustTutorialDTO.getThumbnail());
        tutorialDTO.setThumbnailContentType(adjustTutorialDTO.getThumbnailContentType());
        tutorialDTO.setTitle(adjustTutorialDTO.getTitle());
        tutorialDTO.setTokenPrice(adjustTutorialDTO.getTokenPrice());
        tutorialDTO.setVideoId(tutorialVideoDTO.getId());
        tutorialDTO = tutorialService.save(tutorialDTO);


        adjustClientDTO.setToken(adjustClientDTO.getToken() - adjustTutorialDTO.getTokenPrice());
        adjustClientService.save(adjustClientDTO);

        return ResponseEntity.ok().header("charset", "utf-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTO.getId().toString()))
            .body(tutorialDTO);
    }

    @GetMapping("/client/app/get-tutorials")
    public ResponseEntity<List<TutorialDTO>> getClientTutorials() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        List<TutorialDTO> tutorialDTOList = tutorialRepository.findTutorialsByClient(adjustClientRepository.findAdjustClientByUsername(userLogin).get()).stream().map(tutorialMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok().header("charset", "utf-8")
            .body(tutorialDTOList);
    }

    @GetMapping("/client/app/get-tutorial-video")
    public DeferredResult<ResponseEntity<ByteArrayResource>> getClientTutorialVideo(@RequestParam("video-id") Long videoId, @RequestParam("jwt") String jwt) throws Exception {
        String userLogin = ((User) tokenProvider.getAuthentication(jwt).getPrincipal()).getUsername();
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        List<TutorialDTO> tutorialVideoDTOList = tutorialRepository.findTutorialsByClient(adjustClientMapper.toEntity(adjustClientDTO)).stream().map(tutorialMapper::toDto).collect(Collectors.toList());
        tutorialVideoDTOList.stream().filter((e) -> )
        TutorialVideo tutorialVideo = tutorialVideoMapper.toEntity(tutorialVideoDTO);
        Tutorial tutorial = tutorialRepository.findTutorialByVideo(tutorialVideo).get();
        if (adjustClientDTO.getId() != tutorial.getClient().getId()) {
            throw new Exception("client has not bought the tutorial");
        }
        byte[] videoByte = tutorialVideo.getContent();
        ByteArrayResource resource = new ByteArrayResource(videoByte);
        DeferredResult<ResponseEntity<ByteArrayResource>> dr = new DeferredResult<>();
        dr.setResult(ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaType.parseMediaType(tutorialVideo.getContentContentType()))
            .body(resource));
        return dr;
    }

    @GetMapping("/client/app/get-client-token")
    public ResponseEntity<Double> getClientTokens() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        return ResponseEntity.ok(adjustClientDTO.getToken());
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
