package com.kais.bean;

import java.util.Date;

/**
 * @author Kais
 * @create 2021-07-06-14:00
 */
public class Customer {

    private int id;
    private int age;
    private String name;
    private Date birth;
    private String email;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Customer(int id, int age, String name, Date birth, String email, String sex) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.sex = sex;
    }
}
