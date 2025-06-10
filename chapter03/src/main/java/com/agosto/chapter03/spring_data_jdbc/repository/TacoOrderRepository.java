package com.agosto.chapter03.spring_data_jdbc.repository;

import com.agosto.chapter03.spring_data_jdbc.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {

}
