package com.rere.learn.orm.jpa.repository;

import com.rere.learn.orm.jpa.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
