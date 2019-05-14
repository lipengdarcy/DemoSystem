package cn.smarthse.business.model.system;

import java.io.Serializable;

import cn.smarthse.business.entity.system.SysParamType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public @Data class SysParamTypeModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     *
     */
    @ApiModelProperty(value="实体表")
    private SysParamType record;

}