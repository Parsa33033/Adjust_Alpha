package com.adjust.api.service.dto;

import java.io.Serializable;

public class DummyMealDTO extends MealDTO implements Serializable {
    public DummyMealDTO() {}

    public DummyMealDTO(MealDTO mealDTO) {
        this.setId(mealDTO.getId());
        this.setNutritionProgramId(mealDTO.getNutritionProgramId());
        this.setBreadUnit(mealDTO.getBreadUnit());
        this.setCerealUnit(mealDTO.getCerealUnit());
        this.setFatUnit(mealDTO.getFatUnit());
        this.setFruitUnit(mealDTO.getFruitUnit());
        this.setHighFatDairyUnit(mealDTO.getHighFatDairyUnit());
        this.setHighFatMeatUnit(mealDTO.getHighFatMeatUnit());
        this.setLowFatDairyUnit(mealDTO.getLowFatDairyUnit());
        this.setLowFatMeatUnit(mealDTO.getLowFatMeatUnit());
        this.setMediumFatDairyUnit(mealDTO.getMediumFatDairyUnit());
        this.setMediumFatMeatUnit(mealDTO.getMediumFatMeatUnit());
        this.setName(mealDTO.getName());
        this.setNumber(mealDTO.getNumber());
        this.setPastaUnit(mealDTO.getPastaUnit());
        this.setRiceUnit(mealDTO.getRiceUnit());
        this.setVegetableUnit(mealDTO.getVegetableUnit());
    }
}
