package com.agosto.chapter03.jdbc_template.repository;

import com.agosto.chapter03.jdbc_template.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
