package cn.smarthse.framework.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.smarthse.config.security.web.ShiroPrincipal;
import tk.mybatis.mapper.entity.Example;

/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作, 未实现的方法有 子类各自实现
 */
public abstract class GenericServiceImpl<T> implements GenericService<T> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * 持久层对象
	 */
	@Autowired(required = false)
	protected GenericDao<T> dao;

	/**
	 * 持久层对象
	 */
	@Autowired(required = false)
	protected MongoRepository<T, String> MongoRepository;

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 */
	public Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 根据Example条件查询列表数据
	 * 
	 * @param example
	 * @return
	 */
	public List<T> getListByExample(Example example) {
		return dao.selectByExample(example);
	}

	/**
	 * 根据Example条件查询数量
	 * 
	 * @param example
	 * @return
	 */
	public int getCountByExample(Example example) {
		return dao.selectCountByExample(example);
	}

	/**
	 * 设置更新相关的信息
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author Horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2017年6月13日-下午2:33:44
	 * @param entity
	 *            需要更新的实体类
	 * @param loginStaffId
	 *            当前登录员工ID
	 */
	private void setUpdateInfo(T entity, String updateBy) {
		Class<? extends Object> cls = entity.getClass();
		Method setUpdateDate;
		Method setUpdateBy;
		try {
			setUpdateDate = cls.getDeclaredMethod("setUpdateTime", Date.class);
			if (setUpdateDate != null) {
				setUpdateDate.invoke(entity, new Date());
			}

			if (updateBy != null) {
				setUpdateBy = cls.getDeclaredMethod("setUpdateBy", String.class);
				if (setUpdateBy != null) {
					setUpdateBy.invoke(entity, updateBy);
				}
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置isvalid
	 * 
	 */
	private void setIsValid(T entity, boolean isvalid) {
		Class<? extends Object> cls = entity.getClass();
		Method setIsValid;

		try {
			setIsValid = cls.getDeclaredMethod("setIsValid", Boolean.class);
			if (setIsValid != null) {
				setIsValid.invoke(entity, isvalid);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 設置創建記錄相关的信息
	 */
	private void setCreateInfo(T entity, String createBy) {
		Class<? extends Object> cls = entity.getClass();
		Method setCreateDate;
		Method setCreateBy;
		try {
			// cn.smarthse.business.entity.system.SysLog.setCreateTime(Date)
			setCreateDate = cls.getDeclaredMethod("setCreateTime", Date.class);
			if (setCreateDate != null) {
				setCreateDate.invoke(entity, new Date());
			}

			// 设置有效状态
			setIsValid(entity, true);

			if (createBy != null) {
				setCreateBy = cls.getDeclaredMethod("setCreateBy", String.class);
				if (setCreateBy != null) {
					setCreateBy.invoke(entity, createBy);
				}
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入对象
	 *
	 * @param T
	 *            对象
	 */
	public T insert(T record) {
		ShiroPrincipal p = null;
		Object obj = SecurityUtils.getSubject().getPrincipal();
		p = (ShiroPrincipal) obj;
		String uid = p.getUser().getId();
		this.setUpdateInfo(record, uid);
		this.setCreateInfo(record, uid);
		this.setIsValid(record, true);
		if (dao != null) {
			dao.insertSelective(record);
		}
		if (MongoRepository != null)
			MongoRepository.save(record);
		return record;
	}

	/**
	 * 更新对象
	 *
	 * @param record
	 *            对象
	 */
	public int update(T record) {
		ShiroPrincipal p = null;
		Object obj = SecurityUtils.getSubject().getPrincipal();
		p = (ShiroPrincipal) obj;
		String uid = p.getUser().getId();
		this.setUpdateInfo(record, uid);
		this.setIsValid(record, true);
		if (dao != null) {
			dao.updateByPrimaryKeySelective(record);
		}
		if (MongoRepository != null) {
			MongoRepository.save(record);
		}
		return 1;
	}

	/**
	 * 通过主键, 删除对象
	 *
	 * @param id
	 *            主键
	 */
	public int delete(T entity) {
		ShiroPrincipal p = null;
		Object obj = SecurityUtils.getSubject().getPrincipal();
		p = (ShiroPrincipal) obj;
		String uid = p.getUser().getId();
		this.setUpdateInfo(entity, uid);
		this.setIsValid(entity, false);
		// 提交更新
		if (dao != null)
			dao.updateByPrimaryKeySelective(entity);
		if (MongoRepository != null)
			MongoRepository.save(entity);
		return 1;
	}

	/**
	 * 通过主键, 查询对象
	 *
	 * @param id
	 *            主键
	 * @return
	 */
	public T getById(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	public T getById(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	public T getById(String id) {
		if (dao != null)
			return dao.selectByPrimaryKey(id);
		return MongoRepository.findById(id).get();
	}

	public List<T> selectList() {
		return dao.selectByExample(null);
	}

	public int deleteByExample(Object example) {
		return dao.deleteByExample(example);
	}

	public List<T> selectByExample(Object example) {
		return dao.selectByExample(example);
	}

	/**
	 * 批量更新
	 *
	 * @return 更新条目
	 */
	public int updateByExampleSelective(@Param("record") T record, @Param("example") Object example) {
		return dao.updateByExampleSelective(record, example);
	}

	/**
	 * 批量更新
	 *
	 * @return 更新条目
	 */
	public int updateByExample(@Param("record") T record, @Param("example") Object example) {
		return dao.updateByExample(record, example);
	}

	/**
	 * * 创建分页请求.
	 */
	public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = Sort.by(Direction.DESC, "id");
		} else if ("birthday".equals(sortType)) {
			sort = Sort.by(Direction.ASC, "birthday");
		}
		// 参数1表示当前第几页,参数2表示每页的大小,参数3表示排序
		return PageRequest.of(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 根据Example读取1条记录
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-27-上午9:18:28
	 * @param example
	 * @return
	 */
	protected T getOneByExample(Example example) {
		List<T> list = dao.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

}
