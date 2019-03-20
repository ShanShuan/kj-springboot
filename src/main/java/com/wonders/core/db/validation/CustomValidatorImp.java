package com.wonders.core.db.validation;

public interface CustomValidatorImp<T> {
	public final static String SUCCESS = "SUCCESS";
	public final static String FAILED = "FAILED";
	public final static String ERROR = "ERROR";

	String validate(T obj);
}
