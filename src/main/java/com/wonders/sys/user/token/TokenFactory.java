package com.wonders.sys.user.token;

import java.security.SecureRandom;
//import java.util.HashMap;
//import java.util.Map;

import com.wonders.sys.user.model.LoginUser;

public class TokenFactory {
	public static final String TOKENTYPE_TGC = "TGC";

	public static final String TOKENTYPE_PGT = "PGT";

	public static final String TOKENTYPE_UNK = "UNK";

	public static final SecureRandom sr = new SecureRandom();

//	private static int TRANSACTION_ID_LENGTH = 32;

	private static char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

	/**
	 * 生成token 方法与sso一致 并缓存token、用户
	 * 
	 * @param tokenType
	 * @return
	 */
	public static String newTicket(String tokenType, LoginUser user) {
		// TGC-1-50位随机字节串
		byte[] b = new byte[50];
		sr.nextBytes(b);
		String token = tokenType + "-NEW-" + generate(b);
		return token;
	}

	/**
	 * 缓存token、用户
	 * 
	 * @param token
	 * @param user
	 */
//	public static void cacheLoginUser(String token, LoginUser user) {
//		synchronized (tokenCache) {
//			tokenCache.put(token, user);
//		}
//	}

	/**
	 * 删除缓存token、用户
	 * 
	 * @param token
	 */
//	public static void deleteToken(String token) {
//		synchronized (tokenCache) {
//			tokenCache.remove(token);
//		}
//	}

	/**
	 * token获取登录用户
	 * 
	 * @return
	 */
//	public static LoginUser getLoginUserByTicket(String token, boolean flush) {
//		synchronized (tokenCache) {
//			LoginUser user = tokenCache.get(token);
//			return user;
//		}
//	}

	/**
	 * 获取token
	 * sso中的方法，暂时保留
	 * @return
	 */
//	public static String getTransactionId() {
//		byte[] b = new byte[TRANSACTION_ID_LENGTH];
//		SecureRandom sr = new SecureRandom();
//		sr.nextBytes(b);
//		return generate(b);
//	}

	/**
	 * token生成器
	 * 同sso中方法
	 * @param b
	 * @return
	 */
	public static synchronized String generate(byte[] b) {
		char[] out = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % alphabet.length;
			if (index < 0)
				index += alphabet.length;
			out[i] = alphabet[index];
		}
		return new String(out);
	}
}