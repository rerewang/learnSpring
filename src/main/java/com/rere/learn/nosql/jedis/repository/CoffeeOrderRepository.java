package com.rere.learn.nosql.jedis.repository;

import com.rere.learn.nosql.jedis.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}
