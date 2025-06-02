package com.agosto.chapter02.controller;

import com.agosto.chapter02.entity.Ingredient;
import com.agosto.chapter02.entity.Ingredient.*;
import com.agosto.chapter02.entity.Taco;
import com.agosto.chapter02.entity.TacoOrder;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/newTaco")
@Slf4j
@SessionAttributes("tacoOrder")
public class TacoController {

    @GetMapping
    public String showForm(){
        return "newTaco";
    }

    @PostMapping
    public String addTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){
        if (errors.hasErrors()){
            return "newTaco";
        }
        tacoOrder.addTaco(taco);
        log.info("Procesando taco " + taco);
        return "redirect:/tacoOrders/current";
    }

    private List<Ingredient> filterByType(Type type, List<Ingredient> ingredients){
        return ingredients.stream()
                .filter(x -> x.getType() == type)
                .collect(Collectors.toList());
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        for (Type type: Ingredient.Type.values()) {
            model.addAttribute(type.name().toLowerCase(), filterByType(type, ingredients));
        }
    }

    @ModelAttribute
    public TacoOrder tacoOrder(){
        return new TacoOrder();
    }

    @ModelAttribute
    public Taco taco(){
        return new Taco();
    }

}
