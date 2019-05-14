package cn.smarthse.business.controller.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.model.system.SysUserChooseModel;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.model.system.SysUserSeachModel;
import cn.smarthse.business.service.system.ISysUserService;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户选择组件
 */
@Api(value = "组件：接口",tags="组件：接口")
@Controller
@RequestMapping("tool/user")
public class UserCommonController extends ControllerSupport {
	private final String info_prefix = "【用户选择组件】";

	@Autowired
	private ISysUserService userService;

	
	/**
	 * 人员多选页面
	 */
	@RequestMapping("selectUser")
	public String selectUser(HttpSession session, String name, ModelMap m,
			@RequestParam(value = "type", defaultValue = "-1", required = false) Integer type) {
		List<SysUser> list = userService.getList();
		List<SysUserModel> mlist = new ArrayList<>();
		for (SysUser user : list) {
			SysUserModel umodel = new SysUserModel(user);
			
			umodel.setRealName(user.getRealName());
			umodel.setStaffNo(user.getStaffNo());
			
			mlist.add(umodel);
		}
		m.put("list", mlist);
		return "tool/selectUser";
	}
	
	@ApiOperation(value="用户接口: 读取当前企业的所有用户列表,并按部门分组")
	@RequestMapping(value = "/getUsers", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseData<Collection<SysUserChooseModel>> getUsers(String roleIds) {
		logger.info("{} - 获取用户列表, roleIds={}",info_prefix, roleIds);
		ResponseData<Collection<SysUserChooseModel>> data = new ResponseData<>();
		//当前用户所在企业, 默认暂时为1
		Integer cid = ShiroUtil.getLoginCid()!=null ? ShiroUtil.getLoginCid() : 1;
		//搜索企业用户条件
		SysUserSeachModel searchModel = new SysUserSeachModel();
    	searchModel.setCid(cid);
    	//用户角色信息
		if(StringUtils.isNotEmpty(roleIds)) {
			//验证用户角色
			searchModel.setRoleIds(roleIds);
		}
		
		//用户清单	
    	List<SysUser> list = userService.getUserListBySearchModel(searchModel);
		
		//重复的UserMap Integer 用户所在的deptId
		Map<Integer, SysUserChooseModel> duplicationMap = new HashMap<>();
		//将list转换为cityModel
		list.forEach(item -> {
			//部门编号
			Integer duplicationKey = item.getDeptId() !=null && item.getDeptId()>0 ? item.getDeptId() : 0;
			//用户
			SysUserModel user = new SysUserModel();
			user.setId(item.getId());
			user.setRealName(item.getRealName());
			user.setStaffNo(item.getStaffNo());
			//TODO 其他用户信息
			
			SysUserChooseModel model = duplicationMap.get(duplicationKey);
			if(model==null) {
				model = new SysUserChooseModel();
				model.setDeptId(duplicationKey);

				
				List<SysUserModel> userList = new  ArrayList<>();
				userList.add(user);
				
				model.setUserList(userList);
			}else {
				model.getUserList().add(user);
			}
			
			duplicationMap.put(duplicationKey, model);
			
		});
		
		//将Map值转换为List
		Collection<SysUserChooseModel> mapList = duplicationMap.values();
		//
		List<SysUserChooseModel> modelList = new ArrayList<>();
		modelList.addAll(mapList);
		//排序
		Collections.sort(modelList); // 按部门排序
		//
		data.setData(modelList);
		return data;
	}

	
	
	
}
