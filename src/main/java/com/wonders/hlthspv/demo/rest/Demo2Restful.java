package com.wonders.hlthspv.demo.rest;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.hlthspv.demo.manager.Demo2Manager;
import com.wonders.hlthspv.demo.model.Demo2;

@RestController
@RequestMapping("/rest2")
public class Demo2Restful {
	@Resource(name="Demo2Manager")
	private Demo2Manager demo2Manager;

	@RequestMapping(path = "/greeting", method = RequestMethod.POST)
	@ResponseBody
	public Demo2 greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		return demo2Manager.getByName(name);
	}
}