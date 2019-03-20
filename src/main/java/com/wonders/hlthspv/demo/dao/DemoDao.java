package com.wonders.hlthspv.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.hlthspv.demo.model.Demo;

@Mapper
@Component
public interface DemoDao extends BaseDao<Demo, String> {
}