package cn.smarthse.business.dao;

import java.util.List;

import cn.smarthse.business.entity.system.SysAreaStandard;

//批量插入数据mapper
public interface ATempMapper{
	
	
	 int singleInsert(SysAreaStandard record);
	 
	 int batchInsert(List<SysAreaStandard> list);
		 
}