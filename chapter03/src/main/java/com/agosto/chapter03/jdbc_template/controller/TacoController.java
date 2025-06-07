package com.agosto.chapter03.jdbc_template.controller;

import com.agosto.chapter03.jdbc_template.entity.Ingredient;
import com.agosto.chapter03.jdbc_template.entity.Taco;
import com.agosto.chapter03.jdbc_template.entity.TacoOrder;
import com.agosto.chapter03.jdbc_template.repository.IngredientRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jdbcTacoController/newTaco")
@SessionAttributes("tacoOrder")
@Slf4j
public class TacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public  TacoController(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    private void  fillIngredientsToModel(Model model){
        for (Ingredient.Type type : Ingredient.Type.values()) {
            model.addAttribute(type.name().toLowerCase(), filterIngredientByType(type));
        }
    }

    private List<Ingredient> filterIngredientByType(Ingredient.Type type){
        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();
        return  ingredients.stream()
                .filter(x -> x.getType() == type)
                .collect(Collectors.toList());
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder tacoOrder(){
        return new TacoOrder();
    }

    @ModelAttribute("taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String newTaco(){
        log.info("New Taco OK!");
        return "newTaco";
    }

    @PostMapping
    public String addTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder ){
        if(errors.hasErrors())
            return "newTaco";
        tacoOrder.addTaco(taco);
        log.info("Procesando taco " + taco);
        return "redirect:/jdbcTacoController/tacoOrders/current";
    }

}
