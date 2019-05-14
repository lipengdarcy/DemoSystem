package cn.smarthse.framework.generic;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * 所有自定义Mapper的顶级接口
 * 
 */
public interface GenericDao<T>
		extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, IdsMapper<T>, MySqlMapper<T>, ConditionMapper<T> {

}
