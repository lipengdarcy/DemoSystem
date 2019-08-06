package cn.smarthse.business.controller.wechat;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.business.entity.WxDepartment;
import cn.smarthse.business.model.wechat.WxUserModel;
import cn.smarthse.business.service.wechat.IWxDepartmentService;
import cn.smarthse.business.service.wechat.IWxUserService;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import io.swagger.annotations.Api;

/**
 * 微信用户管理（从企业微信同步到系统）
 */
@Api(value = " 微信用户管理", tags = " 微信用户管理")
@Controller
@RequestMapping("wechat/user")
public class WeChatUserController{

	private Logger logger = LogManager.getLogger(this.getClass());

	private final String basePath = "wechat/user/";

	private final String prefix = " 微信用户管理";

	@Autowired
	private IWxDepartmentService IWxDepartmentService;

	@Autowired
	private IWxUserService IWxUserService;

	/**
	 * 部门页面
	 */
	@RequestMapping()
	public String list(HttpSession session, ModelMap m) {
		IWxUserService.syncData(null);
		return basePath + "userList";
	}

	/**
	 * 部门数据
	 * 
	 */
	@ResponseBody
	@GetMapping(value = "/deptData")
	public List<WxDepartment> deptData() {
		List<WxDepartment> list = IWxDepartmentService.selectList();
		return list;
	}

	/**
	 * 用户数据
	 * 
	 */
	@ResponseBody
	@GetMapping(value = "/userData")
	public JqGridData<WxUserModel> userData(HttpSession session, JqGridParam params, WxUserModel model) {
		JqGridData<WxUserModel> data = null;
		model.setCid(ShiroUtil.getLoginCid());
		// 设置分页
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());
		Page<WxUserModel> userList = (Page<WxUserModel>) IWxUserService.query(model);
		data = new JqGridData<WxUserModel>(userList, params);
		return data;
	}

}
