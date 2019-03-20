package com.wonders.hlthspv.demo.model.validator;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.wonders.core.db.validation.CustomValidatorImp;
import com.wonders.hlthspv.demo.model.Demo;

public class DemoCustomValidaor implements CustomValidatorImp<Demo> {

	@Override
	public String validate(Demo obj) {
		StringBuffer sb = new StringBuffer();
		if(obj == null) {
			sb.append("空");
		}
		if(obj.getCreateddate() != null && obj.getCreateddate().before(new Date())) {
			sb.append("创建时间不对");
		}
		if(StringUtils.isEmpty(obj.getDemoname())) {
			sb.append("名称不能为空");
		}
		if(sb.length() <= 0) {
			return CustomValidatorImp.SUCCESS;
		} else {
			return sb.toString();
		}
	}

}
