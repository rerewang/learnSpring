package com.rere.learn.nosql.cache;

import com.rere.learn.nosql.cache.service.CoffeeOrderService;
import com.rere.learn.nosql.cache.service.CoffeeService;
import com.rere.learn.nosql.cache.model.Coffee;
import com.rere.learn.nosql.cache.model.CoffeeOrder;
import com.rere.learn.nosql.cache.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableCaching(proxyTargetClass = true)
public class CacheDemoApplication implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;

    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initCoffees();

        log.info("Count: {}", coffeeService.findAllCoffee().size());
        for (int i = 0; i < 10; i++) {
            log.info("Reading from cache.");
            coffeeService.findAllCoffee();
        }
        coffeeService.reloadCoffee();
        log.info("reading after refresh.");
        coffeeService.findAllCoffee().forEach(c -> log.info("coffee: {}", c.getName()));
    }

    public void initCoffees() {
        Coffee espresso = coffeeService.createCoffee("espresso", Money.of(CurrencyUnit.of("CNY"), 20.0));
        Coffee latte = coffeeService.createCoffee("latte", Money.of(CurrencyUnit.of("CNY"), 30.0));
    }
}
