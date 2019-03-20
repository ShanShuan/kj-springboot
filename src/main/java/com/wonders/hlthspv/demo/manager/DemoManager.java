package com.wonders.hlthspv.demo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.core.db.validation.annotation.DataValidate;
import com.wonders.hlthspv.demo.dao.DemoDao;
import com.wonders.hlthspv.demo.model.Demo;

@Component
public class DemoManager extends BaseManager<Demo, String> {

	@Autowired
	private DemoDao demoDao;

	@Override
	public BaseDao<Demo, String> getBaseDao() {
		return demoDao;
	}

	@Override
	@DataValidate
	public void save(Demo entity) {
		super.save(entity);
	}
}