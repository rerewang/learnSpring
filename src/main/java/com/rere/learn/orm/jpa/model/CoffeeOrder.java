package com.rere.learn.orm.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String customer;
    @ManyToMany
    @JoinTable(name = "t_order_coffe")
    private List<Coffee> items;
    @Column(nullable = false)
    private Integer state;
    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;
    @Column
    @CreationTimestamp
    private Date updateTime;
}
