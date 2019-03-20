package com.wonders.hlthspv.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.core.db.manager.BaseManager;
import com.wonders.core.rest.BasePublicRestful;
import com.wonders.hlthspv.demo.manager.DemoManager;
import com.wonders.hlthspv.demo.model.Demo;

/**
 * @author silent
 */
@RestController
@RequestMapping("/demo")
public class DemoRestful extends BasePublicRestful<Demo, String> {
	@Autowired
	private DemoManager demoManager;

	@Override
	public BaseManager<Demo, String> getBaseManager() {
		return demoManager;
	}

	@Override
	public Class<Demo> getEntityClass() {
		return Demo.class;
	}

	@Override
	public Class<String> getPkClass() {
		return String.class;
	}
}