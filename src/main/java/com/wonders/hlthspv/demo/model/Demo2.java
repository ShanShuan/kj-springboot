package com.wonders.hlthspv.demo.model;
public class Demo2 {

    private long id;
    private String content;

    public Demo2(){}
    public Demo2(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}