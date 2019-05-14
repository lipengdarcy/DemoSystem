package cn.smarthse.framework.util;


/**
 * 
 * 《字符混搅》
 * 页面调用：
 * <li>$!stringMixedUtils.mixedMobile(string) 手机号码混搅
 * <li>$!stringMixedUtils.mixedEmail(string) 电子信箱
 * <li>$!stringMixedUtils.mixedTelphone(string) 固定电话
 * <li>$!stringMixedUtils.mixedIDCard(string) 身份证
 * <li>$!stringMixedUtils.mixed(String val, int startLen, int endLen) 通用混搅
 * 
 * @Project:  GIIANTECH COMMON
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-4-20-下午2:23:13
 */
public class StringMixedUtil {
	
	 /**  
     * 检查中文名输 入是否正确  
     *  
     * @param value  
     * @return  
     */  
     public static boolean checkChineseName(String value, int length) {  
         return value!=null && !"".equals(value) && value.matches("^[\u4e00-\u9fa5]+{1}") && value.length() <= length;  
     }  
	
	/**
	 * 混搅姓名
	 * @param mobile
	 * @return
	 */
	public static String mixedName(String name){
		if(StringUtils.isEmpty(name)) return null;
		//验证是否手机号码
		if(checkChineseName(name, 99)){
			return mixed(name, 1, 0);
		}
		//
		return mixed(name, 1, 0);
	}
	
	/**  
     * 检查手机输入 是否正确  
     *  
     * @param value  
     * @return  
     */  
     public static boolean checkMobile(String value) {  
         return value!=null && !"".equals(value) && value.matches("^[1][0-9]+\\d{9}");  
     }  
	
	/**
	 * 混搅手机号码
	 * @param mobile
	 * @return
	 */
	public static String mixedMobile(String mobile){
		if(StringUtils.isEmpty(mobile)) return null;
		//验证是否手机号码
		if(checkMobile(mobile)){
			return mixed(mobile, 3, 4);
		}
		//
		return mixed(mobile, 3, 1);
	}
	
	/**  
     * 检查 email输入是否正确  
     * 正确的书写格 式为 username@domain  
     * @param value  
     * @return  
     */  
     public static boolean checkEmail(String value) {  
         return value!=null && !"".equals(value) && value.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");  
     }  
	
	/**
	 * 混搅电子邮箱
	 * @param email
	 * @return
	 */
	public static String mixedEmail(String email){
		if(StringUtils.isEmpty(email)) return null;
		//验证是否邮箱
		if(checkEmail(email)){
			//return mixed(email, 2,  email.indexOf('@')+1);
			Integer endLen = email.indexOf('@')+1;
			if(email.length()>= 2 + endLen){
				String start = StringUtils.left(email, 2);
				String end = StringUtils.right(email, endLen);
				return start + "****" + end;
				
			}else{
				return email;
			}
		}
		//
		return mixed(email, 2, 1);
	}
	
	/**  
     * 检查电话输入 是否正确  
     * 正确格 式 012-87654321、0123-87654321、0123－7654321  
     * @param value  
     * @return  
     */  
     public static boolean checkTel(String value) {  
         return value!=null && !"".equals(value) && value.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)");  
     }  
	
	/**
	 * 混搅固定电话
	 * @param email
	 * @return
	 */
	public static String mixedTelphone(String tel){
		if(StringUtils.isEmpty(tel)) return null;
		//验证是否固定电话
		if(checkTel(tel)){
			return mixed(tel, 3,  6);
		}
		//
		return mixed(tel, 3, 1);
	}
	
	/**  
     * 检查身份证是 否合法,15位或18位  或者 17+X
     * @param value  
     * @return  
     */  
     public static boolean checkIDCard(String value) {  
         return value!=null && !"".equals(value) && value.matches("\\d{15}|\\d{18}|(^\\d{17}(\\d|X|x)$)");  
     }
	
	/**
	 * 混搅身份证
	 * @param email
	 * @return
	 */
	public static String mixedIDCard(String idc){
		if(StringUtils.isEmpty(idc)) return null;
		//验证是否有效身份证编号
		if(checkIDCard(idc)){
			return mixed(idc, 5,  3);
		}
		//
		return mixed(idc, 5, 1);
	}
	
	
	/**
	 * 混淆真实姓名
	 * @param name
	 * @return
	 */
	public static String mixedRealName(String name){
		if(StringUtils.isEmpty(name)) return null;
		
		String start = StringUtils.left(name, 1);
		
		if(name.length() > 2) {
			String end = StringUtils.right(name, 1);
			return start + "***" + end;
		}
		
		return start + "***";
	}
	
	
	/**
	 * 混搅字符串
	 * @param val			字符串原编码
	 * @param startLen		头长度
	 * @param endLen		尾长度
	 * @return
	 */
	public static String mixed(String val, int startLen, int endLen){
		if(StringUtils.isEmpty(val)) return null;
		
		if(val.length()>= startLen + endLen){
			String start = StringUtils.left(val, startLen);
					//val.substring(0,startLen);
//			System.out.println(endLen);
//			System.out.println(val + ", indexOf(@)= "+(val.indexOf('@')));
//			System.out.println(val + ", lastIndexOf(@)= "+(val.lastIndexOf('@') ));
//			System.out.println(StringUtils.right(val, endLen));
			String end = StringUtils.right(val, endLen);
					//.substring(val.length() - endLen, val.length());
			return start + "***" + end;
			
		}else{
			return val;
		}
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println("姓名："+mixedName("邵建义"));
//		System.out.println("姓名2："+mixedName("诸葛建义"));
//		System.out.println("ERROR 姓名："+mixedName("邵帅"));
//		System.out.println("手机号码："+mixedMobile("13486192782"));
//		System.out.println("ERROR 手机号码："+mixedMobile("1348619278"));
//		System.out.println("邮箱："+mixedEmail("ksgameboy@qq.com"));
		System.out.println("邮箱："+mixedEmail("gf@163.com"));
		System.out.println("邮箱："+mixedEmail("gbx@smarthse.cn"));
//		System.out.println("ERROR 邮箱："+mixedEmail("ksgameboy@qq"));
//		System.out.println("固定电话："+mixedTelphone("0577-88888888"));
//		System.out.println("ERROR　固定电话："+mixedTelphone("ABCD-88888888"));
//		System.out.println("身份证："+mixedIDCard("330324199909099999"));
//		System.out.println("ERROR 身份证："+mixedIDCard("330324199909099DDD"));
	}
}
