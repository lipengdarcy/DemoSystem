package cn.smarthse.framework.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 系统模块的自动生成编号工具
 */
public class SerialTool {

	private static SecureRandom random = new SecureRandom();
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHms");

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成编号
	 * <li>规则：PREFIX + 14位日期 + 3位随机数
	 * 
	 * @param prefix
	 *            前缀名
	 * @return
	 */
	public static String generateNo(String prefix) {
		return String.format("%s%s%s", prefix, format.format(new Date()), Math.abs(random.nextInt(1000)));
	}

	/**
	 * 生成短信验证码
	 * 
	 * @return 短信验证码（6位）
	 */
	public static String generateMobileCode() {
		Integer v = Math.abs(random.nextInt(1000000));
		if(v<100000)
			return generateMobileCode();
		return String.format("%s", v);
	}

	public static void main(String[] args) {
		System.out.println(SerialTool.generateMobileCode());
		System.out.println(SerialTool.uuid());
		System.out.println(SerialTool.uuid().length());

	}

}
