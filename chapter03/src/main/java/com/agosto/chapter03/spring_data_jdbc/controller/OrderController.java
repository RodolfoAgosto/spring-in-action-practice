package com.agosto.chapter03.spring_data_jdbc.controller;

import com.agosto.chapter03.spring_data_jdbc.entity.TacoOrder;
import com.agosto.chapter03.spring_data_jdbc.repository.TacoOrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Date;

@Controller("OrderControllerSpringDataJdbc")
@RequestMapping("/springDataJdbcTacoController/tacoOrders")
@SessionAttributes("tacoOrderSpringDataJdbc")
public class OrderController {

    private TacoOrderRepository orderRepo;

    public OrderController(TacoOrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String showTacoOrder(){
        return "taco-order-springdatajdbc";
    }

    @PostMapping
    public String saveTacoOrder(@Valid @ModelAttribute("tacoOrderSpringDataJdbc") TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus){
        if (errors.hasErrors())
            return "taco-order-springdatajdbc";
        tacoOrder.setPlacedAt(new Date());
        orderRepo.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
