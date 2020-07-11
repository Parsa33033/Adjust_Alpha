package com.adjust.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Meal.
 */
@Entity
@Table(name = "meal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Integer number;

    @Column(name = "low_fat_dairy_unit")
    private Integer lowFatDairyUnit;

    @Column(name = "medium_fat_dairy_unit")
    private Integer mediumFatDairyUnit;

    @Column(name = "high_fat_dairy_unit")
    private Integer highFatDairyUnit;

    @Column(name = "low_fat_meat_unit")
    private Integer lowFatMeatUnit;

    @Column(name = "medium_fat_meat_unit")
    private Integer mediumFatMeatUnit;

    @Column(name = "high_fat_meat_unti")
    private Integer highFatMeatUnti;

    @Column(name = "bread_unit")
    private Integer breadUnit;

    @Column(name = "cereal_unit")
    private Integer cerealUnit;

    @Column(name = "rice_unit")
    private Integer riceUnit;

    @Column(name = "pasta_unit")
    private Integer pastaUnit;

    @Column(name = "fruit_unit")
    private Integer fruitUnit;

    @Column(name = "vegetable_unit")
    private Integer vegetableUnit;

    @Column(name = "fat_unit")
    private Integer fatUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = "meals", allowSetters = true)
    private NutritionProgram nutritionProgram;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Meal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public Meal number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLowFatDairyUnit() {
        return lowFatDairyUnit;
    }

    public Meal lowFatDairyUnit(Integer lowFatDairyUnit) {
        this.lowFatDairyUnit = lowFatDairyUnit;
        return this;
    }

    public void setLowFatDairyUnit(Integer lowFatDairyUnit) {
        this.lowFatDairyUnit = lowFatDairyUnit;
    }

    public Integer getMediumFatDairyUnit() {
        return mediumFatDairyUnit;
    }

    public Meal mediumFatDairyUnit(Integer mediumFatDairyUnit) {
        this.mediumFatDairyUnit = mediumFatDairyUnit;
        return this;
    }

    public void setMediumFatDairyUnit(Integer mediumFatDairyUnit) {
        this.mediumFatDairyUnit = mediumFatDairyUnit;
    }

    public Integer getHighFatDairyUnit() {
        return highFatDairyUnit;
    }

    public Meal highFatDairyUnit(Integer highFatDairyUnit) {
        this.highFatDairyUnit = highFatDairyUnit;
        return this;
    }

    public void setHighFatDairyUnit(Integer highFatDairyUnit) {
        this.highFatDairyUnit = highFatDairyUnit;
    }

    public Integer getLowFatMeatUnit() {
        return lowFatMeatUnit;
    }

    public Meal lowFatMeatUnit(Integer lowFatMeatUnit) {
        this.lowFatMeatUnit = lowFatMeatUnit;
        return this;
    }

    public void setLowFatMeatUnit(Integer lowFatMeatUnit) {
        this.lowFatMeatUnit = lowFatMeatUnit;
    }

    public Integer getMediumFatMeatUnit() {
        return mediumFatMeatUnit;
    }

    public Meal mediumFatMeatUnit(Integer mediumFatMeatUnit) {
        this.mediumFatMeatUnit = mediumFatMeatUnit;
        return this;
    }

    public void setMediumFatMeatUnit(Integer mediumFatMeatUnit) {
        this.mediumFatMeatUnit = mediumFatMeatUnit;
    }

    public Integer getHighFatMeatUnti() {
        return highFatMeatUnti;
    }

    public Meal highFatMeatUnti(Integer highFatMeatUnti) {
        this.highFatMeatUnti = highFatMeatUnti;
        return this;
    }

    public void setHighFatMeatUnti(Integer highFatMeatUnti) {
        this.highFatMeatUnti = highFatMeatUnti;
    }

    public Integer getBreadUnit() {
        return breadUnit;
    }

    public Meal breadUnit(Integer breadUnit) {
        this.breadUnit = breadUnit;
        return this;
    }

    public void setBreadUnit(Integer breadUnit) {
        this.breadUnit = breadUnit;
    }

    public Integer getCerealUnit() {
        return cerealUnit;
    }

    public Meal cerealUnit(Integer cerealUnit) {
        this.cerealUnit = cerealUnit;
        return this;
    }

    public void setCerealUnit(Integer cerealUnit) {
        this.cerealUnit = cerealUnit;
    }

    public Integer getRiceUnit() {
        return riceUnit;
    }

    public Meal riceUnit(Integer riceUnit) {
        this.riceUnit = riceUnit;
        return this;
    }

    public void setRiceUnit(Integer riceUnit) {
        this.riceUnit = riceUnit;
    }

    public Integer getPastaUnit() {
        return pastaUnit;
    }

    public Meal pastaUnit(Integer pastaUnit) {
        this.pastaUnit = pastaUnit;
        return this;
    }

    public void setPastaUnit(Integer pastaUnit) {
        this.pastaUnit = pastaUnit;
    }

    public Integer getFruitUnit() {
        return fruitUnit;
    }

    public Meal fruitUnit(Integer fruitUnit) {
        this.fruitUnit = fruitUnit;
        return this;
    }

    public void setFruitUnit(Integer fruitUnit) {
        this.fruitUnit = fruitUnit;
    }

    public Integer getVegetableUnit() {
        return vegetableUnit;
    }

    public Meal vegetableUnit(Integer vegetableUnit) {
        this.vegetableUnit = vegetableUnit;
        return this;
    }

    public void setVegetableUnit(Integer vegetableUnit) {
        this.vegetableUnit = vegetableUnit;
    }

    public Integer getFatUnit() {
        return fatUnit;
    }

    public Meal fatUnit(Integer fatUnit) {
        this.fatUnit = fatUnit;
        return this;
    }

    public void setFatUnit(Integer fatUnit) {
        this.fatUnit = fatUnit;
    }

    public NutritionProgram getNutritionProgram() {
        return nutritionProgram;
    }

    public Meal nutritionProgram(NutritionProgram nutritionProgram) {
        this.nutritionProgram = nutritionProgram;
        return this;
    }

    public void setNutritionProgram(NutritionProgram nutritionProgram) {
        this.nutritionProgram = nutritionProgram;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meal)) {
            return false;
        }
        return id != null && id.equals(((Meal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Meal{" +
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
            "}";
    }
}
