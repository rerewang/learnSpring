package com.rere.learn.nosql.cache.repository;

import com.rere.learn.nosql.cache.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
