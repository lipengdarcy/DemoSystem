package cn.smarthse.business.service.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.oss.ServiceException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.smarthse.business.dao.system.SysUserMapper;
import cn.smarthse.business.entity.system.SysRole;
import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.entity.system.SysUserRole;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.model.system.SysUserSeachModel;
import cn.smarthse.business.service.system.ISysRoleService;
import cn.smarthse.business.service.system.ISysUserRoleService;
import cn.smarthse.business.service.system.ISysUserService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.security.MD5;
import cn.smarthse.framework.util.StringUtil;
import cn.smarthse.framework.util.encode.Digests;
import cn.smarthse.framework.util.encode.Encodes;
import tk.mybatis.mapper.entity.Example;

/**
 * 系统用户Service实现类
 */
@Service
@Transactional(readOnly = true)
public class SysUserServiceImpl extends GenericServiceImpl<SysUser> implements ISysUserService {

	// ======================密码相关========================
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	ISysRoleService roleService;

	@Autowired
	ISysUserRoleService userRoleService;

	@Override
	public Integer getUserId(String username) {
		SysUser user = getUser(username);
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysUserService#getUser(java.lang.String)
	 */
	@Override
	public SysUser getUser(String username) {
		SysUser user = null;
		// step 1 : 根据用户名读取
		user = getUserByUsername(username);
		if (user != null) {
			return user;
		}

		// step 2 : 根据tel读取
		user = getUserByTel(username);

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysUserService#getUser(java.lang.String)
	 */
	@Override
	public SysUser getUserByUsername(String username) {
		SysUser user = null;
		// 读取有效的用户
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("userName", username).andEqualTo("isValid", true);

		user = getOneByExample(example);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysUserService#getUserByTel(java.lang.
	 * String)
	 */
	@Override
	public SysUser getUserByTel(String tel) {
		SysUser user = null;
		// 读取有效的用户
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("tel", tel).andEqualTo("isValid", true);

		user = getOneByExample(example);
		return user;
	}

	@Override
	public List<String> getUserRoleCodeList(Integer userId) {
		return roleService.getUserRoleCodeList(userId);
	}

	@Override
	public List<SysUser> getUserListBySearchModel(SysUserSeachModel searchModel) {
		// TODO 判断用户的必要条件
		return ((SysUserMapper)dao).queryBySearchModel(searchModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysUserService#getUserList(cn.smarthse.
	 * framework.model.JqGridParam,
	 * cn.smarthse.business.model.system.SysUserSeachModel)
	 */
	@Override
	public PageInfo<SysUserModel> getUserList(JqGridParam params, SysUserSeachModel searchModel) {
		logger.info("查询用户列表：searchMode：{}", searchModel);
		// 设置分页
		PageHelper.startPage((int) params.getPage(), (int) params.getRows());

		// 通过Mapper 调用自定义方法
		List<SysUser> list = getUserListBySearchModel(searchModel);
		PageInfo<SysUser> upage = new PageInfo<>(list);
		List<SysUserModel> mlist = new ArrayList<>();

		list.forEach(user -> mlist.add(toUserModel(user)));

		// 转换为PageInfo
		PageInfo<SysUserModel> mpage = new PageInfo<>(mlist);
		mpage.setPageNum(upage.getPageNum());
		mpage.setPageSize(upage.getPageSize());
		mpage.setTotal(upage.getTotal());
		return mpage;
	}

	// 单表查询结果转化成model
	public SysUserModel toUserModel(SysUser user) {
		SysUserModel model = new SysUserModel(user);

		// 角色
		List<SysUserRole> rolelist = userRoleService.getListByUserId(user.getId());
		String roleName = "";
		for (SysUserRole role : rolelist) {
			roleName += (role == null ? "" : role.getRoleName() + "、");
		}
		model.setRoleNames(roleName == "" ? "" : roleName.substring(0, roleName.length() - 1));
		model.setUserRoleList(rolelist);
		return model;
	}

	@Override
	public List<SysUser> getList() {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true);
		return this.getListByExample(example);
	}

	@Override
	public List<SysUser> getListByDeptIdAndCid(Integer deptId, Integer cid) {
		Example example = new Example(SysUser.class);

		Example.Criteria c = example.createCriteria();
		c.andEqualTo("cid", cid).andEqualTo("isValid", true);
		// edit by shao 改写搜索条件,可被getListByCid(Integer cid) 调用
		if (deptId != null && deptId > 0) {
			c.andEqualTo("deptId", deptId);
		}

		return this.getListByExample(example);
	}

	@Override
	public Boolean isUsernameExsists(String username) {
		SysUser user = this.getUserByUsername(username);
		return user == null ? false : true;
	}

	@Override
	public Boolean isIdCardExsists(String idCard) {
		if (StringUtil.isEmpty(idCard))
			return false;
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("idCard", idCard);
		SysUser user = this.getOneByExample(example);
		return user == null ? false : true;
	}

	@Override
	public Boolean isStaffNoExsists(String staffNo) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("staffNo", staffNo);
		SysUser user = this.getOneByExample(example);
		return user == null ? false : true;
	}

	@Transactional(readOnly = false)
	@Override
	public void addByBatch(Integer createBy, List<SysUser> userList) {
		userList.forEach(user -> {
			this.addUser(user, createBy);
		});

	}

	@Transactional(readOnly = false)
	@Override
	public void delByBatch(String userIds, Integer updateBy) {
		logger.info("批量删除userIds={}", userIds);
		String[] idArray = userIds.split(",");
		Arrays.asList(idArray).forEach(userId -> {
			Integer uid = Integer.parseInt(userId);
			this.delSysUser(uid, updateBy);
		});
	}

	@Transactional(readOnly = false)
	@Override
	public void delSysUser(Integer id, Integer updateBy) {
		// 删除用户主表
		SysUser user = new SysUser();
		user.setId(id);
		Integer count = this.delete(user);
		if (count <= 0) {
			logger.info("用户ID为{}的实体不存在!", id);
			throw new ServiceException("删除失败!");
		}

		List<SysUserRole> userRoles = userRoleService.getListByUserId(id);

		// 删除角色用户关系表
		userRoleService.delByUserId(id, updateBy);
	}

	@Transactional(readOnly = false)
	@Override
	public SysUser addUser(SysUser user, Integer createBy) {
		logger.info("新增用户{}开始", user.getRealName());
		/**
		 * 1.重置密码
		 */
		// md5后的16位明文密码
		String plainPassword = MD5.MD5Encode("giian123456");
		// 明文密码
		String plain = cn.smarthse.framework.util.encode.Encodes.unescapeHtml(plainPassword);
		// 登录盐值
		byte[] salt = Digests.generateSalt(SALT_SIZE);

		// byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		//
		user.setPassWord(Encodes.hexEncode(hashPassword));
		// 没有密码验证 所以不需要保存MD5加密后的密码
		// user.setPlanpassword(plainPassword);
		user.setSalt(Encodes.hexEncode(salt));

		// 2.插入SysUser主表
		user.setCid(1);
		SysUser addUser = this.insert(user);

		// 插入SysUserRole表
		Arrays.asList(user.getRoleIds().split(",")).forEach(roleIdString -> {
			Integer roleId = Integer.parseInt(roleIdString);
			SysRole role = roleService.getById(roleId);
			if (role == null) {
				logger.info("找不到角色ID为{}的角色!", roleId);
				throw new ServiceException("新增用户失败!");
			}
			SysUserRole userRole = new SysUserRole();
			userRole.setUid(addUser.getId());
			userRole.setRoleId(roleId);
			userRole.setRoleName(role.getRoleName());

			userRoleService.insert(userRole);

		});

		return addUser;

	}

	@Transactional(readOnly = false)
	@Override
	public void editUser(SysUser user, Integer updateBy) {
		logger.info("编辑用户,user={}", user);
		// 更新用户主表
		this.update(user);

		// 角色用户表->删除旧纪录
		userRoleService.delByUserId(user.getId(), updateBy);

	}

	@Override
	public Boolean hasOthersByTel(String tel, Integer userId) {
		logger.info("校验ID为{}的用户的手机号({})是否被其他用户使用!", userId, tel);
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("tel", tel);
		List<SysUser> list = this.getListByExample(example);

		Boolean hasOthers = false;
		if (list.size() > 1)
			hasOthers = true;
		if (list.size() == 1 && list.get(0).getId() != userId)
			hasOthers = true;

		return hasOthers;
	}

	@Override
	public Boolean hasOthersByIdCard(String idCard, Integer userId) {
		logger.info("校验ID为{}的用户的身份证({})是否被其他用户使用!", userId, idCard);
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("idCard", idCard);
		List<SysUser> list = this.getListByExample(example);

		Boolean hasOthers = false;
		if (list.size() > 1)
			hasOthers = true;
		if (list.size() == 1 && list.get(0).getId() != userId)
			hasOthers = true;

		return hasOthers;
	}

	@Override
	public Boolean hasOthersByStaffNo(String staffNo, Integer userId) {
		logger.info("校验ID为{}的用户的工号({})是否被其他用户使用!", userId, staffNo);
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("staffNo", staffNo);
		List<SysUser> list = this.getListByExample(example);

		Boolean hasOthers = false;
		if (list.size() > 1)
			hasOthers = true;
		if (list.size() == 1 && list.get(0).getId() != userId)
			hasOthers = true;

		return hasOthers;
	}

	@Override
	public Boolean isTelExsists(String tel) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("tel", tel);
		return this.getOneByExample(example) == null ? false : true;
	}

	@Transactional(readOnly = false)
	@Override
	public void updateByBatch(SysUser[] users, Integer updateBy) {
		Arrays.asList(users).forEach(user -> {
			this.editUser(user, updateBy);
		});

	}

	@Transactional(readOnly = false)
	@Override
	public void processImportUserList(SysUser[] coverList, List<SysUser> successList, Integer updateBy) {
		this.addByBatch(updateBy, successList);
		// successList.forEach(user -> {
		// user.setId(null);
		// this.addUser(user, updateBy);
		// });

		if (coverList != null) {
			Arrays.asList(coverList).forEach(user -> {
				SysUser sysuser = this.getUserByStaffNo(user.getStaffNo());
				user.setId(sysuser.getId());
			});
		}

		if (coverList != null)
			this.updateByBatch(coverList, updateBy);

	}

	@Override
	public SysUser getByIdCard(String idCard) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("idCard", idCard);
		List<SysUser> list = this.getListByExample(example);
		if (list.size() > 1) {
			throw new ServiceException("有多个身份证相同的用户!idCard=" + idCard);
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SysUser getUserByStaffNo(String staffNo) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("isValid", true).andEqualTo("staffNo", staffNo);
		List<SysUser> list = this.getListByExample(example);
		if (list.size() > 1) {
			throw new ServiceException("有多个工号相同的用户!staffNo=" + staffNo);
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 通过明文单次md5加密的密码和盐值获取密文形式的密码
	 * 
	 * @param md5Password
	 *            md5加密的密码（如：明文为111111，则md5Password为96e79218965eb72c92a549dd5a330112）
	 * @param salt
	 *            存储在数据库中的盐值，是创建用户时候随机生成的
	 */
	private String getPassword(String md5Password, String salt) {
		byte[] saltbyte = null;
		saltbyte = Encodes.decodeHex(salt);
		byte[] hashPassword = Digests.sha1(md5Password.getBytes(), saltbyte, HASH_INTERATIONS);
		String result = Encodes.hexEncode(hashPassword);
		return result;
	}

	public int changePassword(SysUser user, String oldPassword) {
		SysUser record = null;
		// 找回密码输入的条件只有手机号，没有uid
		if (user.getTel() != null) {
			record = this.getUserByTel(user.getTel());
			// 通过手机号找用户，用户不存在，直接返回
			if (record == null) {
				return -1;
			}
		} else {
			record = this.getById(user.getId());
		}
		if (oldPassword != null) {
			String pass = this.getPassword(oldPassword, record.getSalt());
			// 原密码错误，直接返回
			if (!pass.equals(record.getPassWord())) {
				return -2;
			}
		}

		// 原密码正确，更新密码
		String newPassword = this.getPassword(user.getPassWord(), record.getSalt());
		user.setPassWord(newPassword);
		user.setId(record.getId());
		this.update(user);
		return 0;
	}

	public int resetPassword(Integer uid) {
		SysUser user = this.getById(uid);
		// md5后的16位明文密码
		String plainPassword = MD5.MD5Encode("giian123456");
		// 明文密码
		String plain = cn.smarthse.framework.util.encode.Encodes.unescapeHtml(plainPassword);
		// 登录盐值
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		user.setPassWord(Encodes.hexEncode(hashPassword));
		user.setSalt(Encodes.hexEncode(salt));
		return this.update(user);
	}

}
