package com.zj.boot_web.mapper;

import com.zj.boot_web.common.base.PageData;

public interface StatisticalMapper {
	
	/*
	 * 添加系统操作日志
	 */
	Integer insertLogRecord(PageData pd);
}
