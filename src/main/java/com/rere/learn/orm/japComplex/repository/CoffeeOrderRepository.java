package com.rere.learn.orm.japComplex.repository;

import com.rere.learn.orm.japComplex.model.CoffeeOrder;

import java.util.List;

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByItems_Name(String name);
    List<CoffeeOrder> findByCustomerOrderById(String customer);
}
