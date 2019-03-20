package com.wonders.core.db.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.core.db.model.BaseEntity;

/**
 * 分页返回结果
 * @author silent
 */
public class PageResult<T extends BaseEntity> implements Serializable {
	private static final long serialVersionUID = -2039811366522784587L;

	/**
	 * 分页结果
	 */
	@JsonProperty
	private List<T> result;

	/**
	 * 页长
	 */
	@JsonProperty
	private int pageSize;

	/**
	 * 页码
	 */
	@JsonProperty
	private int pageNum;

	/**
	 * 总数量
	 */
	@JsonProperty
	private long totalCount = 0;

	public PageResult(PageRequest p, long totalCount) {
		this(p.getPageNum(), p.getPageSize(), totalCount);
	}

	public PageResult(int pageNum, int pageSize, long totalCount) {
		this(pageNum, pageSize, totalCount, new ArrayList<T>(0));
	}

	public PageResult(int pageNum, int pageSize, long totalCount, List<T> result) {
		if (pageSize <= 0)
			throw new IllegalArgumentException(
					"[pageSize] must great than zero");
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.totalCount = totalCount;
		setResult(result);
	}

	public void setResult(List<T> elements) {
		if (elements == null)
			throw new IllegalArgumentException("'result' must be not null");
		this.result = elements;
	}

	public List<T> getResult() {
		return this.result;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	@JsonIgnore
	private int getLastPageNum() {
		int result = (int) (this.totalCount % this.pageSize == 0 ? this.totalCount
				/ this.pageSize : this.totalCount / this.pageSize + 1);
		if (result <= 1)
			result = 1;
		return result;
	}

	@JsonIgnore
	private int computePageNum(int pageNumber, int pageSize, int totalElements) {
		if (pageNumber <= 1) {
			return 1;
		}
		int lastPageNum = getLastPageNum();
		if ((Integer.MAX_VALUE == pageNumber) || (pageNumber > lastPageNum)) {
			return lastPageNum;
		}
		return pageNumber;
	}
}
