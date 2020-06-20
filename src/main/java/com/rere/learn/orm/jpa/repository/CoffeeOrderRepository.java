package com.rere.learn.orm.jpa.repository;

import com.rere.learn.orm.jpa.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
