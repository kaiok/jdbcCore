package com.kais.bean;

import com.mysql.jdbc.Blob;

/**
 * @author Kais
 * @create 2021-08-20-15:37
 * @desc 演员（BLOB字段测试）表
 */
public class Actor {

    private int id;
    private String name;
    private Blob photo;

    public Actor() {
    }

    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Actor(int id, String name, Blob photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
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

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }
}
