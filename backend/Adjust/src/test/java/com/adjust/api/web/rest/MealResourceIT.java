package com.adjust.api.web.rest;

import com.adjust.api.AdjustApp;
import com.adjust.api.domain.Meal;
import com.adjust.api.repository.MealRepository;
import com.adjust.api.service.MealService;
import com.adjust.api.service.dto.MealDTO;
import com.adjust.api.service.mapper.MealMapper;

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
 * Integration tests for the {@link MealResource} REST controller.
 */
@SpringBootTest(classes = AdjustApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MealResourceIT {

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

    private static final Integer DEFAULT_HIGH_FAT_MEAT_UNIT = 1;
    private static final Integer UPDATED_HIGH_FAT_MEAT_UNIT = 2;

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
    private MealRepository mealRepository;

    @Autowired
    private MealMapper mealMapper;

    @Autowired
    private MealService mealService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMealMockMvc;

    private Meal meal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meal createEntity(EntityManager em) {
        Meal meal = new Meal()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .lowFatDairyUnit(DEFAULT_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(DEFAULT_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(DEFAULT_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(DEFAULT_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(DEFAULT_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnit(DEFAULT_HIGH_FAT_MEAT_UNIT)
            .breadUnit(DEFAULT_BREAD_UNIT)
            .cerealUnit(DEFAULT_CEREAL_UNIT)
            .riceUnit(DEFAULT_RICE_UNIT)
            .pastaUnit(DEFAULT_PASTA_UNIT)
            .fruitUnit(DEFAULT_FRUIT_UNIT)
            .vegetableUnit(DEFAULT_VEGETABLE_UNIT)
            .fatUnit(DEFAULT_FAT_UNIT);
        return meal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meal createUpdatedEntity(EntityManager em) {
        Meal meal = new Meal()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .lowFatDairyUnit(UPDATED_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(UPDATED_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(UPDATED_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(UPDATED_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(UPDATED_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnit(UPDATED_HIGH_FAT_MEAT_UNIT)
            .breadUnit(UPDATED_BREAD_UNIT)
            .cerealUnit(UPDATED_CEREAL_UNIT)
            .riceUnit(UPDATED_RICE_UNIT)
            .pastaUnit(UPDATED_PASTA_UNIT)
            .fruitUnit(UPDATED_FRUIT_UNIT)
            .vegetableUnit(UPDATED_VEGETABLE_UNIT)
            .fatUnit(UPDATED_FAT_UNIT);
        return meal;
    }

    @BeforeEach
    public void initTest() {
        meal = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeal() throws Exception {
        int databaseSizeBeforeCreate = mealRepository.findAll().size();
        // Create the Meal
        MealDTO mealDTO = mealMapper.toDto(meal);
        restMealMockMvc.perform(post("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mealDTO)))
            .andExpect(status().isCreated());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeCreate + 1);
        Meal testMeal = mealList.get(mealList.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeal.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMeal.getLowFatDairyUnit()).isEqualTo(DEFAULT_LOW_FAT_DAIRY_UNIT);
        assertThat(testMeal.getMediumFatDairyUnit()).isEqualTo(DEFAULT_MEDIUM_FAT_DAIRY_UNIT);
        assertThat(testMeal.getHighFatDairyUnit()).isEqualTo(DEFAULT_HIGH_FAT_DAIRY_UNIT);
        assertThat(testMeal.getLowFatMeatUnit()).isEqualTo(DEFAULT_LOW_FAT_MEAT_UNIT);
        assertThat(testMeal.getMediumFatMeatUnit()).isEqualTo(DEFAULT_MEDIUM_FAT_MEAT_UNIT);
        assertThat(testMeal.getHighFatMeatUnit()).isEqualTo(DEFAULT_HIGH_FAT_MEAT_UNIT);
        assertThat(testMeal.getBreadUnit()).isEqualTo(DEFAULT_BREAD_UNIT);
        assertThat(testMeal.getCerealUnit()).isEqualTo(DEFAULT_CEREAL_UNIT);
        assertThat(testMeal.getRiceUnit()).isEqualTo(DEFAULT_RICE_UNIT);
        assertThat(testMeal.getPastaUnit()).isEqualTo(DEFAULT_PASTA_UNIT);
        assertThat(testMeal.getFruitUnit()).isEqualTo(DEFAULT_FRUIT_UNIT);
        assertThat(testMeal.getVegetableUnit()).isEqualTo(DEFAULT_VEGETABLE_UNIT);
        assertThat(testMeal.getFatUnit()).isEqualTo(DEFAULT_FAT_UNIT);
    }

    @Test
    @Transactional
    public void createMealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mealRepository.findAll().size();

        // Create the Meal with an existing ID
        meal.setId(1L);
        MealDTO mealDTO = mealMapper.toDto(meal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMealMockMvc.perform(post("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mealDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeals() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get all the mealList
        restMealMockMvc.perform(get("/api/meals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].lowFatDairyUnit").value(hasItem(DEFAULT_LOW_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].mediumFatDairyUnit").value(hasItem(DEFAULT_MEDIUM_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].highFatDairyUnit").value(hasItem(DEFAULT_HIGH_FAT_DAIRY_UNIT)))
            .andExpect(jsonPath("$.[*].lowFatMeatUnit").value(hasItem(DEFAULT_LOW_FAT_MEAT_UNIT)))
            .andExpect(jsonPath("$.[*].mediumFatMeatUnit").value(hasItem(DEFAULT_MEDIUM_FAT_MEAT_UNIT)))
            .andExpect(jsonPath("$.[*].highFatMeatUnit").value(hasItem(DEFAULT_HIGH_FAT_MEAT_UNIT)))
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
    public void getMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", meal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(meal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.lowFatDairyUnit").value(DEFAULT_LOW_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.mediumFatDairyUnit").value(DEFAULT_MEDIUM_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.highFatDairyUnit").value(DEFAULT_HIGH_FAT_DAIRY_UNIT))
            .andExpect(jsonPath("$.lowFatMeatUnit").value(DEFAULT_LOW_FAT_MEAT_UNIT))
            .andExpect(jsonPath("$.mediumFatMeatUnit").value(DEFAULT_MEDIUM_FAT_MEAT_UNIT))
            .andExpect(jsonPath("$.highFatMeatUnit").value(DEFAULT_HIGH_FAT_MEAT_UNIT))
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
    public void getNonExistingMeal() throws Exception {
        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        int databaseSizeBeforeUpdate = mealRepository.findAll().size();

        // Update the meal
        Meal updatedMeal = mealRepository.findById(meal.getId()).get();
        // Disconnect from session so that the updates on updatedMeal are not directly saved in db
        em.detach(updatedMeal);
        updatedMeal
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .lowFatDairyUnit(UPDATED_LOW_FAT_DAIRY_UNIT)
            .mediumFatDairyUnit(UPDATED_MEDIUM_FAT_DAIRY_UNIT)
            .highFatDairyUnit(UPDATED_HIGH_FAT_DAIRY_UNIT)
            .lowFatMeatUnit(UPDATED_LOW_FAT_MEAT_UNIT)
            .mediumFatMeatUnit(UPDATED_MEDIUM_FAT_MEAT_UNIT)
            .highFatMeatUnit(UPDATED_HIGH_FAT_MEAT_UNIT)
            .breadUnit(UPDATED_BREAD_UNIT)
            .cerealUnit(UPDATED_CEREAL_UNIT)
            .riceUnit(UPDATED_RICE_UNIT)
            .pastaUnit(UPDATED_PASTA_UNIT)
            .fruitUnit(UPDATED_FRUIT_UNIT)
            .vegetableUnit(UPDATED_VEGETABLE_UNIT)
            .fatUnit(UPDATED_FAT_UNIT);
        MealDTO mealDTO = mealMapper.toDto(updatedMeal);

        restMealMockMvc.perform(put("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mealDTO)))
            .andExpect(status().isOk());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeUpdate);
        Meal testMeal = mealList.get(mealList.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeal.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMeal.getLowFatDairyUnit()).isEqualTo(UPDATED_LOW_FAT_DAIRY_UNIT);
        assertThat(testMeal.getMediumFatDairyUnit()).isEqualTo(UPDATED_MEDIUM_FAT_DAIRY_UNIT);
        assertThat(testMeal.getHighFatDairyUnit()).isEqualTo(UPDATED_HIGH_FAT_DAIRY_UNIT);
        assertThat(testMeal.getLowFatMeatUnit()).isEqualTo(UPDATED_LOW_FAT_MEAT_UNIT);
        assertThat(testMeal.getMediumFatMeatUnit()).isEqualTo(UPDATED_MEDIUM_FAT_MEAT_UNIT);
        assertThat(testMeal.getHighFatMeatUnit()).isEqualTo(UPDATED_HIGH_FAT_MEAT_UNIT);
        assertThat(testMeal.getBreadUnit()).isEqualTo(UPDATED_BREAD_UNIT);
        assertThat(testMeal.getCerealUnit()).isEqualTo(UPDATED_CEREAL_UNIT);
        assertThat(testMeal.getRiceUnit()).isEqualTo(UPDATED_RICE_UNIT);
        assertThat(testMeal.getPastaUnit()).isEqualTo(UPDATED_PASTA_UNIT);
        assertThat(testMeal.getFruitUnit()).isEqualTo(UPDATED_FRUIT_UNIT);
        assertThat(testMeal.getVegetableUnit()).isEqualTo(UPDATED_VEGETABLE_UNIT);
        assertThat(testMeal.getFatUnit()).isEqualTo(UPDATED_FAT_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingMeal() throws Exception {
        int databaseSizeBeforeUpdate = mealRepository.findAll().size();

        // Create the Meal
        MealDTO mealDTO = mealMapper.toDto(meal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealMockMvc.perform(put("/api/meals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mealDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meal in the database
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        int databaseSizeBeforeDelete = mealRepository.findAll().size();

        // Delete the meal
        restMealMockMvc.perform(delete("/api/meals/{id}", meal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meal> mealList = mealRepository.findAll();
        assertThat(mealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
