package com.rere.learn.nosql.jedis;

import com.rere.learn.nosql.jedis.service.CoffeeOrderService;
import com.rere.learn.nosql.jedis.service.CoffeeService;
import com.rere.learn.nosql.jedis.model.Coffee;
import com.rere.learn.nosql.jedis.model.CoffeeOrder;
import com.rere.learn.nosql.jedis.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@EnableJpaRepositories
public class JedisDemoApplication implements ApplicationRunner {
    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    public static void main(String[] args) {
        SpringApplication.run(JedisDemoApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("redis") //把redis打头的config注入进来
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod="close")
    public JedisPool jedisPool(@Value("${redis.host}") String host){
        return new JedisPool(jedisPoolConfig(), host);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(jedisPoolConfig.toString());

        initOrders();

        try (Jedis jedis = jedisPool.getResource()) {


            coffeeService.findAllCoffee().forEach(coffee -> {
                jedis.hset("menu",
                        coffee.getName(),
                        Long.toString(coffee.getPrice().getAmountMinorLong()));
            });

            Map<String, String> menu = jedis.hgetAll("menu");
            log.info("Menu: {}", menu);

            String price = jedis.hget("menu", "espresso");
            log.info("espresso - {}",
                    Money.ofMinor(CurrencyUnit.of("CNY"), Long.parseLong(price)));
        }
    }

    public void initOrders() {
        Coffee espresso = coffeeService.createCoffee("espresso", Money.of(CurrencyUnit.of("CNY"), 20.0));
        Coffee latte = coffeeService.createCoffee("latte", Money.of(CurrencyUnit.of("CNY"), 30.0));

        CoffeeOrder order = coffeeOrderService.createOrder("rere", espresso,latte);
        log.info("Order: {}", order);

        Boolean flag = coffeeOrderService.updateState(order, OrderState.BREWED);
        log.info("Update Flag: {}", flag);
    }
}
