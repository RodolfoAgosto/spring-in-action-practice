package com.agosto.chapter02.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Taco {

    @NotBlank(message = "* Complete este campo")
    @Size(min=5, message = "* El nombre del taco debe tener al menos 5 caracteres.")
    private String name;

    @Size(min=1, message = "* Ingrese al menos un ingrediente.")
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

}
