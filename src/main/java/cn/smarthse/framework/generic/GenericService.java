package cn.smarthse.framework.generic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 */
public interface GenericService<T> {

	/**
	 * 插入对象
	 *
	 * @param model
	 *            对象
	 */
	T insert(T model);

	/**
	 * 通过主键, 删除对象
	 *
	 * @param id
	 *            主键
	 */
	int delete(T record);

	/**
	 * 更新对象
	 *
	 * @param model
	 *            对象
	 */
	int update(T model);

	/**
	 * 通过主键, 查询对象
	 *
	 * @param id
	 *            主键
	 * @return model 对象
	 */
	T getById(Integer id);

	/**
	 * 通过主键, 查询对象
	 *
	 * @param id
	 *            主键
	 * @return model 对象
	 */
	T getById(Long id);

	/**
	 * 通过主键, 查询对象
	 *
	 * @param id
	 *            主键
	 * @return model 对象
	 */
	T getById(String id);

	/**
	 * 查询多个对象
	 *
	 * @return 对象集合
	 */
	List<T> selectList();

	int deleteByExample(Object example);

	List<T> selectByExample(Object example);

	/**
	 * 批量更新
	 *
	 * @return 更新条目
	 */
	int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

	/**
	 * 批量更新
	 *
	 * @return 更新条目
	 */
	int updateByExample(@Param("record") T record, @Param("example") Object example);

}
