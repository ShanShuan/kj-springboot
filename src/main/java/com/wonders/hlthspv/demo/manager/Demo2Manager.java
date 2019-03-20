package com.wonders.hlthspv.demo.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.hlthspv.demo.dao.Demo2Dao;
import com.wonders.hlthspv.demo.model.Demo2;

@Component("Demo2Manager")
public class Demo2Manager {

	@Resource(name = "Demo2Dao")
	private Demo2Dao demo2Dao;

	public Demo2 getByName(String name) {
		return demo2Dao.getByName(name);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void save(Demo2 entity) {
	}
}