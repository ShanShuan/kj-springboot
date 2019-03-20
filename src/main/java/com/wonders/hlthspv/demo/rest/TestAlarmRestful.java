package com.wonders.hlthspv.demo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.core.rest.rsp.ResponseMsg;

@RestController
@RequestMapping("/alarm")
public class TestAlarmRestful {
	protected static Logger logger = LoggerFactory.getLogger(TestAlarmRestful.class);

	@RequestMapping(path = "/test", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg test(@RequestParam(value = "req") String req) {
		logger.info(req);
		return ResponseMsg.success("ok");
	}
}