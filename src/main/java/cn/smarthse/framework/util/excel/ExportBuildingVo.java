package cn.smarthse.framework.util.excel;

import java.util.Date;

import lombok.Data;
//建筑物导出
public @Data class ExportBuildingVo {
	
	private String name; //名称

    private String position; //位置

    private String type; //类别
    
    private String layer; //层数
    
    private Date updateTime; //更新时间
	
}