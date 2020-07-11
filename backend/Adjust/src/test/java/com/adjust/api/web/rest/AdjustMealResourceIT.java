package com.adjust.api.web.rest;

import com.adjust.api.AdjustApp;
import com.adjust.api.domain.AdjustMeal;
import com.adjust.api.repository.AdjustMealRepository;
import com.adjust.api.service.AdjustMealService;
import com.adjust.api.service.dto.AdjustMealDTO;
import com.adjust.api.service.mapper.AdjustMealMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdjustMealResource} REST controller.
 */
@SpringBootTest(classes = AdjustApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdjustMealResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_LOW_FAT_DAIRY_UNIT = 1;
    private static final Integer UPDATED_LOW_FAT_DAIRY_UNIT = 2;

    private static final Integer DEFAULT_MEDIUM_FAT_DAIRY_UNIT = 1;
    private static final Integer UPDATED_MEDIUM_FAT_DAIRY_UNIT = 2;

    private static final Integer DEFAULT_HIGH_FAT_DAIRY_UNIT = 1;
    private static final Integer UPDATED_HIGH_FAT_DAIRY_UNIT = 2;

    private static final Integer DEFAULT_LOW_FAT_MEAT_UNIT = 1;
    private static final Integer UPDATED_LOW_FAT_MEAT_UNIT = 2;

    private static final Integer DEFAULT_MEDIUM_FAT_MEAT_UNIT = 1;
    private static final Integer UPDATED_MEDIUM_FAT_MEAT_UNIT = 2;

    private static final Integer DEFAULT_HIGH_FAT_MEAT_UNTI = 1;
    private static final Integer UPDATED_HIGH_FAT_MEAT_UNTI = 2;

    private static final Integer DEFAULT_BREAD_UNIT = 1;
    private static final Integer UPDATED_BREAD_UNIT = 2;

    private static final Integer DEFAULT_CEREAL_UNIT = 1;
    private static final Integer UPDATED_CEREAL_UNIT = 2;

    private static final Integer DEFAULT_RICE_UNIT = 1;
    private static final Integer UPDATED_RICE_UNIT = 2;

    private static final Integer DEFAULT_PASTA_UNIT = 1;
    private static final Integer UPDATED_PASTA_UNIT = 2;

    private static final Integer DEFAULT_FRUIT_UNIT = 1;
    private static final Integer UPDATED_FRUIT_UNIT = 2;

    private static final Integer DEFAULT_VEGETABLE_UNIT = 1;
    private static final Integer UPDATED_VEGETABLE_UNIT = 2;

    private static final Integer DEFAULT_FAT_UNIT = 1;
    private static final Integer UPDATED_FAT_UNIT = 2;

    @Autowired
    private AdjustMealRepository adjustMealRepository;

    @Autowired
    private AdjustMealMapper adjustMealMapper;

    @Autowired
    private AdjustMealService adjustMealService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjustMealMockMvc;

    private AdjustMeal adjustMeal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjustMeal createEntity(EntityManager em) {
        AdjustMeal adjustMeal = new AdjustMeal()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .lowFatDairyUnit(DEFAULT_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(DEFAULT_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(DEFAULT_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(DEFAULT_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(DEFAULT_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnti(DEFAULT_HIGH_FAT_MEAT_UNTI)
            .breadUnit(DEFAULT_BREAD_UNIT)
            .cerealUnit(DEFAULT_CEREAL_UNIT)
            .riceUnit(DEFAULT_RICE_UNIT)
            .pastaUnit(DEFAULT_PASTA_UNIT)
            .fruitUnit(DEFAULT_FRUIT_UNIT)
            .vegetableUnit(DEFAULT_VEGETABLE_UNIT)
            .fatUnit(DEFAULT_FAT_UNIT);
        return adjustMeal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjustMeal createUpdatedEntity(EntityManager em) {
        AdjustMeal adjustMeal = new AdjustMeal()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .lowFatDairyUnit(UPDATED_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(UPDATED_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(UPDATED_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(UPDATED_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(UPDATED_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnti(UPDATED_HIGH_FAT_MEAT_UNTI)
            .breadUnit(UPDATED_BREAD_UNIT)
            .cerealUnit(UPDATED_CEREAL_UNIT)
            .riceUnit(UPDATED_RICE_UNIT)
            .pastaUnit(UPDATED_PASTA_UNIT)
            .fruitUnit(UPDATED_FRUIT_UNIT)
            .vegetableUnit(UPDATED_VEGETABLE_UNIT)
            .fatUnit(UPDATED_FAT_UNIT);
        return adjustMeal;
    }

    @BeforeEach
    public void initTest() {
        adjustMeal = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdjustMeal() throws Exception {
        int databaseSizeBeforeCreate = adjustMealRepository.findAll().size();
        // Create the AdjustMeal
        AdjustMealDTO adjustMealDTO = adjustMealMapper.toDto(adjustMeal);
        restAdjustMealMockMvc.perform(post("/api/adjust-meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustMealDTO)))
            .andExpect(status().isCreated());

        // Validate the AdjustMeal in the database
        List<AdjustMeal> adjustMealList = adjustMealRepository.findAll();
        assertThat(adjustMealList).hasSize(databaseSizeBeforeCreate + 1);
        AdjustMeal testAdjustMeal = adjustMealList.get(adjustMealList.size() - 1);
        assertThat(testAdjustMeal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdjustMeal.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAdjustMeal.getLowFatDairyUnit()).isEqualTo(DEFAULT_LOW_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getMediumFatDairyUnit()).isEqualTo(DEFAULT_MEDIUM_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getHighFatDairyUnit()).isEqualTo(DEFAULT_HIGH_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getLowFatMeatUnit()).isEqualTo(DEFAULT_LOW_FAT_MEAT_UNIT);
        assertThat(testAdjustMeal.getMediumFatMeatUnit()).isEqualTo(DEFAULT_MEDIUM_FAT_MEAT_UNIT);
        assertThat(testAdjustMeal.getHighFatMeatUnti()).isEqualTo(DEFAULT_HIGH_FAT_MEAT_UNTI);
        assertThat(testAdjustMeal.getBreadUnit()).isEqualTo(DEFAULT_BREAD_UNIT);
        assertThat(testAdjustMeal.getCerealUnit()).isEqualTo(DEFAULT_CEREAL_UNIT);
        assertThat(testAdjustMeal.getRiceUnit()).isEqualTo(DEFAULT_RICE_UNIT);
        assertThat(testAdjustMeal.getPastaUnit()).isEqualTo(DEFAULT_PASTA_UNIT);
        assertThat(testAdjustMeal.getFruitUnit()).isEqualTo(DEFAULT_FRUIT_UNIT);
        assertThat(testAdjustMeal.getVegetableUnit()).isEqualTo(DEFAULT_VEGETABLE_UNIT);
        assertThat(testAdjustMeal.getFatUnit()).isEqualTo(DEFAULT_FAT_UNIT);
    }

    @Test
    @Transactional
    public void createAdjustMealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjustMealRepository.findAll().size();

        // Create the AdjustMeal with an existing ID
        adjustMeal.setId(1L);
        AdjustMealDTO adjustMealDTO = adjustMealMapper.toDto(adjustMeal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjustMealMockMvc.perform(post("/api/adjust-meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustMealDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjustMeal in the database
        List<AdjustMeal> adjustMealList = adjustMealRepository.findAll();
        assertThat(adjustMealList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdjustMeals() throws Exception {
        // Initialize the database
        adjustMealRepository.saveAndFlush(adjustMeal);

        // Get all the adjustMealList
        restAdjustMealMockMvc.perform(get("/api/adjust-meals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjustMeal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].lowFatDairyUnit").value(hasItem(DEFAULT_LOW_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].mediumFatDairyUnit").value(hasItem(DEFAULT_MEDIUM_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].highFatDairyUnit").value(hasItem(DEFAULT_HIGH_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].lowFatMeatUnit").value(hasItem(DEFAULT_LOW_FAT_MEAT_UNIT)))
            .andExpect(jsonPath("$.[*].mediumFatMeatUnit").value(hasItem(DEFAULT_MEDIUM_FAT_MEAT_UNIT)))
            .andExpect(jsonPath("$.[*].highFatMeatUnti").value(hasItem(DEFAULT_HIGH_FAT_MEAT_UNTI)))
            .andExpect(jsonPath("$.[*].breadUnit").value(hasItem(DEFAULT_BREAD_UNIT)))
            .andExpect(jsonPath("$.[*].cerealUnit").value(hasItem(DEFAULT_CEREAL_UNIT)))
            .andExpect(jsonPath("$.[*].riceUnit").value(hasItem(DEFAULT_RICE_UNIT)))
            .andExpect(jsonPath("$.[*].pastaUnit").value(hasItem(DEFAULT_PASTA_UNIT)))
            .andExpect(jsonPath("$.[*].fruitUnit").value(hasItem(DEFAULT_FRUIT_UNIT)))
            .andExpect(jsonPath("$.[*].vegetableUnit").value(hasItem(DEFAULT_VEGETABLE_UNIT)))
            .andExpect(jsonPath("$.[*].fatUnit").value(hasItem(DEFAULT_FAT_UNIT)));
    }
    
    @Test
    @Transactional
    public void getAdjustMeal() throws Exception {
        // Initialize the database
        adjustMealRepository.saveAndFlush(adjustMeal);

        // Get the adjustMeal
        restAdjustMealMockMvc.perform(get("/api/adjust-meals/{id}", adjustMeal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjustMeal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.lowFatDairyUnit").value(DEFAULT_LOW_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.mediumFatDairyUnit").value(DEFAULT_MEDIUM_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.highFatDairyUnit").value(DEFAULT_HIGH_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.lowFatMeatUnit").value(DEFAULT_LOW_FAT_MEAT_UNIT))
            .andExpect(jsonPath("$.mediumFatMeatUnit").value(DEFAULT_MEDIUM_FAT_MEAT_UNIT))
            .andExpect(jsonPath("$.highFatMeatUnti").value(DEFAULT_HIGH_FAT_MEAT_UNTI))
            .andExpect(jsonPath("$.breadUnit").value(DEFAULT_BREAD_UNIT))
            .andExpect(jsonPath("$.cerealUnit").value(DEFAULT_CEREAL_UNIT))
            .andExpect(jsonPath("$.riceUnit").value(DEFAULT_RICE_UNIT))
            .andExpect(jsonPath("$.pastaUnit").value(DEFAULT_PASTA_UNIT))
            .andExpect(jsonPath("$.fruitUnit").value(DEFAULT_FRUIT_UNIT))
            .andExpect(jsonPath("$.vegetableUnit").value(DEFAULT_VEGETABLE_UNIT))
            .andExpect(jsonPath("$.fatUnit").value(DEFAULT_FAT_UNIT));
    }
    @Test
    @Transactional
    public void getNonExistingAdjustMeal() throws Exception {
        // Get the adjustMeal
        restAdjustMealMockMvc.perform(get("/api/adjust-meals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdjustMeal() throws Exception {
        // Initialize the database
        adjustMealRepository.saveAndFlush(adjustMeal);

        int databaseSizeBeforeUpdate = adjustMealRepository.findAll().size();

        // Update the adjustMeal
        AdjustMeal updatedAdjustMeal = adjustMealRepository.findById(adjustMeal.getId()).get();
        // Disconnect from session so that the updates on updatedAdjustMeal are not directly saved in db
        em.detach(updatedAdjustMeal);
        updatedAdjustMeal
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .lowFatDairyUnit(UPDATED_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(UPDATED_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(UPDATED_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(UPDATED_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(UPDATED_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnti(UPDATED_HIGH_FAT_MEAT_UNTI)
            .breadUnit(UPDATED_BREAD_UNIT)
            .cerealUnit(UPDATED_CEREAL_UNIT)
            .riceUnit(UPDATED_RICE_UNIT)
            .pastaUnit(UPDATED_PASTA_UNIT)
            .fruitUnit(UPDATED_FRUIT_UNIT)
            .vegetableUnit(UPDATED_VEGETABLE_UNIT)
            .fatUnit(UPDATED_FAT_UNIT);
        AdjustMealDTO adjustMealDTO = adjustMealMapper.toDto(updatedAdjustMeal);

        restAdjustMealMockMvc.perform(put("/api/adjust-meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustMealDTO)))
            .andExpect(status().isOk());

        // Validate the AdjustMeal in the database
        List<AdjustMeal> adjustMealList = adjustMealRepository.findAll();
        assertThat(adjustMealList).hasSize(databaseSizeBeforeUpdate);
        AdjustMeal testAdjustMeal = adjustMealList.get(adjustMealList.size() - 1);
        assertThat(testAdjustMeal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdjustMeal.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAdjustMeal.getLowFatDairyUnit()).isEqualTo(UPDATED_LOW_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getMediumFatDairyUnit()).isEqualTo(UPDATED_MEDIUM_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getHighFatDairyUnit()).isEqualTo(UPDATED_HIGH_FAT_DAIRY_UNIT);
        assertThat(testAdjustMeal.getLowFatMeatUnit()).isEqualTo(UPDATED_LOW_FAT_MEAT_UNIT);
        assertThat(testAdjustMeal.getMediumFatMeatUnit()).isEqualTo(UPDATED_MEDIUM_FAT_MEAT_UNIT);
        assertThat(testAdjustMeal.getHighFatMeatUnti()).isEqualTo(UPDATED_HIGH_FAT_MEAT_UNTI);
        assertThat(testAdjustMeal.getBreadUnit()).isEqualTo(UPDATED_BREAD_UNIT);
        assertThat(testAdjustMeal.getCerealUnit()).isEqualTo(UPDATED_CEREAL_UNIT);
        assertThat(testAdjustMeal.getRiceUnit()).isEqualTo(UPDATED_RICE_UNIT);
        assertThat(testAdjustMeal.getPastaUnit()).isEqualTo(UPDATED_PASTA_UNIT);
        assertThat(testAdjustMeal.getFruitUnit()).isEqualTo(UPDATED_FRUIT_UNIT);
        assertThat(testAdjustMeal.getVegetableUnit()).isEqualTo(UPDATED_VEGETABLE_UNIT);
        assertThat(testAdjustMeal.getFatUnit()).isEqualTo(UPDATED_FAT_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdjustMeal() throws Exception {
        int databaseSizeBeforeUpdate = adjustMealRepository.findAll().size();

        // Create the AdjustMeal
        AdjustMealDTO adjustMealDTO = adjustMealMapper.toDto(adjustMeal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjustMealMockMvc.perform(put("/api/adjust-meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adjustMealDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdjustMeal in the database
        List<AdjustMeal> adjustMealList = adjustMealRepository.findAll();
        assertThat(adjustMealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdjustMeal() throws Exception {
        // Initialize the database
        adjustMealRepository.saveAndFlush(adjustMeal);

        int databaseSizeBeforeDelete = adjustMealRepository.findAll().size();

        // Delete the adjustMeal
        restAdjustMealMockMvc.perform(delete("/api/adjust-meals/{id}", adjustMeal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjustMeal> adjustMealList = adjustMealRepository.findAll();
        assertThat(adjustMealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
