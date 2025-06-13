package com.agosto.chapter03.spring_data_jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date created_at = new Date();

    @NotBlank(message = "* Complete este campo")
    @Size(min=5, message = "* El nombre del taco debe tener al menos 5 caracteres.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TACO_ORDER", nullable = false)
    private TacoOrder tacoOrder;

    @ManyToMany
    @Size(min=1, message = "* Ingrese al menos un ingrediente.")
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    @Override
    public String toString() {
        return "Taco{id=" + id + ", name=" + name + "}";
    }
}