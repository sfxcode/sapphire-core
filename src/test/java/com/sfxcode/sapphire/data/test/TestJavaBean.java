package com.sfxcode.sapphire.data.test;

import java.util.Date;

public class TestJavaBean {

    protected String name = "test";
    private int age = 42;
    private Date date = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
