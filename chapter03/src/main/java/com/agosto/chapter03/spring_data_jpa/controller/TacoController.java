package com.agosto.chapter03.spring_data_jpa.controller;

import com.agosto.chapter03.spring_data_jpa.entity.Ingredient;
import com.agosto.chapter03.spring_data_jpa.entity.Taco;
import com.agosto.chapter03.spring_data_jpa.entity.TacoOrder;
import com.agosto.chapter03.spring_data_jpa.repository.IngredientRepositoryJpa;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller("TacoControllerSpringDataJpa")
@RequestMapping("/springDataJpaTacoController/newTaco")
@SessionAttributes("tacoOrderSpringDataJpa")
@Slf4j
public class TacoController {

    private final IngredientRepositoryJpa ingredientRepositoryJpa;

    @Autowired
    public TacoController(IngredientRepositoryJpa ingredientRepositoryJpa){
        this.ingredientRepositoryJpa = ingredientRepositoryJpa;
    }

    @ModelAttribute
    private void  fillIngredientsToModel(Model model){
        for (Ingredient.Type type : Ingredient.Type.values()) {
            model.addAttribute(type.name().toLowerCase(), filterIngredientByType(type));
        }
    }

    private List<Ingredient> filterIngredientByType(Ingredient.Type type){
        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepositoryJpa.findAll();
        return  ingredients.stream()
                .filter(x -> x.getType() == type)
                .collect(Collectors.toList());
    }

    @ModelAttribute("tacoOrderSpringDataJpa")
    public TacoOrder tacoOrder(){
        return new TacoOrder();
    }

    @ModelAttribute("tacoSpringDataJpa")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String newTaco(){
        log.info("New Taco OK!");
        return "new-taco-springdatajpa";
    }

    @PostMapping
    public String addTaco(@Valid @ModelAttribute("tacoSpringDataJpa") com.agosto.chapter03.spring_data_jpa.entity.Taco taco, Errors errors, @ModelAttribute("tacoOrderSpringDataJpa") com.agosto.chapter03.spring_data_jpa.entity.TacoOrder tacoOrder ){
        if(errors.hasErrors())
            return "new-taco-springdatajpa";
        tacoOrder.addTaco(taco);
        log.info("Procesando taco " + taco);
        return "redirect:/springDataJpaTacoController/tacoOrders/current";
    }

}
