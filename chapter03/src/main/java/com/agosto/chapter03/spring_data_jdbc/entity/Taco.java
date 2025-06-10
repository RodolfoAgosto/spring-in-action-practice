package com.agosto.chapter03.spring_data_jdbc.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;

@Data
@Table("Taco")
public class Taco {

    @Id
    private Long id;
    private Date created_at = new Date();

    @NotBlank(message = "* Complete este campo")
    @Size(min=5, message = "* El nombre del taco debe tener al menos 5 caracteres.")
    private String name;

    @Size(min=1, message = "* Ingrese al menos un ingrediente.")
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(new IngredientRef(ingredient.getId()));
    }


}
