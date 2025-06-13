package com.agosto.chapter03.spring_data_jdbc.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;

@Data
@Table("TACO")
public class Taco {

    @Id
    private Long id;

    private Date created_at = new Date();

    @NotBlank(message = "* Complete este campo")
    @Size(min=5, message = "* El nombre del taco debe tener al menos 5 caracteres.")
    private String name;

    private Integer tacoOrderKey;

    @MappedCollection(idColumn = "taco")  // FK a taco.id en ingredient_ref
    @Size(min=1, message = "* Ingrese al menos un ingrediente.")
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        int taco_key = ingredients.size();
        IngredientRef ingredientRef = new IngredientRef(ingredient.getId());
        ingredientRef.setTacoKey(taco_key);
        this.ingredients.add(ingredientRef);
    }

}