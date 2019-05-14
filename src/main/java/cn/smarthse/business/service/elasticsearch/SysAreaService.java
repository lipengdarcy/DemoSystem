package cn.smarthse.business.service.elasticsearch;

import org.springframework.data.domain.Page;

import cn.smarthse.business.model.elasticsearch.SysArea;

public interface SysAreaService {

	/**
	 * 初始化数据，从mysql导入elastic
	 */
	void initData();

	Iterable<SysArea> getAll();

	Page<SysArea> pageQuery(Integer pageNo, Integer pageSize, SysArea kw);

}
