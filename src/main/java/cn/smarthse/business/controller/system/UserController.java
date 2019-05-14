package cn.smarthse.business.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cn.smarthse.business.controller.ControllerSupport;
import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.entity.system.SysUserRole;
import cn.smarthse.business.model.system.SysUserCoverFormModel;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.model.system.SysUserSeachModel;
import cn.smarthse.business.model.system.SysUserSuccessModel;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.business.service.system.ISysUserRoleService;
import cn.smarthse.business.service.system.ISysUserService;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.util.CommonUtil;
import cn.smarthse.framework.util.DateUtils;
import cn.smarthse.framework.util.StringMixedUtil;
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
	private ISysUserService userService;

	@Autowired
	private ISysUserRoleService userRoleService;

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

	@RequestMapping("importCover")
	public String common() {
		return "user/importCover";
	}

	/**
	 * 系统管理：用户列表数据
	 * 
	 * @param name
	 *            用户名或者姓名
	 */
	@ResponseBody
	@GetMapping(value = "/listData")
	public JqGridData<SysUserModel> listData(HttpSession session, JqGridParam param, SysUserSeachModel model) {
		JqGridData<SysUserModel> data = null;
		model.setCid(ShiroUtil.getLoginCid());
		PageInfo<SysUserModel> userList = userService.getUserList(param, model);
		data = new JqGridData<>(userList, param);
		return data;
	}

	@GetMapping(value = "/viewUser")
	public String viewUser(@RequestParam(value = "id", required = false) Integer id, ModelMap m) {
		SysUser user = userService.getById(id);
		SysUserModel userModel = userService.toUserModel(user);
		if (userModel != null) {
			if (userModel.getTel() != null) {
				userModel.setTel(StringMixedUtil.mixedMobile(userModel.getTel()));
			}
		}
		m.addAttribute("viewUser", userModel);
		return basePath + "viewUser";
	}

	@GetMapping(value = "/editUser")
	public String editPage(@RequestParam(value = "id", required = false) Integer id, ModelMap m) {
		// 测试修改
		logger.info("{} - 用户编辑页,id={}", prefix, id);

		// 编辑页
		SysUser user = userService.getById(id);

		SysUserModel model = new SysUserModel(user);

		logger.info(model);

		// 角色
		List<SysUserRole> rolelist = userRoleService.getListByUserId(user.getId());
		model.setUserRoleList(rolelist);
		List<SysRole> roleList = roleService.getRoleList(null);

		m.addAttribute("roleList", roleList);
		m.addAttribute("dutyState", user.getDutyState());
		m.addAttribute("editUser", model);

		return basePath + "editUser";
	}

	@GetMapping(value = "/addUser")
	public String addPage(ModelMap m) {
		List<SysRole> roleList = roleService.getRoleList(null);
		m.addAttribute("roleList", roleList);
		return basePath + "addUser";
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "用户新增", type = LogConstans.type_opt_add)
	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseData<Integer> add_ajax(SysUser user,
			@RequestParam(value = "deptJoinDateString", required = false) String deptJoinDateString, ModelMap m) {
		logger.info("{} - 用户新增提交,user={},deptJoinDateString={}", prefix, user, deptJoinDateString);
		ResponseData<Integer> data = checkUserForm(user, user.getRoleIds());

		if (data.getCode() == -1) {
			return data;
		}
		user.setDeptJoinDate(DateUtils.StringToDate(deptJoinDateString));

		if (!StringUtils.isNotEmpty(user.getUserName())) {
			logger.info("用户名不能为空!");
			data.setCode(-1);
			data.setMessage("用户名不能为空!");
			return data;
		}

		if (StringUtils.isNotEmpty(user.getIdCard()) && userService.isIdCardExsists(user.getIdCard())) {
			logger.info("该身份证号已被使用!");
			data.setCode(-1);
			data.setMessage("该身份证号已被使用!");
			return data;
		}

		if (userService.isStaffNoExsists(user.getStaffNo())) {
			logger.info("该工号已被使用!");
			data.setCode(-1);
			data.setMessage("该工号已被使用!");
			return data;
		}

		if (userService.isTelExsists(user.getTel())) {
			logger.info("该手机号已被使用!");
			data.setCode(-1);
			data.setMessage("该手机号已被使用!");
			return data;
		}

		SysUser addUser = userService.addUser(user, ShiroUtil.getLoginUserId());

		data.setData(addUser.getId());
		data.setMessage("新增成功!");
		return data;
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "用户编辑", type = LogConstans.type_opt_edit)
	@PostMapping(value = "/edit")
	@ResponseBody
	public ResponseData<Integer> edit_ajax(SysUser user,
			@RequestParam(value = "deptJoinDateString", required = false) String deptJoinDateString, ModelMap m) {
		// 测试修改
		logger.info("{} - 用户编辑提交,user={},deptJoinDateString={}", prefix, user, deptJoinDateString);
		ResponseData<Integer> data = checkUserForm(user, user.getRoleIds());
		if (data.getCode() == -1) {
			return data;
		}
		user.setDeptJoinDate(DateUtils.StringToDate(deptJoinDateString));
		if (StringUtils.isNotEmpty(user.getIdCard()) && userService.hasOthersByIdCard(user.getIdCard(), user.getId())) {
			logger.info("该身份证号已被使用!");
			data.setCode(-1);
			data.setMessage("该身份证号已被使用!");
			return data;
		}
		if (userService.hasOthersByTel(user.getTel(), user.getId())) {
			logger.info("该手机号已被使用!");
			data.setCode(-1);
			data.setMessage("该手机号已被使用!");
			return data;
		}

		if (userService.hasOthersByStaffNo(user.getStaffNo(), user.getId())) {
			logger.info("该工号已被使用!");
			data.setCode(-1);
			data.setMessage("该工号已被使用!");
			return data;
		}

		userService.editUser(user, ShiroUtil.getLoginUserId());

		data.setMessage("编辑成功!");
		return data;
	}

	private ResponseData<Integer> checkUserForm(SysUser user, String roleIds) {
		ResponseData<Integer> data = new ResponseData<Integer>();

		String idCard = user.getIdCard();
		String mobile = user.getTel();
		String staffNo = user.getStaffNo();
		if (!StringUtils.isNotEmpty(user.getRealName())) {
			logger.info("姓名不能为空!");
			data.setCode(-1);
			data.setMessage("姓名不能为空!");
			return data;
		}

		if (!StringUtils.isNotEmpty(staffNo)) {
			logger.info("工号不能为空!");
			data.setCode(-1);
			data.setMessage("工号不能为空!");
			return data;
		}
		if (!StringUtils.isNotEmpty(mobile)) {
			logger.info("手机号不能为空!");
			data.setCode(-1);
			data.setMessage("手机号不能为空!");
			return data;
		}
		if (user.getDeptId() == null) {
			logger.info("部门不能为空!");
			data.setCode(-1);
			data.setMessage("部门不能为空!");
			return data;
		}
		if (user.getDutyState() == null) {
			logger.info("岗位状态不能为空!");
			data.setCode(-1);
			data.setMessage("岗位状态不能为空!");
			return data;
		}
		if (!StringUtils.isNotEmpty(roleIds)) {
			logger.info("角色不能为空!");
			data.setCode(-1);
			data.setMessage("角色不能为空!");
			return data;
		}

		if (StringUtils.isNotEmpty(idCard) && idCard.length() != 18) {
			logger.info("身份证号格式错误!");
			data.setCode(-1);
			data.setMessage("身份证号格式错误!");
			return data;
		}

		if (!CommonUtil.checkMobile(mobile)) {
			logger.info("手机号格式错误!");
			data.setCode(-1);
			data.setMessage("手机号格式错误!");
			return data;
		}
		return data;
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "密码重置", type = LogConstans.type_opt_reset)
	@ResponseBody
	@PostMapping(value = "/pwdReset")
	public ResponseData<String> pwdReset(@RequestParam(value = "id", required = false) Integer id) {
		logger.info("{}-密码重置,id={}", prefix, id);
		ResponseData<String> data = new ResponseData<String>();
		userService.resetPassword(id);
		logger.info("密码重置成功!默认密码为giian123456");
		return data;
	}

	@Log(module = LogConstans.SYSTEM_USER, description = "删除用户", type = LogConstans.type_opt_del)
	@ResponseBody
	@PostMapping(value = "/del")
	public ResponseData<String> delUser(@RequestParam(value = "ids", required = false) String ids) {
		logger.info("{}-删除用户,ids={}", prefix, ids);
		ResponseData<String> data = new ResponseData<String>();
		userService.delByBatch(ids, ShiroUtil.getLoginUserId());
		data.setMessage("删除成功!");
		return data;
	}

	@ResponseBody
	@PostMapping(value = "/submCoverForm/{updateCover}")
	public ResponseData<String> submCoverForm(@PathVariable(name = "updateCover", required = false) Integer updateCover,
			SysUserCoverFormModel model, HttpSession session) {
		// logger.info("coverModel:{}",coverModel);
		ResponseData<String> data = new ResponseData<String>();

		List<SysUser> userList = new ArrayList<>();
		List<SysUserSuccessModel> successList = (List<SysUserSuccessModel>) session
				.getAttribute("importUser_success" + ShiroUtil.getLoginUserId());
		successList.forEach(m -> {
			SysUser user = new SysUser();
			user.setDeptId(m.getDeptId());
			user.setDeptJoinDate(m.getDeptJoinDate());
			user.setDutyState(m.getDutyState() == "在岗" ? 1 : 2);
			user.setIdCard(m.getIdCard());
			user.setRealName(m.getRealName());
			user.setUserName(m.getUserName());
			user.setTel(m.getTel());
			user.setStaffNo(m.getStaffNo());
			user.setRoleIds(m.getRoleIds());

			userList.add(user);
		});

		if (updateCover == 1) {
			userService.processImportUserList(model.getUsers(), userList, ShiroUtil.getLoginUserId());
		}
		if (updateCover == 0) {
			userService.processImportUserList(null, userList, ShiroUtil.getLoginUserId());
		}

		data.setMessage("导入成功!");
		return data;
	}

	/**
	 * 系统管理：个人中心
	 */
	@RequestMapping("profile")
	public String profile(HttpSession session, ModelMap m) {
		Integer uid = ShiroUtil.getLoginUserId();
		SysUser user = userService.getById(uid);
		SysUserModel model = userService.toUserModel(user);
		// m.put("user", model);
		m.put("the_user", model); // 用user作key，页面一直取不到user.roleNames这个值，不知原因
		return basePath + "profile";
	}

	/**
	 * 密码修改
	 */
	@ResponseBody
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseData<String> changePassword(String oldPassword, SysUser user, HttpSession session) {
		ResponseData<String> data = new ResponseData<String>();
		Integer uid = ShiroUtil.getLoginUserId();
		user.setId(uid);
		int result = userService.changePassword(user, oldPassword);
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
