package cn.smarthse.framework.util.excel;

import java.util.List;

import lombok.Data;

/**
 * 《导出List通用VO》
 * 
 * 
 * @Project:  GIIANTECH CORE
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2016-3-24-下午4:09:07
 */
public @Data class ExportListVO<T> {
	//父属性
	private Object parent;
	//子列表
	private List<T> childs;
}
