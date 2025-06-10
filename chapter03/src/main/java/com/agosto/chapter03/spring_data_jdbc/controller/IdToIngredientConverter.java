package com.agosto.chapter03.spring_data_jdbc.controller;

import com.agosto.chapter03.spring_data_jdbc.entity.Ingredient;
import com.agosto.chapter03.spring_data_jdbc.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("idToIngredientConverterSpringDataJdbc")
public class IdToIngredientConverter implements Converter<String, Ingredient> {

    final IngredientRepository ingredientRepository;

    @Autowired
    IdToIngredientConverter(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }
    @Override
    public Ingredient convert(String source)
    {
        return ingredientRepository.findById(source).orElse(null);
    }

}
