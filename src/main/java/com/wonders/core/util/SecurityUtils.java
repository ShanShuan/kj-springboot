package com.wonders.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密处理工具
 * @author silent
 */
public class SecurityUtils {
	private static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	/**
	 * sha1加密
	 * @param input
	 * @return
	 */
	public static String encodeSHA1(String input) {
		String result = DigestUtils.sha1Hex(input);
		logger.debug("encodeSHA1[" + input + "] to [" + result + "]");
		return result;
	}
	public static void main(String[] args) {
		System.out.println(encodeSHA1("111111"));
	}
}
