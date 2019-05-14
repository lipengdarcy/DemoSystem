/**
 * 
 */
package cn.smarthse.framework.interceptor.log;

/**
 * 《日志常量定义》
 * 
 * 
 * @Project: smarthse-common1.0
 * @Module ID: <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-6-23-上午11:14:55
 */
public enum LogConstans {
	ANON("-", ""), 
	SYSTEM("system", "系统管理"), 
	SYSTEM_USER("system_user", "用户管理"), 
	SYSTEM_ROLE("system_role", "角色管理"), 
	SYSTEM_LOG("system_log", "系统日志"), 
	
	BASE("base", "基础信息"), 
	BASE_COMPANY("base_company", "公司信息"), 
	BASE_DEVICE("base_device", "设备管理"), 
	BASE_ANALOG("base_analog", "类比资料库"), 
	BASE_SALES("base_sales", "销售目标管理"), 
	
	BUSINESS("business", "业务模块"), 
	//...定义日志类型
	TECHNOLOGY("technology", "技术模块"), 
	//...定义日志类型
	;
	
	/**
	 * 0-异常日志
	 */
	public final static int type_error = 0;
	/**
	 * 1-登录日志
	 */
	public final static int type_login = 1;
	
	/**
	 * 11-单点登录(用户)
	 */
	public final static int type_cas_login = 11;
	
	/**
	 * 2-操作日志
	 */
	public final  static int type_opt = 2;
	/**
	 * 添加类型
	 */
	public final  static int type_opt_add = 20;
	/**
	 * 修改类型
	 */
	public final  static int type_opt_edit = 21;
	/**
	 * 删除
	 */
	public final  static int type_opt_del = 22;
	/**
	 * 导入
	 */
	public final  static int type_opt_import = 23;
	/**
	 * 导出
	 */
	public final  static int type_opt_export = 24;
	/**
	 * 下载
	 */
	public final  static int type_opt_download = 25;
	/**
	 * 上传
	 */
	public final  static int type_opt_upload = 26;
	/**
	 * 重置密码
	 */
	public final  static int type_opt_reset = 27;
	/**
	 * 初始化
	 */
	public final  static int type_opt_init = 28;
	/**
	 * 3-微信日志
	 */
	public final  static int type_weixin = 3;
	/**
	 * 用户提现
	 */
	public final  static int type_weixin_withdraw = 31;
	
	/**
	 * 4-短信日志
	 */
	public final  static int type_smt = 4;
	
	/**
	 * 41-短信发送成功
	 */
	public final  static int type_smt_send_success = 41;
	
	/**
	 * 42-短信发送失败
	 */
	public final  static int type_smt_send_fail = 42;
	
	/**
	 * 5-邮件日志
	 */
	public final  static int type_email = 5;
	
	/**
	 * 模块名
	 */
	public String moduleName;
	/**
	 * 模块编号
	 */
	public String moduleCode;

	LogConstans(String moduleCode, String moduleName) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
	}
}
