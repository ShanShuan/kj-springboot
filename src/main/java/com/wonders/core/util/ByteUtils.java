package com.wonders.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字节码处理工具
 * 
 * @author silent
 */
public class ByteUtils {
	private static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

	/**
	 * 编码
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes) {
		if(bytes == null || bytes.length <= 0) {
			return "";
		}
		try {
			logger.debug("source byte length=" + bytes.length);
			byte[] resultbyte = Base64.encodeBase64(bytes);
			return new String(resultbyte, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 解码
	 * 
	 * @param string
	 * @return
	 */
	public static byte[] decode(String string) {
		if(StringUtils.isEmpty(string)) {
			return null;
		}
		try {
			byte[] bytes = string.getBytes("utf-8");
			byte[] resultbyte = Base64.decodeBase64(bytes);
			return resultbyte;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\silent\\Desktop\\百度RASP.docx");
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[is.available()];
		is.read(bytes);

		String encode = encode(bytes);
		is.close();
		System.out.println("encode" + encode.length());
		
		byte[] filecontent = decode(encode);
		File fileo = new File("C:\\Users\\silent\\Desktop\\百度RASP2.docx");
		OutputStream os = new FileOutputStream(fileo);
		os.write(filecontent);
		os.flush();
		os.close();
	}
}
