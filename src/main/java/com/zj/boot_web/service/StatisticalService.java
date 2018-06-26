package com.zj.boot_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zj.boot_web.common.base.PageData;
import com.zj.boot_web.mapper.StatisticalMapper;

@Service
public class StatisticalService {

	@Autowired
    private StatisticalMapper mapper;
	
	
	/*
	 * 添加系统操作日志
	 */
	public Integer insertLogRecord (PageData pd) {
		
		return mapper.insertLogRecord(pd);
	}
}
