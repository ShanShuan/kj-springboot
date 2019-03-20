package com.wonders.core.rest.rsp;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据对象
 * @author silent
 */
public class ResponseMsg extends HashMap<String, Object> {
	private static final long serialVersionUID = 625569765058564144L;
	/**
	 * key for success or failed status
	 */
	private static final String KEY_SUCCESS = "success";
	/**
	 * key for return object
	 */
	private static final String KEY_OBJECT = "obj";
	/**
	 * success status
	 */
	public static final int SUCCESS = 0;
	/**
	 * error status when authentication valid failed
	 */
	public static final int FAIL_AUTH_VALID = 1;
	/**
	 * error status when token valid failed
	 */
	public static final int FAIL_TOKEN_VALID = 2;
	/**
	 * error status when request failed
	 */
	public static final int FAIL_REQ_ERROR = 3;
	/**
	 * error status when execute failed
	 */
	public static final int FAIL_DATA_VALID = 4;
	/**
	 * error status when execute failed
	 */
	public static final int FAIL_EXE_ERROR = 5;
	/**
	 * error status when return failed
	 */
	public static final int FAIL_RTN_ERROR = 6;
	/**
	 * error status with uncatch exception
	 */
	public static final int FAIL_UNC_ERROR = 9;

	private ResponseMsg() {
		put(KEY_SUCCESS, SUCCESS);
	}

	public static ResponseMsg error(int success, Object rtnObj) {
		ResponseMsg r = new ResponseMsg();
		r.put(KEY_SUCCESS, success);
		r.put(KEY_OBJECT, rtnObj);
		return r;
	}

	public static ResponseMsg tokenError(Object rtnObj) {
		ResponseMsg r = error(FAIL_TOKEN_VALID, rtnObj);
		return r;
	}

	public static ResponseMsg success() {
		return new ResponseMsg();
	}

	public static ResponseMsg success(Object rtnObj) {
		ResponseMsg r = new ResponseMsg();
		r.put(KEY_OBJECT, rtnObj);
		return r;
	}

	public static ResponseMsg success(Map<String, Object> map) {
		ResponseMsg r = new ResponseMsg();
		map.remove(KEY_SUCCESS);
		map.remove(KEY_OBJECT);
		r.putAll(map);
		return r;
	}

	public ResponseMsg put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
