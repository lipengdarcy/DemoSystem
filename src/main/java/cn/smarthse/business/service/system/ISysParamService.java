package cn.smarthse.business.service.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysParam;
import cn.smarthse.business.entity.system.SysParamType;
import cn.smarthse.framework.generic.GenericService;

public interface ISysParamService extends GenericService<SysParam> {
	/**
	 * 获取职业照射职业分类
	 */
	public List<SysParam> getOccupationClassList();

	/**
	 * 获取所有的参数类型
	 */
	public List<SysParamType> getParamTypeList(SysParamType param);

	/**
	 * 获取单个参数类型
	 */
	public SysParamType getParamType(Integer id);
	
	/**
	 * 根据系统参数，参数编码结尾，获取系统参数信息
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2019年4月8日-下午4:39:54
	 * @param typeCode 参数编码结尾部分，横杆之后。
	 * @return
	 */
	public SysParam getSystemParamEndwithTypeCode(String typeCode);

	/**
	 * 根据参数类型，获取参数列表
	 * 
	 * @param paramType
	 *            参数类型id
	 */
	public List<SysParam> getParamList(Integer paramType);

	/**
	 * 获取默认的业务管理部审核时间
	 */
	public SysParam getDefaultAuditTime();

	SysParam getOccupationClassListByName(String name);
	
	SysParam getOccupationClassListByCode(String code);
}
