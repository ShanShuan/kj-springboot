package com.wonders.hlthspv.demo.rest.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.wonders.core.db.page.PageRequest;
import com.wonders.core.rest.req.Header;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.test.HttpTest;
import com.wonders.core.util.JsonMapper;
import com.wonders.hlthspv.demo.model.Demo;

@SuppressWarnings("unchecked")
public class TestDemo {
	public static void main(String[] args) {

		// testJsonMapper();
		// testPageJson();
		// testIfexists();
		// testGetById();
//		testPageSelect();
		// testSelect();
//		testSave();
		testAlarm();
		System.out.println("=====================华丽的分割线==========");
	}

	public static void testIfexists() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试Ifexists=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<Demo> req = new RequestMsg<Demo>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		Demo demo = new Demo();
		demo.setDemoDate(new Date());
		demo.setCreateddateStr("2018-01-01 22:12:12");
		demo.setId("ded8b73a4d27498ca9909b9358517ff4");
		req.setBody(demo);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/demo/ifexists", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testGetById() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试getById=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<String> req = new RequestMsg<String>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		req.setBody("ded8b73a4d27498ca9909b9358517ff4");

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/demo/getById", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testPageSelect() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试getById=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<PageRequest> req = new RequestMsg<PageRequest>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("demoname", "S-XK");
		PageRequest pageRequest = new PageRequest(2, 2, filter, null);
		req.setBody(pageRequest);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/demo/pageSelect", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testSelect() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试select=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<Map<String, Object>> req = new RequestMsg<Map<String, Object>>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		Map<String, Object> filter = new HashMap<String, Object>();
		// filter.put("demoname", "S-XK");
		req.setBody(filter);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/demo/select", arg, null);
		System.out.println(httpEntity);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}
	
	public static void testAlarm() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;
		
		System.out.println("===========开始测试testAlarm=========");
		Map<String, String> arg = new HashMap<String, String>();
		
		// 拼装请求对象
		RequestMsg<Map<String, Object>> req = new RequestMsg<Map<String, Object>>();
		// 请求头
		Header header = new Header();
		header.setKey("xxxxxxxxxxxxxxxx");
		header.setToken("1111111111111111");
		req.setHeader(header);
		
		// 拼装参数对象
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("demoname", "S-XK");
		req.setBody(filter);
		
		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);
		
		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/alarm/test", arg, null);
		System.out.println(httpEntity);
		String content = hf.getContent(httpEntity);
		hf.close();
		
		System.out.println(content);
	}

	public static void testSave() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试save=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<Demo> req = new RequestMsg<Demo>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		Demo demo = new Demo();
		demo.setWhocreateid("f2qasdfasfwerqwe");
		demo.setDemoname("sssssRASP.docx");
		demo.setDemoDate(new Date());
		demo.setUpdateddate(new Date());
		demo.setId("33333333333");
		req.setBody(demo);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://127.0.0.1:8443/demo/save", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testJsonMapper() {
		RequestMsg<Demo> req = new RequestMsg<Demo>();
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("23fsxcvzxfsdf");
		Demo demo = new Demo();
		demo.setDemoDate(new Date());
		demo.setCreateddateStr("2018-01-01 22:12:12");
		demo.setId("asdfasdfas");
		req.setBody(demo);
		req.setHeader(header);
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		req = JsonMapper.buildNormalMapper().fromJson(reqstr, RequestMsg.class, Demo.class);
		System.out.println(req.getBody());

	}

	public static void testPageJson() {

		RequestMsg<PageRequest> req = new RequestMsg<PageRequest>();
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("23fsxcvzxfsdf");
		PageRequest pageRequest = new PageRequest();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("aa", "111");
		args.put("bbb", "222");
		pageRequest.setFilters(args);
		pageRequest.setPageNum(2);
		pageRequest.setPageSize(10);
		req.setBody(pageRequest);
		req.setHeader(header);
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		req = JsonMapper.buildNormalMapper().fromJson(reqstr, RequestMsg.class, PageRequest.class);
		System.out.println(req.getBody());
	}
}