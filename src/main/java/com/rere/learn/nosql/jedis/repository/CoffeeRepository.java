package com.rere.learn.nosql.jedis.repository;

import com.rere.learn.nosql.jedis.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
