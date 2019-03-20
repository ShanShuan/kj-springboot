package com.wonders.sys.user.rest.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.wonders.core.rest.req.Header;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.test.HttpTest;
import com.wonders.core.util.JsonMapper;
import com.wonders.core.util.SecurityUtils;
import com.wonders.sys.user.rest.bean.LoginUserBean;

public class TestLoginUser {
	public static void main(String[] args) {
//		testlogin();
		testlogout();
		System.out.println("=====================华丽的分割线==========");
	}

	public static void testlogin() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试testlogin=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<LoginUserBean> req = new RequestMsg<LoginUserBean>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		LoginUserBean body = new LoginUserBean();
		body.setPassword(SecurityUtils.encodeSHA1("111111"));
		body.setUsername("32000000wangzhe");
		req.setBody(body);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/user/login", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testlogout() {

		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试testlogout=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<LoginUserBean> req = new RequestMsg<LoginUserBean>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("TGC-NEW-7ajB0Sumbx2a1ojXHNOd5iAxXUSDKXvLctid5NtnZZO2amwv0j");
		req.setHeader(header);

		// 拼装参数对象
		req.setBody(new LoginUserBean());
		
		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://localhost:8443/user/logout", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}
}