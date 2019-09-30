package com.wzp.jian3.db.model;

import javax.persistence.*;

public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Test(Integer id) {
        this.id = id;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }
}