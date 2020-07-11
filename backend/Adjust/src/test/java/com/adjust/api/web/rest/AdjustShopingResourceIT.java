package com.adjust.api.web.rest;

import com.adjust.api.AdjustApp;
import com.adjust.api.domain.AdjustShoping;
import com.adjust.api.repository.AdjustShopingRepository;
import com.adjust.api.service.AdjustShopingService;
import com.adjust.api.service.dto.AdjustShopingDTO;
import com.adjust.api.service.mapper.AdjustShopingMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdjustShopingResource} REST controller.
 */
@SpringBootTest(classes = AdjustApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdjustShopingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_TOKEN = 1D;
    private static final Double UPDATED_TOKEN = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private AdjustShopingRepository adjustShopingRepository;

    @Autowired
    private AdjustShopingMapper adjustShopingMapper;

    @Autowired
    private AdjustShopingService adjustShopingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjustShopingMockMvc;

    private AdjustShoping adjustShoping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjustShoping createEntity(EntityManager em) {
        AdjustShoping adjustShoping = new AdjustShoping()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .token(DEFAULT_TOKEN)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return adjustShoping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjustShoping createUpdatedEntity(EntityManager em) {
        AdjustShoping adjustShoping = new AdjustShoping()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .token(UPDATED_TOKEN)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return adjustShoping;
    }

    @BeforeEach
    public void initTest() {
        adjustShoping = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdjustShoping() throws Exception {
        int databaseSizeBeforeCreate = adjustShopingRepository.findAll().size();
        // Create the AdjustShoping
        AdjustShopingDTO adjustShopingDTO = adjustShopingMapper.toDto(adjustShoping);
        restAdjustShopingMockMvc.perform(post("/api/adjust-shopings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustShopingDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjustShoping in the database
        List<AdjustShoping> adjustShopingList = adjustShopingRepository.findAll();
        assertThat(adjustShopingList).hasSize(databaseSizeBeforeCreate + 1);
        AdjustShoping testAdjustShoping = adjustShopingList.get(adjustShopingList.size() - 1);
        assertThat(testAdjustShoping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdjustShoping.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdjustShoping.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testAdjustShoping.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testAdjustShoping.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAdjustShoping.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAdjustShopingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjustShopingRepository.findAll().size();

        // Create the AdjustShoping with an existing ID
        adjustShoping.setId(1L);
        AdjustShopingDTO adjustShopingDTO = adjustShopingMapper.toDto(adjustShoping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjustShopingMockMvc.perform(post("/api/adjust-shopings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustShopingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjustShoping in the database
        List<AdjustShoping> adjustShopingList = adjustShopingRepository.findAll();
        assertThat(adjustShopingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdjustShopings() throws Exception {
        // Initialize the database
        adjustShopingRepository.saveAndFlush(adjustShoping);

        // Get all the adjustShopingList
        restAdjustShopingMockMvc.perform(get("/api/adjust-shopings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjustShoping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getAdjustShoping() throws Exception {
        // Initialize the database
        adjustShopingRepository.saveAndFlush(adjustShoping);

        // Get the adjustShoping
        restAdjustShopingMockMvc.perform(get("/api/adjust-shopings/{id}", adjustShoping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjustShoping.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }
    @Test
    @Transactional
    public void getNonExistingAdjustShoping() throws Exception {
        // Get the adjustShoping
        restAdjustShopingMockMvc.perform(get("/api/adjust-shopings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdjustShoping() throws Exception {
        // Initialize the database
        adjustShopingRepository.saveAndFlush(adjustShoping);

        int databaseSizeBeforeUpdate = adjustShopingRepository.findAll().size();

        // Update the adjustShoping
        AdjustShoping updatedAdjustShoping = adjustShopingRepository.findById(adjustShoping.getId()).get();
        // Disconnect from session so that the updates on updatedAdjustShoping are not directly saved in db
        em.detach(updatedAdjustShoping);
        updatedAdjustShoping
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .token(UPDATED_TOKEN)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AdjustShopingDTO adjustShopingDTO = adjustShopingMapper.toDto(updatedAdjustShoping);

        restAdjustShopingMockMvc.perform(put("/api/adjust-shopings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustShopingDTO)))
            .andExpect(status().isOk());

        // Validate the AdjustShoping in the database
        List<AdjustShoping> adjustShopingList = adjustShopingRepository.findAll();
        assertThat(adjustShopingList).hasSize(databaseSizeBeforeUpdate);
        AdjustShoping testAdjustShoping = adjustShopingList.get(adjustShopingList.size() - 1);
        assertThat(testAdjustShoping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdjustShoping.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdjustShoping.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testAdjustShoping.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testAdjustShoping.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAdjustShoping.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdjustShoping() throws Exception {
        int databaseSizeBeforeUpdate = adjustShopingRepository.findAll().size();

        // Create the AdjustShoping
        AdjustShopingDTO adjustShopingDTO = adjustShopingMapper.toDto(adjustShoping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjustShopingMockMvc.perform(put("/api/adjust-shopings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustShopingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjustShoping in the database
        List<AdjustShoping> adjustShopingList = adjustShopingRepository.findAll();
        assertThat(adjustShopingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdjustShoping() throws Exception {
        // Initialize the database
        adjustShopingRepository.saveAndFlush(adjustShoping);

        int databaseSizeBeforeDelete = adjustShopingRepository.findAll().size();

        // Delete the adjustShoping
        restAdjustShopingMockMvc.perform(delete("/api/adjust-shopings/{id}", adjustShoping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjustShoping> adjustShopingList = adjustShopingRepository.findAll();
        assertThat(adjustShopingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
