package com.wonders.recorder.video.rest.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.wonders.core.db.page.PageRequest;
import com.wonders.core.rest.req.Header;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;
import com.wonders.core.rest.test.HttpTest;
import com.wonders.core.util.ByteUtils;
import com.wonders.core.util.JFileUtils;
import com.wonders.core.util.JsonMapper;
import com.wonders.recorder.video.model.FileDemo;

public class TestFileDemo {

	public static void testIfexists() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试Ifexists=========");
		Map<String, String> arg = new HashMap<String, String>();

		// 拼装请求对象
		RequestMsg<FileDemo> req = new RequestMsg<FileDemo>();
		// 请求头
		Header header = new Header();
		header.setKey("32fadfasdf");
		header.setToken("123456");
		req.setHeader(header);

		// 拼装参数对象
		FileDemo demo = new FileDemo();
		demo.setCreateddateStr("2018-01-01 22:12:12");
		demo.setId("ded8b73a4d27498ca9909b9358517ff4");
		req.setBody(demo);

		// 转json
		String reqstr = JsonMapper.buildNormalMapper().toJson(req);
		System.out.println(reqstr);

		arg.put("req", reqstr);
		httpEntity = hf.post("https://127.0.0.1:8443/filedemo/ifexists", arg, null);
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
		httpEntity = hf.post("https://localhost:8443/filedemo/getById", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testPageSelect() {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;

		System.out.println("===========开始测试testPageSelect=========");
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
		httpEntity = hf.post("https://127.0.0.1:8443/filedemo/pageSelect", arg, null);
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
	}

	public static void testSave() {
		try {
			HttpTest hf = new HttpTest();
			HttpEntity httpEntity;

			System.out.println("===========开始测试save=========");
			Map<String, String> arg = new HashMap<String, String>();

			// 拼装请求对象
			RequestMsg<FileDemo> req = new RequestMsg<FileDemo>();
			// 请求头
			Header header = new Header();
			header.setKey("32fadfasdf");
			header.setToken("123456");
			req.setHeader(header);

			// 获取文件
			File file = new File("C:\\Users\\silent\\Desktop\\百度RASP.docx");
			InputStream is = new FileInputStream(file);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();
			// 拼装参数对象
			FileDemo demo = new FileDemo();
			demo.setWhocreateid("f2qasdfasfwerqwe");
			demo.setDemoname("百度RASP.docx");
			demo.setWhoupdateid("33333333333");
			demo.setUpdateddate(new Date());
			demo.setId("ded8b73a4d27498ca9909b9358517ff4");
			req.setBody(demo);

			// 非断点续传写法
			demo.setFilename("百度RASP.docx");
			demo.setContent(bytes);
			// 转json
			String reqstr = JsonMapper.buildNormalMapper().toJson(req);
			System.out.println(reqstr);

			arg.put("req", reqstr);
			httpEntity = hf.post("https://127.0.0.1:8443/filedemo/uploadbp", arg, null);
			String content = hf.getContent(httpEntity);
			hf.close();

			System.out.println(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testBrokenSave() {
		try {
			// 获取文件
			File file = new File("C:\\Users\\silent\\Desktop\\卫监整体功能清单.docx");
			InputStream is = new FileInputStream(file);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			is.close();

			// 断点续传写法
			// 第i次传输，取(i-1)*JFileUtils.UPLOADBP_BLOCK_LENGTH
			// 长度JFileUtils.UPLOADBP_BLOCK_LENGTH数组进行传输。
			int uploadcount = (int) Math
					.ceil(((new Double(bytes.length) / new Double(JFileUtils.UPLOADBP_BLOCK_LENGTH))));
			for (int i = 1; i <= uploadcount; i++) {
				// int i = 146;
				HttpTest hf = new HttpTest();
				HttpEntity httpEntity;
				System.out.println("===========开始测试save=========");
				Map<String, String> arg = new HashMap<String, String>();

				// 拼装请求对象
				RequestMsg<FileDemo> req = new RequestMsg<FileDemo>();
				// 请求头
				Header header = new Header();
				header.setKey("32fadfasdf");
				header.setToken("123456");
				req.setHeader(header);

				// 拼装参数对象
				FileDemo demo = new FileDemo();
				demo.setWhocreateid("f2qasdfasfwerqwe");
				demo.setDemoname("卫监整体功能清单.docx");
				demo.setWhoupdateid("33333333333");
				demo.setUpdateddate(new Date());
				demo.setId("cccccccccccc");
				req.setBody(demo);

				int bytelength = i < uploadcount ? JFileUtils.UPLOADBP_BLOCK_LENGTH
						: (bytes.length - (uploadcount - 1) * JFileUtils.UPLOADBP_BLOCK_LENGTH);
				byte[] desByte = new byte[bytelength];
				System.arraycopy(bytes, (i - 1) * JFileUtils.UPLOADBP_BLOCK_LENGTH, desByte, 0, bytelength);
				demo.setBroken(true);
				demo.setStart((i - 1) * JFileUtils.UPLOADBP_BLOCK_LENGTH);
				demo.setLast(i >= uploadcount ? true : false);
				demo.setFilename("卫监整体功能清单.docx");
				demo.setContent(desByte);
				// 转json
				String reqstr = JsonMapper.buildNormalMapper().toJson(req);
				System.out.println(reqstr);

				arg.put("req", reqstr);
				httpEntity = hf.post("https://127.0.0.1:8443/filedemo/uploadbp", arg, null);
				String content = hf.getContent(httpEntity);

				System.out.println("第" + i + "次传输成功" + content);
				hf.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testBrokenDownload() {
		try {

			int filesize = 0;
			int start = 0;
			int i = 1;
			while (filesize == 0 || filesize > start) {
				HttpTest hf = new HttpTest();
				HttpEntity httpEntity;
				System.out.println("===========开始测试downloadbp=========");
				Map<String, String> arg = new HashMap<String, String>();

				// 拼装请求对象
				RequestMsg<FileDemo> req = new RequestMsg<FileDemo>();
				// 请求头
				Header header = new Header();
				header.setKey("32fadfasdf");
				header.setToken("123456");
				req.setHeader(header);

				// 拼装参数对象
				FileDemo demo = new FileDemo();
				// 一次下载写法
//				demo.setId("ded8b73a4d27498ca9909b9358517ff4");
//				demo.setBroken(false);

				// 断点下载写法
				demo.setId("aaaaaaaaaaaaaaaaaaa");
				demo.setBroken(true);
				demo.setStart(start);

				req.setBody(demo);
				// 转json
				String reqstr = JsonMapper.buildNormalMapper().toJson(req);
				System.out.println(reqstr);

				arg.put("req", reqstr);
				httpEntity = hf.post("https://127.0.0.1:8443/filedemo/downloadbp", arg, null);
				String content = hf.getContent(httpEntity);

				System.out.println("第" + i + "次传输成功" + content);
				ResponseMsg resp = JsonMapper.buildNormalMapper().fromJson(content, ResponseMsg.class);
				@SuppressWarnings("rawtypes")
				Map dd = (Map) resp.get("obj");
				String contentStr = (String) dd.get("contentStr");
				filesize = (Integer) dd.get("filesize");
				hf.close();
				
				byte[] bytes = ByteUtils.decode(contentStr);
				// 写入文件
				File rfile = new File("C:\\Users\\silent\\Desktop\\aaaa.docx");
				if(start == 0) {
					rfile.delete();
				}
				RandomAccessFile raf = new RandomAccessFile(rfile, "rw");
				raf.seek(start);
				raf.write(bytes, 0, bytes.length);
				raf.close();
				//改变开始位置 start+bytes.length
				start += bytes.length;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		// testJsonMapper();
		// testPageJson();
		// testIfexists();
		// testGetById();
//		 testPageSelect();
		// testSave();
		testBrokenSave();
//		testBrokenDownload();
		System.out.println("=====================华丽的分割线==========");
	}
}