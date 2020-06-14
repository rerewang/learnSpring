package com.rere.learn.jdbcDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@RestController
@Slf4j
public class JdbcDemoApplication {
    @Autowired
    private FooDao fooDao;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(JdbcDemoApplication.class, args);
    }

    @Bean
    @Autowired
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("foo").usingGeneratedKeyColumns("id");
    }

    @RequestMapping("/insert")
    public String insert() {
        fooDao.insert();
        return "insert";
    }

    @RequestMapping("/batchInsert")
    public String batchInsert() {
        fooDao.batchInsert();
        return "batchInsert";
    }

    @RequestMapping("/list")
    public String list() {
        fooDao.list();
        return "list";
    }

    @RequestMapping("/source")
    public String source() throws SQLException {
        Connection conn = dataSource.getConnection();
        return conn.toString();
    }
}
