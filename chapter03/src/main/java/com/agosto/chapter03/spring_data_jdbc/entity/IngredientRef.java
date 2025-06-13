package com.agosto.chapter03.spring_data_jdbc.entity;

import lombok.Data;

@Data
public class IngredientRef {

    public IngredientRef(String ingredientId){
        this.ingredient = ingredientId;
    }

    private String ingredient; // debe coincidir con el PK de Ingredient

    private int tacoKey; // para numerar el orden
}

