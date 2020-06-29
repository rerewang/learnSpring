package com.rere.learn.nosql.jedis.repository;

import com.rere.learn.nosql.jedis.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
