package cn.smarthse.business.controller.system;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.collection.system.SystemUser;
import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.service.mongo.system.SystemUserService;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.util.StringUtils;
import io.swagger.annotations.Api;

/**
 * 用户管理
 */
@Api(value = "用户管理", tags = "用户管理")
@Controller
@RequestMapping("system/user")
public class UserController extends ControllerSupport {

	private Logger logger = LogManager.getLogger(this.getClass());

	private final String basePath = "system/user/";

	private final String prefix = "用户管理";

	@Autowired
	private SystemUserService SystemUserService;

	@Autowired
	private ISysRoleService roleService;

	// **************用户列表jqgrid模块*********************

	/**
	 * 系统管理：用户列表页面
	 */
	@RequestMapping()
	public String list(HttpSession session, ModelMap m) {
		List<SysRole> roleList = roleService.getRoleList(null);
		m.addAttribute("roleList", roleList);
		return basePath + "userList";
	}

	/**
	 * 系统管理：用户列表数据
	 * 
	 * @param name
	 *            用户名或者姓名
	 */
	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SystemUser> listData(HttpSession session, JqGridParam param, SystemUser dataParam) {
		JqGridData<SystemUser> data = SystemUserService.getGridData(param, dataParam);
		return data;
	}

	@GetMapping(value = "/viewUser")
	public String viewUser(@RequestParam(value = "id", required = false) String id, ModelMap m) {
		SystemUser user = SystemUserService.getById(id);
		m.put("viewUser", user);
		return basePath + "viewUser";
	}

	@GetMapping(value = "/editUser")
	public String editPage(@RequestParam(value = "id", required = false) String id, ModelMap m) {
		logger.debug("{} - 用户编辑页,id={}", prefix, id);
		SystemUser user = SystemUserService.getById(id);
		List<SysRole> roleList = roleService.getRoleList(null);
		m.put("roleList", roleList);
		m.put("editUser", user);
		return basePath + "editUser";
	}

	@GetMapping(value = "/addUser")
	public String addPage(ModelMap m) {
		List<SysRole> roleList = roleService.getRoleList(null);
		m.put("roleList", roleList);
		return basePath + "addUser";
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "用户新增", type = LogConstans.type_opt_add)
	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseData<String> add(SystemUser user) {
		ResponseData<String> data = new ResponseData<String>();
		if (StringUtils.isNotEmpty(user.getIdCard()) && SystemUserService.getUser(user.getIdCard()) != null) {
			data.setCode(-1);
			data.setMessage("该身份证号已被使用!");
			return data;
		}
		if (StringUtils.isNotEmpty(user.getTel()) && SystemUserService.getUser(user.getTel()) != null) {
			data.setCode(-1);
			data.setMessage("该手机号已被使用!");
			return data;
		}
		SystemUser addUser = SystemUserService.addUser(user);
		data.setData(addUser.getId());
		data.setMessage("新增成功!");
		return data;
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "用户编辑", type = LogConstans.type_opt_edit)
	@PostMapping(value = "/edit")
	@ResponseBody
	public ResponseData<String> edit(SystemUser user) {
		ResponseData<String> data = new ResponseData<String>();
		if (StringUtils.isNotEmpty(user.getIdCard()) && SystemUserService.getUser(user.getIdCard()) != null) {
			data.setCode(-1);
			data.setMessage("该身份证号已被使用!");
			return data;
		}
		if (StringUtils.isNotEmpty(user.getTel()) && SystemUserService.getUser(user.getTel()) != null) {
			data.setCode(-1);
			data.setMessage("该手机号已被使用!");
			return data;
		}
		SystemUserService.editUser(user);
		data.setMessage("编辑成功!");
		return data;
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "密码重置", type = LogConstans.type_opt_reset)
	@ResponseBody
	@PostMapping(value = "/pwdReset")
	public ResponseData<String> pwdReset(@RequestParam(value = "id", required = false) String id) {
		ResponseData<String> data = new ResponseData<String>();
		SystemUserService.resetPassword(id);
		return data;
	}

	/**
	 * 系统管理：个人中心
	 */
	@RequestMapping("profile")
	public String profile(HttpSession session, ModelMap m) {
		String uid = ShiroUtil.getLoginUserId();
		SystemUser user = SystemUserService.getById(uid);
		// m.put("user", model);
		m.put("the_user", user); // 用user作key，页面一直取不到user.roleNames这个值，不知原因
		return basePath + "profile";
	}

	/**
	 * 密码修改
	 */
	@ResponseBody
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseData<String> changePassword(String oldPassword, SystemUser user, HttpSession session) {
		ResponseData<String> data = new ResponseData<String>();
		String uid = ShiroUtil.getLoginUserId();
		user.setId(uid);
		int result = SystemUserService.changePassword(user, oldPassword);
		if (result == -1) {
			data.setCode(result);
			data.setMessage("账号不存在，请确认输入正确的手机号码");
			return data;
		} else if (result == -2) {
			data.setCode(result);
			data.setMessage("原密码输入错误!");
			return data;
		} else {
			data.setMessage("用户修改密码成功!");
			return data;
		}

	}

}
