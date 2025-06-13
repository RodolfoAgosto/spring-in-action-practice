package com.agosto.chapter03.spring_data_jpa.controller;

import com.agosto.chapter03.spring_data_jpa.entity.Ingredient;
import com.agosto.chapter03.spring_data_jpa.repository.IngredientRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToIngredientConverterJpa implements Converter<String, Ingredient> {
    final IngredientRepositoryJpa ingredientRepositoryJpa;

    @Autowired
    public IdToIngredientConverterJpa(IngredientRepositoryJpa ingredientRepositoryJpa){
        this.ingredientRepositoryJpa = ingredientRepositoryJpa;
    }
    @Override
    public Ingredient convert(String source) {
        return ingredientRepositoryJpa.findById(source).orElse(null);
    }
}
