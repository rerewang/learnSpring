package com.rere.learn.nosql.jedis.repository;

import com.rere.learn.nosql.jedis.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
