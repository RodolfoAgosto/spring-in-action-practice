package com.agosto.chapter03.spring_data_jpa.controller;

import com.agosto.chapter03.spring_data_jpa.entity.TacoOrder;
import com.agosto.chapter03.spring_data_jpa.repository.TacoOrderRepositoryJpa;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller("OrderControllerSpringDataJpa")
@RequestMapping("/springDataJpaTacoController/tacoOrders")
@SessionAttributes("tacoOrderSpringDataJpa")
public class OrderController {

    private final TacoOrderRepositoryJpa tacoOrderRepositoryJpa;

    public OrderController(TacoOrderRepositoryJpa tacoOrderRepositoryJpa){
        this.tacoOrderRepositoryJpa = tacoOrderRepositoryJpa;
    }

    @GetMapping("/current")
    public String showTacoOrder(){
        return "taco-order-springdatajpa";
    }

    @PostMapping
    public String saveTacoOrder(@Valid @ModelAttribute("tacoOrderSpringDataJpa") TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus){
        if (errors.hasErrors())
            return "taco-order-springdatajpa";
        tacoOrderRepositoryJpa.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

}

