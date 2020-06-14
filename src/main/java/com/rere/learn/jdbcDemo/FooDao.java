package com.rere.learn.jdbcDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FooDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insert() {
        String bar = "bar1";
        jdbcTemplate.update("insert into foo (bar) value (?)", bar);

        HashMap<String, String> row = new HashMap<>();
        row.put("bar", "bar2");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of bar2: {}", id.longValue());
    }

    public void list() {
        //count
        log.info("Count is {}",
            jdbcTemplate.queryForObject("select count(1) from foo", Long.class)
        );

        //list
        List<String> list = jdbcTemplate.queryForList("select bar from foo", String.class);
        list.forEach(s -> log.info("Bar: {}", s));

        //list object
        List<Foo> fooList = jdbcTemplate.query("select * from foo", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet rs, int rowNum) throws SQLException{
                return Foo.builder()
                        .id(rs.getLong("id"))
                        .bar(rs.getString("bar"))
                        .build();
            }
        });
        fooList.forEach(foo -> log.info("Foo is {}", foo));
    }

    public void batchInsert() {
        jdbcTemplate.batchUpdate("insert into foo (bar) value (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, "bar" + i);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });

        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(100L).bar("bar100").build());
        list.add(Foo.builder().id(101L).bar("bar101").build());
        namedParameterJdbcTemplate.batchUpdate("insert into foo (id, bar) value (:id, :bar)",
                SqlParameterSourceUtils.createBatch(list));
    }
}
