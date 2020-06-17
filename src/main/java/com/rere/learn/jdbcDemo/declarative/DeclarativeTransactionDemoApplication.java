package com.rere.learn.jdbcDemo.declarative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {
    @Autowired
    private FooService fooService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fooService.insertRecord();
        log.info("aaa {}",
                jdbcTemplate.queryForObject("select count(1) from foo where bar = 'bar-aaa'", long.class)
        );

        try {
            fooService.insertThenRollback();
        } catch (Exception e) {
            log.info("bbb {}",
                    jdbcTemplate.queryForObject("select count(1) from foo where bar = 'bar-bbb'", long.class)
            );
        }

        try {
            fooService.invokeInsertThenRollback();
        } catch (Exception e) {
            log.info("bbb {}",
                    jdbcTemplate.queryForObject("select count(1) from foo where bar = 'bar-bbb'", long.class)
            );
        }
    }
}
