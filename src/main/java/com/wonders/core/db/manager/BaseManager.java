package com.wonders.core.db.manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.page.PageRequest;
import com.wonders.core.db.page.PageResult;

/**
 * BaseManager all business logic here
 * 
 * @author silent
 */
public abstract class BaseManager<T extends BaseEntity, PK extends Serializable> {
	protected static Logger logger = LoggerFactory.getLogger(BaseManager.class);

	public abstract BaseDao<T, PK> getBaseDao();

	/**
	 * save 插入数据对象
     * 这里使用泛型T，可通过继承完成不同表的插入操作
	 * @param entity数据对象参数
	 */
	public void save(T entity) {
		Assert.notNull(entity, "entity must not be null");
		entity.setCreateddate(new Date());
		getBaseDao().save(entity);
	}

	/**
	 * update 更新数据对象
     * 这里使用泛型T，可通过继承完成不同表的更新操作
	 * @param entity数据对象参数
	 * @return int 影响的数据个数
	 */
	public int update(T entity) {
		Assert.notNull(entity, "entity must not be null");
		entity.setUpdateddate(new Date());
		return getBaseDao().update(entity);
	}

	/**
	 * logicRemove 逻辑删除数据对象
     * 这里使用泛型T，可通过继承完成不同表的逻辑删除操作
	 * @param entity 表数据对象参数
	 * @return int 影响的数据个数
	 */
	public int logicRemove(T entity) {
		Assert.notNull(entity, "entity must not be null");
		entity.setRemovedate(new Date());
		entity.setRemoved("1");
		return getBaseDao().update(entity);
	}

	/**
	 * saveOrUpdate 插入或更新数据对象
     * 这里使用泛型T，可通过继承完成不同表的插入或更新操作
	 * @param entity数据对象参数
	 */
	public void saveOrUpdate(T entity) {
		if (this.ifexists(entity)) {
			this.update(entity);
		} else {
			this.save(entity);
		}
	}

	/**
	 * delete 删除数据对象
     * 这里使用泛型T，可通过继承完成不同表的删除操作
	 * @param pk表主键参数
	 * @return int 影响的数据个数
	 */
	public int delete(PK pk) {
		Assert.notNull(pk, "pk must not be null");
		if(pk instanceof String) {
			String text = (String) pk;
			Assert.hasLength(text, "pk must not be empty");
		}
		return getBaseDao().delete(pk);
	}

	/**
	 * ifexists 判断查询数据存在条数
     * 这里使用泛型T，可通过继承完成不同表的判断查询数据存在条数操作
	 * @param entity查询数据对象参数
	 * @return long 返回的判断查询数据存在条数
	 */
	public boolean ifexists(T entity) {
		Assert.notNull(entity, "entity must not be null");
		long ifexists = getBaseDao().ifexists(entity);
		return ifexists > 0;
	}

	/**
	 * getById 获取单个数据对象，不包含blob字段
     * 这里使用泛型T，可通过继承完成不同表的获取单个数据对象操作
	 * @param pk表主键参数
	 * @return T 返回的数据对象
	 */
	public T getById(PK pk) {
		Assert.notNull(pk, "pk must not be null");
		if(pk instanceof String) {
			String text = (String) pk;
			Assert.hasLength(text, "pk must not be empty");
		}
		return getBaseDao().getById(pk);
	}

	/**
	 * getByIdWithBlob 获取单个数据对象，包含blob字段
     * 这里使用泛型T，可通过继承完成不同表的获取单个数据对象操作
	 * @param pk表主键参数
	 * @return T 返回的数据对象
	 */
	public T getByIdWithBlob(PK pk) {
		Assert.notNull(pk, "pk must not be null");
		if(pk instanceof String) {
			String text = (String) pk;
			Assert.hasLength(text, "pk must not be empty");
		}
		return getBaseDao().getByIdWithBlob(pk);
	}

	/**
	 * select 条件查询数据对象
     * 这里使用泛型T，可通过继承完成不同表的条件查询数据对象操作
	 * @param args查询参数
	 * @return List<T> 返回的数据对象集合
	 */
	public List<T> select(Map<String, Object> args) {
		Assert.notNull(args, "args must not be null");
		return getBaseDao().select(args);
	}

	/**
	 * select 条件查询数据对象
     * 这里使用泛型T，可通过继承完成不同表的条件查询数据对象操作
	 * @param args查询参数
	 * @return List<T> 返回的数据对象集合
	 */
	public int count(Map<String, Object> args) {
		Assert.notNull(args, "args must not be null");
		return getBaseDao().count(args);
	}

	/**
	 * pageSelect 分页条件查询数据对象
     * 这里使用泛型T，可通过继承完成不同表的分页条件查询数据对象操作
	 * @param args分页查询条件参数
	 * @return Page<T> 返回的分页数据对象集合
	 */
	public PageResult<T> pageSelect(PageRequest pageRequest) {
		Assert.notNull(pageRequest, "pageRequest must not be null");
		PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
		Page<T> page = getBaseDao().pageSelect(pageRequest.getFilters());
		int pageNum = page.getPageNum();
		int pageSize = page.getPageSize();
		long total = page.getTotal();
		PageResult<T> pageResult = new PageResult<T>(pageNum, pageSize, total);
		List<T> result = page.getResult();
		pageResult.setResult(result);
		logger.debug("total>>" + total + ">resultsize>>" + result.size() + ">pageNum>" + pageNum + ">pageSize>"
				+ pageSize + ">result>" + result);
		return pageResult;
	}
}
