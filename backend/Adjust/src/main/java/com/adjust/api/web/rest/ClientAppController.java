package com.adjust.api.web.rest;

import com.adjust.api.domain.AdjustClient;
import com.adjust.api.domain.Tutorial;
import com.adjust.api.domain.TutorialVideo;
import com.adjust.api.repository.AdjustClientRepository;
import com.adjust.api.repository.TutorialRepository;
import com.adjust.api.security.SecurityUtils;
import com.adjust.api.security.jwt.TokenProvider;
import com.adjust.api.service.*;
import com.adjust.api.service.dto.*;
import com.adjust.api.service.mapper.AdjustClientMapper;
import com.adjust.api.service.mapper.TutorialMapper;
import com.adjust.api.web.rest.errors.BadRequestAlertException;
import com.adjust.api.web.rest.errors.InvalidPasswordException;
import com.adjust.api.web.rest.errors.LoginAlreadyUsedException;
import com.adjust.api.web.rest.vm.LoginVM;
import com.adjust.api.web.rest.vm.ManagedUserVM;
import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/client/app")
public class ClientAppController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AdjustClientResource.class);

    private static final String ENTITY_NAME = "adjustClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${client.app.apiKey}")
    private String apiKey;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final UserJWTController userJWTController;

    private final AdjustClientService adjustClientService;
    private final AdjustClientRepository adjustClientRepository;
    private final AdjustClientMapper adjustClientMapper;

    private final AdjustTutorialService adjustTutorialService;
    private final AdjustTutorialVideoService adjustTutorialVideoService;

    private final TutorialService tutorialService;
    private final TutorialVideoService tutorialVideoService;
    private final TutorialRepository tutorialRepository;
    private final TutorialMapper tutorialMapper;

    private final AdjustShopingItemService adjustShopingItemService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ShopingItemService shopingItemService;

    private final AdjustTokensResource adjustTokensResource;
    private final AdjustTokensService adjustTokensService;

    public ClientAppController(UserService userService, UserJWTController userJWTController, TokenProvider tokenProvider, AdjustClientService adjustClientService,
                               AdjustClientRepository adjustClientRepository, AdjustClientMapper adjustClientMapper,
                               AdjustTokensResource adjustTokensResource, AdjustTutorialService adjustTutorialService,
                               TutorialRepository tutorialRepository, AdjustTutorialVideoService adjustTutorialVideoService,
                               TutorialService tutorialService, TutorialVideoService tutorialVideoService, TutorialMapper tutorialMapper,
                               AdjustShopingItemService adjustShopingItemService, OrderService orderService,
                               CartService cartService, ShopingItemService shopingItemService, AdjustTokensService adjustTokensService) {
        this.userService = userService;
        this.userJWTController = userJWTController;
        this.tokenProvider = tokenProvider;
        this.adjustClientService = adjustClientService;
        this.adjustClientRepository = adjustClientRepository;
        this.adjustClientMapper = adjustClientMapper;;
        this.adjustTutorialService = adjustTutorialService;
        this.adjustTutorialVideoService = adjustTutorialVideoService;
        this.tutorialRepository = tutorialRepository;
        this.tutorialService = tutorialService;
        this.tutorialVideoService = tutorialVideoService;
        this.tutorialMapper = tutorialMapper;
        this.adjustShopingItemService = adjustShopingItemService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.shopingItemService = shopingItemService;
        this.adjustTokensResource = adjustTokensResource;
        this.adjustTokensService = adjustTokensService;
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws com.adjust.api.web.rest.errors.InvalidPasswordException  {@code 400 (Bad Request)} if the password is incorrect.
     * @throws com.adjust.api.web.rest.errors.EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserJWTController.JWTToken> registerAccountByClientApp(@Valid @RequestBody ManagedUserVM managedUserVM, @RequestHeader("Authorization") String authorization) throws Exception {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }

        String clientApiKeyEncoded = authorization.substring(6);
        log.info("client api key encoded: {}", clientApiKeyEncoded);

        String apiKeyEncoded = new String(Base64.getEncoder().encode(apiKey.getBytes()), "UTF-8");
        log.info("backend api key encoded: {}", apiKeyEncoded);
        if (!clientApiKeyEncoded.equals(apiKeyEncoded)) {
            throw new Exception("request does not come from client app");
        }

        managedUserVM.setLangKey("fa");
        com.adjust.api.domain.User user = userService.registerUser(managedUserVM, managedUserVM.getPassword(), true, false);


        AdjustClientDTO adjustClientDTO = new AdjustClientDTO();
        adjustClientDTO.setUsername(user.getLogin());
        adjustClientDTO.setToken(0.0);
        adjustClientDTO.setScore(0.0);
        adjustClientService.save(adjustClientDTO);

        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(user.getLogin());
        loginVM.setPassword(managedUserVM.getPassword());
        loginVM.setRememberMe(true);
        return userJWTController.authorize(loginVM);
    }


    /**
     * get adjust client for login
     * @return
     */
    @GetMapping("/adjust-clients")
    public ResponseEntity<AdjustClientDTO> getAdjustClientByClientApp() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        return ResponseEntity.ok().header("charset", "UTF-8")
            .header("charset", "UTF-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTO.getId().toString()))
            .body(adjustClientDTO);
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
    @PutMapping("/adjust-clients")
    public ResponseEntity<AdjustClientDTO> updateAdjustClientByApp(@RequestBody AdjustClientDTO adjustClientDTO) throws URISyntaxException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTOUpdatee = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        adjustClientDTO.setScore(null);
        adjustClientDTO.setToken(null);
        if (adjustClientDTO.getBirthDate() != null)
            adjustClientDTO.setBirthDate(adjustClientDTO.getBirthDate().plusDays(1));
        AdjustClientDTO adjustClientDTOUpdated = (AdjustClientDTO) ClassUpdater.updateClass(adjustClientDTO, adjustClientDTOUpdatee);
        log.debug("REST request to update AdjustClient : {}", adjustClientDTOUpdated);
        if (adjustClientDTOUpdated.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjustClientDTO result = adjustClientService.save(adjustClientDTOUpdated);
        return ResponseEntity.ok().header("charset", "UTF-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTOUpdated.getId().toString()))
            .body(result);
    }

    /**
     * buy a video by client app
     * @param videoId
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/buy-tutorial")
    public ResponseEntity<TutorialDTO> buyVideo(@RequestBody Long videoId, HttpServletResponse response) throws Exception {
        response.setHeader("charset", "utf-8");
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        AdjustTutorialDTO adjustTutorialDTO = adjustTutorialService.findOne(videoId).get();

        if (adjustClientDTO.getToken() < adjustTutorialDTO.getTokenPrice()) {
            ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("client does not have enough token");
        }

        List<TutorialDTO> tutorialDTOList = tutorialRepository.findTutorialsByClient(adjustClientRepository.findAdjustClientByUsername(userLogin).get()).stream().map(tutorialMapper::toDto).collect(Collectors.toList());

        boolean clientHasTutorial = tutorialDTOList.stream().filter((e) -> e.getVideoId() == adjustTutorialDTO.getVideoId()).collect(Collectors.toList()).iterator().hasNext();
        if (clientHasTutorial) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("client has the tutorial already");
        }

        AdjustTutorialVideoDTO adjustTutorialVideoDTO = adjustTutorialVideoService.findOne(adjustTutorialDTO.getVideoId()).get();

        TutorialVideoDTO tutorialVideoDTO = new TutorialVideoDTO();
        tutorialVideoDTO.setAdjustTutorialVideoId(adjustTutorialVideoDTO.getId());
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
        tutorialDTO.setAdjustTutorialId(adjustTutorialDTO.getId());
        tutorialDTO.setVideoId(tutorialVideoDTO.getId());
        tutorialDTO = tutorialService.save(tutorialDTO);


        adjustClientDTO.setToken(adjustClientDTO.getToken() - adjustTutorialDTO.getTokenPrice());
        adjustClientService.save(adjustClientDTO);

        return ResponseEntity.ok().header("charset", "utf-8")
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjustClientDTO.getId().toString()))
            .body(tutorialDTO);
    }

    /**
     * get the list of tutorials owned by client
     * @return
     */
    @GetMapping("/get-tutorials")
    public ResponseEntity<List<TutorialDTO>> getClientTutorials() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        List<TutorialDTO> tutorialDTOList = tutorialRepository.findTutorialsByClient(adjustClientRepository.findAdjustClientByUsername(userLogin).get()).stream().map(tutorialMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok().header("charset", "utf-8")
            .body(tutorialDTOList);
    }

    /**
     * broadcast tutorial video by videoId
     * @param videoId
     * @param jwt
     * @return
     * @throws Exception
     */
    @GetMapping("/get-tutorial-video")
    public DeferredResult<ResponseEntity<ByteArrayResource>> getClientTutorialVideo(@RequestParam("video-id") Long videoId, @RequestParam("jwt") String jwt) throws Exception {
        String userLogin = ((User) tokenProvider.getAuthentication(jwt).getPrincipal()).getUsername();
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        AdjustClient adjustClient = adjustClientMapper.toEntity(adjustClientDTO);

        Tutorial tutorial = tutorialRepository.findTutorialByClientAndAdjustTutorialId(adjustClient, videoId).get();
        if (tutorial == null) {
            throw new Exception("client has not bought the tutorial");
        }
        TutorialVideo tutorialVideo = tutorial.getVideo();

        byte[] videoByte = tutorialVideo.getContent();
        ByteArrayResource resource = new ByteArrayResource(videoByte);
        DeferredResult<ResponseEntity<ByteArrayResource>> dr = new DeferredResult<>();
        dr.setResult(ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaType.parseMediaType(tutorialVideo.getContentContentType()))
            .body(resource));
        return dr;
    }


    /**
     * {@code GET  /adjust-shoping-items} : get all the adjustShopingItems for client app.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustShopingItems in body.
     */
    @GetMapping("/adjust-shoping-items")
    public List<AdjustShopingItemDTO> getAllAdjustShopingItemsForClientApp() {
        log.debug("REST request to get all AdjustShopingItems");
        return adjustShopingItemService.findAll();
    }

    /**
     * order a product from client app using DummyOrderDTO class
     *
     * @param dummyOrderDTO
     * @return
     */
    @PostMapping("/adjust-shopings")
    public ResponseEntity<DummyOrderDTO> order(@RequestBody DummyOrderDTO dummyOrderDTO) {
        OrderDTO orderDTO = dummyOrderDTO;
        DummyCartDTO dummyCartDTO = dummyOrderDTO.getCart();
        List<DummyShopingItemDTO> dummyShopingItemDTOList = dummyCartDTO.getItems();
        CartDTO cartDTO = cartService.save(dummyCartDTO);
        Long cartId = cartDTO.getId();
        orderDTO.setCartId(cartId);
        orderService.save(orderDTO);
        dummyShopingItemDTOList.forEach((ShopingItemDTO i) -> {
            i.setCartId(cartId);
            shopingItemService.save(i);
        });
        return ResponseEntity.ok(dummyOrderDTO);
    }

    /**
     * buy a token for client by client app
     *
     * @param adjustTokenItemId
     * @return
     */
    @PostMapping("/buy-token")
    public ResponseEntity<Double> buyToken(@RequestBody String adjustTokenItemId) {
        AdjustTokensDTO adjustTokensDTO = adjustTokensResource.getAdjustTokens(Long.valueOf(adjustTokenItemId)).getBody();
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        adjustClientDTO.setToken(adjustClientDTO.getToken() + adjustTokensDTO.getToken());
        AdjustClientDTO result = adjustClientService.save(adjustClientDTO);
        return ResponseEntity.ok(result.getToken());
    }

    /**
     * get the amount of token client has
     * @return
     */
    @GetMapping("/get-client-token")
    public ResponseEntity<Double> getClientTokens() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        AdjustClientDTO adjustClientDTO = adjustClientRepository.findAdjustClientByUsername(userLogin).map(adjustClientMapper::toDto).get();
        return ResponseEntity.ok(adjustClientDTO.getToken());
    }

    /**
     * {@code GET  /adjust-tokens} : get all the adjustTokens for client app.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustTokens in body.
     */
    @GetMapping("/adjust-tokens")
    public List<AdjustTokensDTO> getAllAdjustTokensForClientApp() {
        log.debug("REST request to get all AdjustTokens");
        return adjustTokensService.findAll();
    }

    /**
     * {@code GET  /adjust-tutorials} : get all the adjustTutorials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjustTutorials in body.
     */
    @GetMapping("/adjust-tutorials")
    public List<AdjustTutorialDTO> getAllAdjustTutorialsForClientApp() {
        log.debug("REST request to get all AdjustTutorials");
        return adjustTutorialService.findAll();
    }

}