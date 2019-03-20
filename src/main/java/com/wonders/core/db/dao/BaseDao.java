package com.wonders.core.db.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.wonders.core.db.model.BaseEntity;

/**
 * BaseDao
 * 
 * @author silent
 * @param <T>
 * 2018年5月14日 15:42:16
 */
public interface BaseDao<T extends BaseEntity, PK extends Serializable> {
	/**
	 * save 插入数据对象
     * 这里使用泛型T，可通过继承完成不同表的插入操作
	 * @param entity数据对象参数
	 */
	void save(T entity);

	/**
	 * update 更新数据对象
     * 这里使用泛型T，可通过继承完成不同表的更新操作
	 * @param entity数据对象参数
	 * @return int 影响的数据个数
	 */
	int update(T entity);

	/**
	 * delete 删除数据对象
     * 这里使用泛型T，可通过继承完成不同表的删除操作
	 * @param pk表主键参数
	 * @return int 影响的数据个数
	 */
	int delete(PK pk);

	/**
	 * getByIdWithBlob 获取单个数据对象，包含blob字段
     * 这里使用泛型T，可通过继承完成不同表的获取单个数据对象操作
	 * @param pk表主键参数
	 * @return T 返回的数据对象
	 */
	T getByIdWithBlob(PK pk);

	/**
	 * getById 获取单个数据对象，不包含blob字段
     * 这里使用泛型T，可通过继承完成不同表的获取单个数据对象操作
	 * @param pk表主键参数
	 * @return T 返回的数据对象
	 */
	T getById(PK pk);

	/**
	 * select 条件查询数据对象
     * 这里使用泛型T，可通过继承完成不同表的条件查询数据对象操作
	 * @param args查询参数
	 * @return List<T> 返回的数据对象集合
	 */
	List<T> select(Map<String, Object> args);

	/**
	 * count 条件查询数据数量
     * 这里使用泛型T，可通过继承完成不同表的条件查询数据数量操作
	 * @param args查询参数
	 * @return int 返回的查询数量
	 */
	int count(Map<String, Object> args);

	/**
	 * pageSelect 分页条件查询数据对象
     * 这里使用泛型T，可通过继承完成不同表的分页条件查询数据对象操作
	 * @param args分页查询条件参数
	 * @return Page<T> 返回的分页数据对象集合
	 */
	Page<T> pageSelect(Map<String, Object> args);

	/**
	 * ifexists 判断查询数据存在条数
     * 这里使用泛型T，可通过继承完成不同表的判断查询数据存在条数操作
	 * @param entity查询数据对象参数
	 * @return long 返回的判断查询数据存在条数
	 */
	long ifexists(T entity);
}
