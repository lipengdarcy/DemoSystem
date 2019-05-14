package cn.smarthse.framework.util;

import java.util.Random;

/**
 * 随机数生成器
 * @author Janny
 *
 */
public class RandomGenerator {
	/**随机值范围内容,TODO 增加A-Z**/
	private static String rangeLower = "0123456789abcdefghijklmnopqrstuvwxyz";
	
	/**随机值数字**/
	private static String rangeNum = "0123456789";
	
	/**随机大小写字符**/
	private static String range = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**随机大写字符**/
	private static String rangeUpCase = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
	
	/**
	 * 生成随机值
	 * @param count	随机值位数
	 * @return
	 */
	public static synchronized String getRandomString(int count)
	{
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++)
			result.append(rangeLower.charAt(random.nextInt(rangeLower.length())));

		return result.toString();
	}
	
	
	/**
	 * 生成随机值
	 * @param count	随机值位数
	 * @return
	 */
	public static synchronized String getRandomNum(int count)
	{
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++)
			result.append(rangeNum.charAt(random.nextInt(rangeNum.length())));
		return result.toString();
	}
	
	
	/**
	 * 生成随机值
	 * @param count	随机值位数
	 * @return
	 */
	public static synchronized String getRandom(int count)
	{
		Random random = new Random();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < count; i++)
			result.append(range.charAt(random.nextInt(range.length())));
		return result.toString();
	}
	
	/**
	 * 随机读取strList中的一个数据,strList字符串用逗号隔开
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月28日-下午5:00:04
	 * @param strList
	 * @return
	 */
	public static synchronized String getRandomByList(String strList) {
		String[] list = strList.split(",");
		if(list.length == 0) {
			return null;
		}
		
		int index = NumberUtil.randomRange(0, list.length-1);
		return list[index];
	}
	
	
	
	public static void main(String[] args) throws Exception {
//    	System.out.println("contains1="+range.toUpperCase());
//    	System.out.println("contains2="+isEmail("邮件@.com"));
//    	System.out.println("contains3="+isEmail("@aa.com"));
//    	System.out.println("contains4="+isEmail("aa@aa.com"));
//    	System.out.println("contains5="+isEmail("aa@bb.com"));
//    	System.out.println("contains6="+isEmail("aac.com"));
//    	System.out.println("contains7="+isEmail("_@b.com"));
//    	System.out.println(getAgentPromCode("0202ZZ"));
//		System.out.println(getBusinessPromCode("02ZZ00", (byte)2));
    }
}
