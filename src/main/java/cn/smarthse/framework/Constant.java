package cn.smarthse.framework;

/**
 * 系统常量
 **/

public class Constant {
	/**character encoding.**/
	public static final String characterEncoding = "utf-8";	
	
	public static final String areaId = "areaId";
	public static final String areaName = "areaName";
	
	/** 登录类型：普通用户+监管人员 */
	public static final String loginType = "loginType";

	/** 业务数据memcache缓存名称 */
	public static final String memCacheName = "HSE-memCache";

	/** 业务数据redis缓存名称 */
	public static final String redisCacheName = "HSE-rediscache";
	
	/** app数据redis缓存名称 */
	public static final String appRedisCacheName = "app-rediscache";
	
	/** 业务数据缓存key生成 */
	public static final String redisKey = "customKeyGenerator";

	public static final Integer CompanyROLE = 100;

	public static final String IGNOR_INIT = "ignor_init"; // 是否忽略初始化

	public static final String userNameMap = "userNameMap"; // 用户id对应姓名
	public static final String staffNameMap = "staffNameMap"; // 员工id对应姓名
	public static final String orgNameMap = "orgNameMap"; // 部门id对应部门名称
	public static final String areaNameMap = "areaNameMap"; // 区域id对应区域名称

	// 常用页面链接
	/** 无权限 */
	public static final String PAGE_403 = "/common/403";
	/** 页面找不到 */
	public static final String PAGE_404 = "/common/404";
	/** 系统内部错误 */
	public static final String PAGE_500 = "/common/500";
	/** 登录 */
	public static final String PAGE_LOGIN = "/login";

	// 查询列表时，返回所有记录的最多记录数
	public static final Integer RECORD_ALL = 1000;

	// 用户证书类型
	public static final Byte certificate_funcdept = 1; // 职能机构任凭文书
	public static final Byte certificate_train = 2; // 职业卫生培训证明

	// 文件类型
	public static final Integer FILETYPE_ALL = 0;
	public static final Integer FILETYPE_PIC = 1;
	public static final Integer FILETYPE_XLS = 2;
	public static final Integer FILETYPE_DOC = 3;
	public static final Integer FILETYPE_PDF = 4;
	public static final Integer FILETYPE_TXT = 5;

	// 参数类型
	public static final String sysParamType = "sysParamType"; // 系统参数类型
	public static final String sysParam = "sysParam"; // 系统参数
	public static final String sysType_job_name = "职务信息";
	public static final String sysType_job = "JOBTITLE_TYPE"; // 职务信息
	public static final String sysType_register = "register_type"; // 企业登记注册类型
	public static final String sysType_business = "national_econ_type"; // 企业国民经济行业分类
	public static final String sysType_companysize = "company_size"; // 企业规模
	// 设备设施类型
	public static final String sysType_device = "device_type"; // 设备设施

	public static final String sysType_device_equip_state = "equip_state"; // 设备状态

	// 特种设备类型 spec_equip_category
	public static final String sysType_device_spec_equip_category = "spec_equip_category";
	// 放射设备类型 radio_apparatus_type
	public static final String sysType_device_radio_apparatus_type = "radio_apparatus_type";
	// 核素名称 radioisotope_name
	public static final String sysType_device_radioisotope_name = "radioisotope_name";
	// 放射源类型
	public static final String sysType_device_radioisotope_type = "radioisotope_type";
	// 源强单位
	public static final String sysType_device_radio_strength_unit = "radio_strength_unit";
	// 射线装置类型
	public static final String sysType_device_radio_emit_device_type = "radio_emit_device_type";
	// 计量单位
	public static final String sysType_material_unit = "material_unit";
	// 物料状态
	public static final String sysType_material_state = "material_state";
	// 物料类别
	public static final String sysType_material_type = "material_type";
	// 危险类别
	public static final String sysType_hazard_category = "hazard_category";
	// 包装类别
	public static final String sysType_packaging_groups = "packaging_groups";
	// 物料使用状态
	public static final String sysType_material_use_state = "material_use_state";
	// 物料生产状态
	public static final String sysType_material_production_state = "material_production_state";
	// 职业卫生管理制度类型
	public static final String sysType_managsys_occuptype = "managsys_occuptype";
	// 管理制度类别
	public static final String sysType_managsys_type = "managsys_type";
	// 警示标识种类
	public static final String sysType_warnmark_type = "warnmark_type";

	/**
	 * 警示标识type
	 */
	public static final int warningLabelPosition = 24;
	// 警示标识公告分类
	public static final String sysType_warnmark_Notice_type = "warnmark_Notice_type";
	// 法律法规-法律法规标准状态
	public static final String sysType_State_law_standard = "State_law_standard";
	// 法律法规-法律法规标准类别
	public static final String sysType_Type_law_standard = "Type_law_standard";

	/** 放射人员工作证 */
	public static final String sysType_Cert_radio = "25-1";
	/** 辐射安全与防护培训合格证书 */
	public static final String sysType_Cert_safe = "27-1";

	/** 有效 */
	public static final Byte ACTIVE_YES = 1;
	/** 删除 */
	public static final Byte ACTIVE_NO = 0;
	/** 终止 */
	public static final Byte ACTIVE_STOP = -1;

	public static final String OSS_URL_PREFIX = "oss_url_prefix"; // oss路径前缀
	public static final String ACCOUNT_COMPANYID = "account_companyid"; // 登陆用户所在企业id
	public static final String ACCOUNT_COMPANYNAME = "account_companyname"; // 登陆用户所在企业名称
	public static final String ACCOUNT_SESSION_UID = "account_session_uid"; // 登陆用户id
	public static final String ACCOUNT_SESSION_UNAME = "account_session_uname"; // 登陆用户name
	public static final String ACCOUNT_SESSION_STAFFID = "account_session_ustaffid"; // 登陆用户员工编号
	public static final String ACCOUNT_USER_ICON = "account_user_icon"; // 用户头像

	public static final String ACCOUNT_SESSION_VCODE = "account_session_vcode"; // 登录验证码
	
	public static final String ACCOUNT_SESSION_ADMINID = "account_session_adminid"; // 管理员id

	/** COOKIE保存的用户名 **/
	public static final String COOKIE_LOGIN_USERNAME = "account_cookie_username";
	/** COOKIE保存的密码 **/
	public static final String COOKIE_LOGIN_PASSWORD = "account_cookie_password";

	/**
	 * 分割符
	 */
	public static final String SPLIT_CHAR = ",";
	public static final String SPLIT_CHAR_RUSSIAN = "азделител";// 俄语的分隔符为：разделитель，取其中去掉首位的单词

	/**
	 * 查看业务信息
	 */
	public static final String BUSINESS_INFO = "1";

	public static final String SQL_LIKE = "%";

	public static final String STAFF_CACHE_PREFIX = "staff_cache_";

	// 判断是否
	public static final byte YES = 1;
	public static final byte NO = 0;
	// 已删除标识
	public static final String deleteTitle = "(已删除)";

	/**
	 * 首页提示信息详情
	 */
	public static final Integer hintDetail_health = 1; // 体检提示信息详情
	public static final Integer hintDetail_deviceCheck = 2; // 设备性能检测提示信息详情
}
