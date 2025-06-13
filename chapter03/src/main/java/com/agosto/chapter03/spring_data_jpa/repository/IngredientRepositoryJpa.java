package com.agosto.chapter03.spring_data_jpa.repository;

import com.agosto.chapter03.spring_data_jpa.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepositoryJpa extends CrudRepository<Ingredient, String> {

}
