/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */


package cn.smarthse.business.model.system;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 *
 *
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8>
 * @author <开发者>
 * @since 2019-02-22 09:11
 */
public @Data class SysFileModel implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    @ApiModelProperty(value="")
	private Integer id;
	/**
	 * 文件名称
	 */
    @ApiModelProperty(value="文件名称")
	private String fileName;
	/**
	 * 文件大小
	 */
    @ApiModelProperty(value="文件大小")
	private String fileSize;
	/**
	 * 描述
	 */
    @ApiModelProperty(value="描述")
	private String description;
	/**
	 * 文件md5值
	 */
    @ApiModelProperty(value="文件md5值")
	private String md5;
	/**
	 * 创建时间
	 */
    @ApiModelProperty(value="创建时间")
	private java.util.Date createTime;
	/**
	 * 文件类型
	 */
    @ApiModelProperty(value="文件类型")
	private String mimeType;

    /**
	 * 文件全路径
	 */
    @ApiModelProperty(value="文件的完整路径（本地）")
	private String fullpath;

}
