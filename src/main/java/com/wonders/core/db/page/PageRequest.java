package com.wonders.core.db.page;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 分页请求参数
 * @author silent
 */
public class PageRequest implements Serializable {
	private static final long serialVersionUID = -571953884967203085L;

	/**
	 * 分页查询条件
	 */
	@JsonProperty
	private Map<String, Object> filters;

	/**
	 * 页码
	 */
	@JsonProperty
	private int pageNum;

	/**
	 * 页长
	 */
	@JsonProperty
	private int pageSize;

	/**
	 * 排序条件
	 */
	@JsonProperty
	private String sortColumns;

	public PageRequest() {
		this(0, 0);
	}

	public PageRequest(Map<String, Object> filters) {
		this(0, 0, filters, null);
	}

	public PageRequest(int pageNum, int pageSize) {
		this(pageNum, pageSize, null);
	}

	public PageRequest(int pageNum, int pageSize, String sortColumns) {
		this(pageNum, pageSize, null, sortColumns);
	}

	public PageRequest(int pageNum, int pageSize,
			Map<String, Object> filters, String sortColumns) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		setFilters(filters);
		setSortColumns(sortColumns);
	}

	public Map<String, Object> getFilters() {
		return this.filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumns() {
		return this.sortColumns;
	}

	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
}
