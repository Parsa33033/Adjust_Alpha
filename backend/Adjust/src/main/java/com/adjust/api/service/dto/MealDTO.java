package com.adjust.api.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.adjust.api.domain.Meal} entity.
 */
public class MealDTO implements Serializable {
    
    private Long id;

    private String name;

    private Integer number;

    private Integer lowFatDairyUnit;

    private Integer mediumFatDairyUnit;

    private Integer highFatDairyUnit;

    private Integer lowFatMeatUnit;

    private Integer mediumFatMeatUnit;

    private Integer highFatMeatUnti;

    private Integer breadUnit;

    private Integer cerealUnit;

    private Integer riceUnit;

    private Integer pastaUnit;

    private Integer fruitUnit;

    private Integer vegetableUnit;

    private Integer fatUnit;


    private Long nutritionProgramId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLowFatDairyUnit() {
        return lowFatDairyUnit;
    }

    public void setLowFatDairyUnit(Integer lowFatDairyUnit) {
        this.lowFatDairyUnit = lowFatDairyUnit;
    }

    public Integer getMediumFatDairyUnit() {
        return mediumFatDairyUnit;
    }

    public void setMediumFatDairyUnit(Integer mediumFatDairyUnit) {
        this.mediumFatDairyUnit = mediumFatDairyUnit;
    }

    public Integer getHighFatDairyUnit() {
        return highFatDairyUnit;
    }

    public void setHighFatDairyUnit(Integer highFatDairyUnit) {
        this.highFatDairyUnit = highFatDairyUnit;
    }

    public Integer getLowFatMeatUnit() {
        return lowFatMeatUnit;
    }

    public void setLowFatMeatUnit(Integer lowFatMeatUnit) {
        this.lowFatMeatUnit = lowFatMeatUnit;
    }

    public Integer getMediumFatMeatUnit() {
        return mediumFatMeatUnit;
    }

    public void setMediumFatMeatUnit(Integer mediumFatMeatUnit) {
        this.mediumFatMeatUnit = mediumFatMeatUnit;
    }

    public Integer getHighFatMeatUnti() {
        return highFatMeatUnti;
    }

    public void setHighFatMeatUnti(Integer highFatMeatUnti) {
        this.highFatMeatUnti = highFatMeatUnti;
    }

    public Integer getBreadUnit() {
        return breadUnit;
    }

    public void setBreadUnit(Integer breadUnit) {
        this.breadUnit = breadUnit;
    }

    public Integer getCerealUnit() {
        return cerealUnit;
    }

    public void setCerealUnit(Integer cerealUnit) {
        this.cerealUnit = cerealUnit;
    }

    public Integer getRiceUnit() {
        return riceUnit;
    }

    public void setRiceUnit(Integer riceUnit) {
        this.riceUnit = riceUnit;
    }

    public Integer getPastaUnit() {
        return pastaUnit;
    }

    public void setPastaUnit(Integer pastaUnit) {
        this.pastaUnit = pastaUnit;
    }

    public Integer getFruitUnit() {
        return fruitUnit;
    }

    public void setFruitUnit(Integer fruitUnit) {
        this.fruitUnit = fruitUnit;
    }

    public Integer getVegetableUnit() {
        return vegetableUnit;
    }

    public void setVegetableUnit(Integer vegetableUnit) {
        this.vegetableUnit = vegetableUnit;
    }

    public Integer getFatUnit() {
        return fatUnit;
    }

    public void setFatUnit(Integer fatUnit) {
        this.fatUnit = fatUnit;
    }

    public Long getNutritionProgramId() {
        return nutritionProgramId;
    }

    public void setNutritionProgramId(Long nutritionProgramId) {
        this.nutritionProgramId = nutritionProgramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealDTO)) {
            return false;
        }

        return id != null && id.equals(((MealDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", number=" + getNumber() +
            ", lowFatDairyUnit=" + getLowFatDairyUnit() +
            ", mediumFatDairyUnit=" + getMediumFatDairyUnit() +
            ", highFatDairyUnit=" + getHighFatDairyUnit() +
            ", lowFatMeatUnit=" + getLowFatMeatUnit() +
            ", mediumFatMeatUnit=" + getMediumFatMeatUnit() +
            ", highFatMeatUnti=" + getHighFatMeatUnti() +
            ", breadUnit=" + getBreadUnit() +
            ", cerealUnit=" + getCerealUnit() +
            ", riceUnit=" + getRiceUnit() +
            ", pastaUnit=" + getPastaUnit() +
            ", fruitUnit=" + getFruitUnit() +
            ", vegetableUnit=" + getVegetableUnit() +
            ", fatUnit=" + getFatUnit() +
            ", nutritionProgramId=" + getNutritionProgramId() +
            "}";
    }
}
