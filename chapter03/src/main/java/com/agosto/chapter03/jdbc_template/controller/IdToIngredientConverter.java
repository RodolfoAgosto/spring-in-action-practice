package com.agosto.chapter03.jdbc_template.controller;

import com.agosto.chapter03.jdbc_template.entity.Ingredient;
import com.agosto.chapter03.jdbc_template.repository.JdbcIngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToIngredientConverter implements Converter<String, Ingredient> {

    final JdbcIngredientRepository jdbcIngredientRepository;

    @Autowired
    IdToIngredientConverter(JdbcIngredientRepository jdbcIngredientRepository){
        this.jdbcIngredientRepository = jdbcIngredientRepository;
    }
    @Override
    public Ingredient convert(String source)
    {
        return  jdbcIngredientRepository.findById(source).orElse(null);
    }

}
