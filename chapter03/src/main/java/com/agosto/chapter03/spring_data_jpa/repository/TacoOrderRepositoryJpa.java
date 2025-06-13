package com.agosto.chapter03.spring_data_jpa.repository;

import com.agosto.chapter03.spring_data_jpa.entity.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface TacoOrderRepositoryJpa extends CrudRepository<TacoOrder , Long> {

}
