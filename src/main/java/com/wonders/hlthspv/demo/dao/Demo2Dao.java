package com.wonders.hlthspv.demo.dao;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.wonders.hlthspv.demo.model.Demo2;

@Component("Demo2Dao")
public class Demo2Dao {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

    public Demo2 getByName(String name) {
        return new Demo2(counter.incrementAndGet(), String.format(template, name));
    }
}