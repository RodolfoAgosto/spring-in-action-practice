package com.agosto.chapter03.spring_data_jdbc.controller;

import com.agosto.chapter03.jdbc_template.entity.TacoOrder;
import com.agosto.chapter03.jdbc_template.repository.JdbcTacoOrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller("OrderControllerSpringDataJdbc")
@RequestMapping("/springDataJdbcTacoController/tacoOrders")
@SessionAttributes("springDataTacoOrder")
public class OrderController {

    private final JdbcTacoOrderRepository jdbcTacoOrderRepository;

    public OrderController(JdbcTacoOrderRepository jdbcTacoOrderRepository){
        this.jdbcTacoOrderRepository = jdbcTacoOrderRepository;
    }

    @GetMapping("/current")
    public String showTacoOrder(){
        return "taco-order-jdbctemplate";
    }

    @PostMapping
    public String saveTacoOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus){
        if (errors.hasErrors())
            return "tacoOrder";
        jdbcTacoOrderRepository.save(tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

}
