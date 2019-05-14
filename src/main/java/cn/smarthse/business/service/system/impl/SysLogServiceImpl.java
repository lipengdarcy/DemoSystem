package cn.smarthse.business.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.smarthse.business.entity.system.SysLog;
import cn.smarthse.business.model.system.SysLogModel;
import cn.smarthse.business.model.system.SysLogSearchModel;
import cn.smarthse.business.service.system.ISysLogService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 系统日志Service实现
 *
 *
 * @Module ID: <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used: <JDK1.8>
 * @author <开发者>
 * @since 2019-02-20 08:45
 */
@Service
@Transactional(readOnly = true)
public class SysLogServiceImpl extends GenericServiceImpl<SysLog> implements ISysLogService {

	@Override
	public PageInfo<SysLogModel> getLogList(JqGridParam param, SysLogSearchModel model) {
		PageHelper.startPage((int) param.getPage(), (int) param.getRows());
		List<SysLogModel> mlist = new ArrayList<>();

		Example example = new Example(SysLog.class);
		Criteria c = example.createCriteria().andEqualTo("isValid", true);
		if (StringUtils.isNotEmpty(model.getDesc())) {
			c.andLike("description", "%" + model.getDesc() + "%");
		}
		if (model.getLogType() != null) {
			c.andCondition("SUBSTRING(log_type,1,1)=", model.getLogType());
		}
		if (model.getLogType() == null) {
			c.andCondition("log_type not in (0)");
		}
		if (StringUtils.isNotEmpty(model.getOptStartTime())) {
			c.andCondition("update_time >=", model.getOptStartTime());
		}
		if (StringUtils.isNotEmpty(model.getOptEndTime())) {
			c.andCondition("update_time <=", model.getOptEndTime());
		}
		example.setOrderByClause("update_time desc");
		List<SysLog> logList = this.getListByExample(example);
		PageInfo<SysLog> logPage = new PageInfo<>(logList);
		logList.forEach(log -> {
			mlist.add(new SysLogModel(log));
		});

		// 转换为PageInfo
		PageInfo<SysLogModel> page = new PageInfo<>(mlist);
		page.setPageNum(logPage.getPageNum());
		page.setPageSize(logPage.getPageSize());
		page.setTotal(logPage.getTotal());
		return page;
	}

}
