package com.agosto.chapter03.jdbc_template.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;
    private Date createdAt = new Date();
    @NotBlank(message = "* Complete este campo")
    @Size(min=5, message = "* El nombre del taco debe tener al menos 5 caracteres.")
    private String name;
    @Size(min=1, message = "* Ingrese al menos un ingrediente.")
    List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient){
        ingredients.add(new IngredientRef(ingredient.getId()));
    }

}
