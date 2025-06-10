package com.agosto.chapter03.spring_data_jdbc.repository;

import com.agosto.chapter03.spring_data_jdbc.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
