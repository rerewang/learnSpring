package com.rere.learn.nosql.cache.repository;

import com.rere.learn.nosql.cache.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
