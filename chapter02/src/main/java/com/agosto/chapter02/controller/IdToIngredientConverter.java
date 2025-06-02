package com.agosto.chapter02.controller;

import com.agosto.chapter02.entity.Ingredient;
import com.agosto.chapter02.entity.Ingredient.Type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IdToIngredientConverter implements Converter<String, Ingredient> {
    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public IdToIngredientConverter() {

        ingredients.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredients.put("COTO", new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredients.put("GRBF", new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredients.put("CARN", new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredients.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredients.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredients.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredients.put("JACK", new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredients.put("SLSA", new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredients.put("SRCR", new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

    }

    @Override
    public Ingredient convert(String source) {
        return ingredients.get(source);
    }

}