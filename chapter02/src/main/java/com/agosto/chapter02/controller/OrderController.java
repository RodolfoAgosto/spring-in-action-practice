package com.agosto.chapter02.controller;

import com.agosto.chapter02.entity.TacoOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/tacoOrders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String showOrder(){
       return "tacoOrder";
    }

    @PostMapping
    public String saveOrder(@Valid @ModelAttribute TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus){

        if (errors.hasErrors()){
            return "tacoOrder";
        }
        log.info("Orden guardada: {}", tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }


}
