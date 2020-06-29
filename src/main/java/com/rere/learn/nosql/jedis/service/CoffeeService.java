package com.rere.learn.nosql.jedis.service;

import com.rere.learn.nosql.jedis.model.Coffee;
import com.rere.learn.nosql.jedis.model.CoffeeOrder;
import com.rere.learn.nosql.jedis.model.OrderState;
import com.rere.learn.nosql.jedis.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    //Optional可以包含Null或指定的类型
    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(
                Example.of(Coffee.builder().name(name).build(), matcher)
        );
        return coffee;
    }

    public Coffee createCoffee(String name, Money price) {
        Coffee coffee = Coffee.builder()
                .name(name)
                .price(price)
                .build();
        Coffee saved = coffeeRepository.save(coffee);
        log.info("New Coffee: {}", saved);
        return saved;
    }
}
