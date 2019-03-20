package com.wonders.recorder.video.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.recorder.video.model.FileDemo;

@Mapper
@Component
public interface FileDemoDao extends BaseDao<FileDemo, String> {
}