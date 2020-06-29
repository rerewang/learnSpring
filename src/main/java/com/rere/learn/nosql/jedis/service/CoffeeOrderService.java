package com.rere.learn.nosql.jedis.service;

import com.rere.learn.nosql.jedis.model.Coffee;
import com.rere.learn.nosql.jedis.model.CoffeeOrder;
import com.rere.learn.nosql.jedis.model.OrderState;
import com.rere.learn.nosql.jedis.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, Coffee...coffee) {
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .state(OrderState.INIT)
                .build();
        CoffeeOrder saved = coffeeOrderRepository.save(coffeeOrder);
        log.info("New Order: {}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order:{}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderRepository.save(order);
        log.info("Update Order: {}", order);
        return true;
    }
}
