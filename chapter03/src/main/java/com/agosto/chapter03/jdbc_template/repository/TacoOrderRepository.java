package com.agosto.chapter03.jdbc_template.repository;

import com.agosto.chapter03.jdbc_template.entity.Taco;
import com.agosto.chapter03.jdbc_template.entity.TacoOrder;

import java.util.Optional;

public interface TacoOrderRepository {

    Optional<TacoOrder> findById(Long id);

    TacoOrder save(TacoOrder tacoOrder);

}
