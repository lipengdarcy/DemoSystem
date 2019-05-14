/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.model.system;

import java.io.Serializable;

import cn.smarthse.business.entity.system.SysIndustry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *
 * 树形结构展开行业分类
 */
public @Data class SysIndustryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@ApiModelProperty(value = "实体表")
	private SysIndustry record;

	/**
	 * 是否叶节点
	 */
	private Boolean isLeaf;

	/**
	 * 是否展开
	 */
	private Boolean expanded;

}
