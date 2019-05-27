package cn.smarthse.business.service.mongo.system.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ServiceException;

import cn.smarthse.business.collection.system.SystemUser;
import cn.smarthse.business.repository.system.SystemUserRepository;
import cn.smarthse.business.service.mongo.system.SystemUserService;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.security.MD5;
import cn.smarthse.framework.util.StringUtil;
import cn.smarthse.framework.util.encode.Digests;
import cn.smarthse.framework.util.encode.Encodes;

/**
 * 系统用户Service实现类
 */
@Service
public class SystemUserServiceImpl extends GenericServiceImpl<SystemUser> implements SystemUserService {

	// ======================密码相关========================
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	SystemUserRepository SystemUserRepository;

	@Autowired
	MongoTemplate MongoTemplate;

	private Query getQuery(SystemUser dataParam) {
		Query query = new Query();
		Criteria c = new Criteria();
		// 用户名
		if (!StringUtil.isEmpty(dataParam.getUserName()))
			c.and("userName").is(dataParam.getUserName());
		// 姓名
		if (!StringUtil.isEmpty(dataParam.getRealName()))
			c.and("realName").is(dataParam.getRealName());
		query.addCriteria(c);
		// 排序
		query.with(new Sort(Direction.ASC, "_id"));
		return query;
	}

	@Override
	public List<SystemUser> getData(SystemUser dataParam) {
		return MongoTemplate.find(getQuery(dataParam), SystemUser.class);
	}

	@Override
	public JqGridData<SystemUser> getGridData(JqGridParam param, SystemUser dataParam) {
		PageRequest pageRequest = PageRequest.of((int) param.getPage() - 1, (int) param.getRows());
		Query query = getQuery(dataParam);
		long count = MongoTemplate.count(query, SystemUser.class);
		query.with(pageRequest);
		List<SystemUser> list = MongoTemplate.find(query, SystemUser.class);
		Page<SystemUser> page = new PageImpl<SystemUser>(list, pageRequest, count);
		JqGridData<SystemUser> data = new JqGridData<SystemUser>(page);
		return data;
	}

	@Override
	public SystemUser getUser(String keyword) {
		SystemUser user = null;
		// step 1 : 根据用户名读取
		user = getUserByUsername(keyword);
		if (user != null) {
			return user;
		}
		// step 2 : 根据身份证读取
		user = getUserByIdCard(keyword);
		if (user != null) {
			return user;
		}
		// step 3 : 根据tel读取
		user = getUserByTel(keyword);
		return user;
	}

	private SystemUser getUserByUsername(String userName) {
		SystemUser queryParam = new SystemUser();
		queryParam.setUserName(userName);
		// 创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
				.withIgnoreCase(true).withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.exact());
		// 创建实例
		Example<SystemUser> example = Example.of(new SystemUser(), matcher);
		Optional<SystemUser> user = SystemUserRepository.findOne(example);
		return user.get();
	}

	private SystemUser getUserByIdCard(String idCard) {
		SystemUser queryParam = new SystemUser();
		queryParam.setIdCard(idCard);
		// 创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
				.withIgnoreCase(true).withMatcher("idCard", ExampleMatcher.GenericPropertyMatchers.exact());
		// 创建实例
		Example<SystemUser> example = Example.of(new SystemUser(), matcher);
		Optional<SystemUser> user = SystemUserRepository.findOne(example);
		return user.get();
	}

	private SystemUser getUserByTel(String tel) {
		SystemUser queryParam = new SystemUser();
		queryParam.setTel(tel);
		// 创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
				.withIgnoreCase(true).withMatcher("tel", ExampleMatcher.GenericPropertyMatchers.exact());
		// 创建实例
		Example<SystemUser> example = Example.of(queryParam, matcher);
		Optional<SystemUser> user = SystemUserRepository.findOne(example);
		return user.get();
	}

	@Override
	public void delUser(String id) {
		// 删除用户主表
		SystemUser user = new SystemUser();
		user.setId(id);
		Integer count = this.delete(user);
		if (count <= 0) {
			logger.info("用户ID为{}的实体不存在!", id);
			throw new ServiceException("删除失败!");
		}
	}

	@Override
	public SystemUser addUser(SystemUser user) {
		// md5后的16位明文密码
		String plainPassword = MD5.MD5Encode("123456");
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

		SystemUser addUser = this.insert(user);
		return addUser;
	}

	@Override
	public void editUser(SystemUser user) {
		// 更新用户主表
		this.update(user);
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

	public int changePassword(SystemUser user, String oldPassword) {
		SystemUser record = null;
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

	public int resetPassword(String uid) {
		SystemUser user = this.getById(uid);
		// md5后的16位明文密码
		String plainPassword = MD5.MD5Encode("123456");
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
