package cn.smarthse.framework.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 《字符串工具类》
 * <li>继承org.apache.commons.lang.StringUtils类
 * 
 * @Project: GIIANTECH CORE
 * @Module ID: <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used: <JDK1.7>
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2016-1-28-下午5:10:02
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";
	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 字符串是否含有数字
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2019年4月1日-下午2:00:07
	 * @param content
	 * @return
	 */
	public static boolean hasDigit(String content) {
		if(isBlank(content)) {
			return false;
		}
		
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
	
	/**
	 * ID是否为空
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author Horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2018年2月6日-下午1:29:37
	 * @param id
	 * @return
	 */
	public static boolean isIdIsNull(Long id){
		if(id == null || id.equals(0L)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取指定长度的字符串，后面跟省略号
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author Horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2018年1月26日-下午3:47:33
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getStrByLength(String str,int length){
		if(isBlank(str)){
			return "";
		}
		str=str.trim();
		if(str.length()<=length){
			return str;
		}
		return str.substring(0, length)+"……";
	}
	
	public static boolean isLong(String str){

		if (str == null) {
			return false;
		}
		
		try {
			Long.valueOf(str.trim());
		} catch (Exception e) {
			return false;
		}
		
		
		return true; 
		
	
	}
	
	public static boolean isInteger(String str){

		if (str == null) {
			return false;
		}
		
		try {
			Integer.valueOf(str.trim());
		} catch (Exception e) {
			return false;
		}
		return true; 
		
	
	}
	
	/**
	 * 获取字符串去除非数字字符
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2019年3月26日-下午12:37:55
	 * @param str
	 * @return
	 */
	public static String getNumberStr(String str) {
		if(isBlank(str)) {
			return null;
		}
		
		String number = Pattern.compile("[^0-9]").matcher(str).replaceAll("").trim();
		
		return number;
	}

	/**
	 * 字符串是否数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		
		try {
			Double.valueOf(str.trim());
		} catch (Exception e) {
			return false;
		}
		
		
		return true; 
		
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
     * 检查电话输入 是否正确  
     * 正确格 式 012-87654321、0123-87654321、0123－7654321  
     * @param value  
     * @return  
     */  
     public static boolean checkTel(String value) {  
         return value!=null && !"".equals(value) && value.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)");  
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
     * 检查中文名输 入是否正确  
     *  
     * @param value  
     * @return  
     */  
     public static boolean checkChineseName(String value, int length) {  
         return value!=null && !"".equals(value) && value.matches("^[\u4e00-\u9fa5]+{1}") && value.length() <= length;  
     }  
   
     /**  
     * 检查HTML 中首尾空行或空格  
     * @param value  
     * @return  
     */  
     public static boolean checkBlank(String value) {  
         return value!=null && !"".equals(value) && value.matches("^\\s*|\\s*{1}");  
     }  
   
     /**  
     * 检查字符串是 否含有HTML标签  
     * @param value  
     * @return  
     */  
   
     public static boolean checkHtmlTag(String value) {  
         return value!=null && !"".equals(value) && value.matches("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");  
     }  
   
     /**  
     * 检查URL是 否合法  
     * @param value  
     * @return  
     */  
     public static boolean checkURL(String value) {  
         return value!=null && !"".equals(value) && value.matches("[a-zA-z]+://[^\\s]*");  
     }  
   
     /**  
     * 检查IP是否 合法  
     * @param value  
     * @return  
     */  
     public static boolean checkIP(String value) {  
         return value!=null && !"".equals(value) && value.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");  
     }  
   
     /**  
     * 检查ID是否 合法，开头必须是大小写字母，其他位可以有大小写字符、数字、下划线  
     * @param value  
     * @return  
     */  
     public static boolean checkID(String value) {  
         return value!=null && !"".equals(value) && value.matches("[a-zA-Z][a-zA-Z0-9_]{4,15}{1}");  
     }  
   
     /**  
     * 检查QQ是否 合法，必须是数字，且首位不能为0，最长15位  
     * @param value  
     * @return  
     */  
   
     public static boolean checkQQ(String value) {  
         return value!=null && !"".equals(value) && value.matches("[1-9][0-9]{4,13}");  
     }  
   
     /**  
     * 检查邮编是否 合法  
     * @param value  
     * @return  
     */  
     public static boolean checkPostCode(String value) {  
         return value!=null && !"".equals(value) && value.matches("[1-9]\\d{5}(?!\\d)");  
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
     * 检查输入是否 超出规定长度  
     * @param length  
     * @param value  
     * @return  
     */  
     public static boolean checkLength(String value, int length) {  
         return ((value == null || "".equals(value.trim())) ? 0 : value.length()) <= length;  
     }  
	
	
	
	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}


	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		if (val == null) {
			return 0L;
		}
		try {
			return Long.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}


	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 如果不为空，则设置值
	 * 
	 * @param target
	 * @param source
	 */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)) {
			target = source;
		}
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	public static boolean isNotEmpty(String str) {
		if (str == null)
			return false;
		str = str.trim();
		return !str.equals("");
	}
	/**
	 * 将字符串按规则拆分并转化成int数组，不包含空字符串
	 * */
	public static List<Integer> parseToIntegerList(String str,String spliter) {
		List<Integer> intList = new ArrayList<Integer>();
		if (isNotEmpty(str)) {
			String[] arr = str.split(spliter);
			for (String a : arr) {
				if (isBlank(a))
					continue;
				intList.add(Integer.parseInt(a));
			}
		}
		return intList;
	}
	/**
	 * 将字符串按规则拆分并转化成long数组，不包含空字符串
	 * */
	public static List<Long> parseToLongList(String str,String spliter) {
		List<Long> longList = new ArrayList<Long>();
		if (isNotEmpty(str)) {
			String[] arr = str.split(spliter);
			for (String a : arr) {
				if (isBlank(a)){
					continue;
				}
				longList.add(Long.parseLong(a));
			}
		}
		return longList;
	}
	/**
	 * 将字符串按规则拆分并转化成int数组，包含空字符串
	 * */
	public static List<Integer> parseStringToIntegerListIncludeNull(String str,String spliter) {
		List<Integer> intList = new ArrayList<Integer>();
		if (isNotEmpty(str)) {
			String[] arr = str.split(spliter);
			for (String a : arr) {
				if (isNotEmpty(a))
					intList.add(Integer.parseInt(a));
				else
					intList.add(null);
			}
		}
		return intList;
	}

	/**
	 * 获取字符串的长度，中文字符按2个char计算
	 */
	public static int getStringLength(String str) {
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
			if (m.find()) {
				len += 2;
			} else {
				len++;
			}
		}
		return len;
	}
	
	/**
	 * 将本地文件地址，转换为Web格式
	 * 
	 * @param filePath
	 * @param firstIndex
	 *            Global.FILE_UPLOAD_PATH_LEVEL_FIRST
	 * @return
	 */
	public static String local2url(String filePath, String firstIndex) {
		String webfilepath = "/"
				+ filePath.substring(filePath.indexOf(firstIndex),
						filePath.length());
		try {
			webfilepath = webfilepath.replaceAll("\\\\", "/");
		} catch (Exception e) {
		}

		return webfilepath;
	}
    
	
	public static Boolean validContains(String values, String value, String separator) {
		if(StringUtils.isBlank(values) || StringUtils.isBlank(value)) {
			return false;
		}
		String[] valuesArray = StringUtils.split(values, separator);
		if(valuesArray!=null && valuesArray.length>0) {
			for(String v : valuesArray) {
				if(value.equals(v)) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	
	/**
	 * 
	 * 
	 * @Comments:  排序字符串的获取
	 * @author Horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2017年6月23日-下午6:07:34
	 * @param columName
	 * @param orderType
	 * @param isOerderByPinYin
	 * @return
	 */
	public static String getOrderByStr(String columName,String orderType,boolean isOerderByPinYin) {
		
		if(isOerderByPinYin){
			return "CONVERT( "+columName+" USING gbk ) COLLATE gbk_chinese_ci" + " " +orderType;
		} else {
			return columName + " " +orderType;
		}
	}
	
	/**
	 * url地址是否正确可以访问。
	 * @param urlStr 完整的url地址
	 * @return
	 */
	public static boolean isUrlCurrent(String urlStr){
		int counts = 5;
		boolean isCurrent=false;
		while (counts > 0) {//连接5次，5次都失败，则地址无效
			try {
				URL url = new URL(urlStr);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				int state = con.getResponseCode();
				if (state == 200) {
					isCurrent=true;
					break;
				}
				counts--;
			} catch (Exception ex) {
				counts--;
			}
		}
		return isCurrent;
	}
	
	/**
	 * 
	 * @Comments:  <字符串被被全角或半角波浪线分割成两个字符串>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2017年8月1日-下午6:50:54
	 * @param str
	 * @return
	 */
	public static String[] splitByWaveLine(String str){
		String[]  strs={"",""};
		if(!isBlank(str)){
			if(str.contains("~")){
				strs=str.split("~");
			}else if(str.contains("～")){
				strs=str.split("～");
			}else{
				strs[0]=str;
				strs[1]=str;
			}
		}
		return strs;
	}
	
	/**
	 * 
	 * @Comments:  <字符串被被全角小于符号分割成两个字符串>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2017年8月1日-下午7:14:44
	 * @param str
	 * @return
	 */
	public static String[] splitByLt(String str){
		String[]  strs={"",""};
		if(!isBlank(str)){
			if(str.contains("＜")){
				strs=str.split("＜");
			}else{
				strs[0]=str;
				strs[1]=str;
			}
		}
		return strs;
	}
	
	/**
	 * 
	 * @Comments:  <字符串被被小于等于符号分割成两个字符串>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2017年8月1日-下午7:14:44
	 * @param str
	 * @return
	 */
	public static String[] splitByLte(String str){
		String[]  strs={"",""};
		if(!isBlank(str)){
			if(str.contains("≤")){
				strs=str.split("≤");
			}else{
				strs[0]=str;
				strs[1]=str;
			}
		}
		return strs;
	}
	
	public static String convertArrayToString(String[] array,String spliter) {
		String ids = "";
		if(array!=null&&array.length>0){			
			for(String id:array) {
				ids += id + ",";
			}
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	
    /**
     * 
     * @Comments:  <此方法只能将数字和中文分开，其格式必须是前面是数字后面是中文>
     * @author BinXu(徐斌) [784514607@qq.com]
     * @since 2017年10月10日-下午6:16:17
     * @param str
     * @return
     */
    public static String[] getNumAndChinese(String str){
    	String[]  strs={"0",""};
    	Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
        Matcher m = p.matcher( str );
        int i=0;
        while ( m.find() ) {
        	strs[i]= m.group();
        	if(!isNumber(strs[0])){
        		strs[0]="0";
        		break;
        	}
        	if(i==2){
        		break;
        	}
        	i++;
        }
    	return strs;
    }
    
    /**
     * 获取weindows能后允许的文件（文件夹）名称。去除掉不允许使用的特殊字符
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author Horsy(何世壹) [hsy@smarthse.cn]
     * @since 2017年10月13日-下午2:14:24
     * @param fileName
     * @return
     */
    public static String getFileNameStr(String fileName){
    	if(isBlank(fileName)){
    		return "";
    	}
    	fileName = fileName.replace("/", "#");
    	fileName = fileName.replace("\\", "#");
    	fileName = fileName.replace(":", "#");
    	fileName = fileName.replace("*", "#");
    	fileName = fileName.replace("?", "#");
    	fileName = fileName.replace("<", "#");
    	fileName = fileName.replace(">", "#");
    	fileName = fileName.replace("?", "#");
    	return fileName;
    }
    
    /**
	 * 根据字节转换为KB或者MB
	 * 
	 * @param n
	 *            字节大小
	 * @return
	 */
	public static String b2mb(long filesize) {
		double r = 0.0;
		String sbyte = "";
		if (filesize > (1l * 1024 * 1024 * 1024 * 1024)) {
			sbyte = "T";
			r = Math.round((filesize / 1024 / 1024 / 1024 / 1024) * 10) / 10;
		} else if (filesize > (1l * 1204 * 1024 * 1024)) {
			sbyte = "G";
			r = Math.round((filesize / 1024 / 1024 / 1024) * 10) / 10;
		} else if (filesize > (1l * 1024 * 1024)) {
			sbyte = "M";
			r = Math.round((filesize / 1024 / 1024) * 10) / 10;
		} else if (filesize > (1l * 1024)) {
			sbyte = "K";
			r = Math.round((filesize / 1024) * 10) / 10;
		} else {
			sbyte = "";
			r = filesize;
		}

		return r + sbyte;
	}
	
	/**
	 * 获取Web全路径
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-26-下午4:19:19
	 * @param request
	 * @return
	 */
	public static String getWebFullPath(HttpServletRequest request){
		StringBuffer path = new StringBuffer();
		path.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort())
			.append(request.getContextPath()).append("/");
//		
//		String contextPath = request.getContextPath();    
//		String basePath = request.getScheme()+"://"+request.getServerName()+":"+
//                request.getServerPort()+contextPath+"/";
		
		return path.toString();
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(parseStringToIntegerListIncludeNull("",","));
//		System.out.println(parseStringToIntegerListIncludeNull(",,,",","));
//		System.out.println(parseStringToIntegerListIncludeNull("1,,2,",","));
		
//		 companys:智慧职安环保科技有限公司,浙江建安检测研究院有限公司 ===>containsOnly<====== report.reportCompany : 智慧职安环保科技有限公司
		String reportCompany = "智慧职安环保科技有限公司";
		String companys = "智慧职安环保科技有限公司,浙江建安检测研究院有限公司";
		System.out.println("companys validContains >> "+ StringUtils.validContains(companys, reportCompany, ","));
		
		String companys2 = "中国智慧职安环保科技有限公司";
		System.out.println("companys2 validContains >> "+ StringUtils.validContains(companys2, reportCompany, ","));
		
		String companys3 = "浙江建安检测研究院有限公司";
		System.out.println("companys3 validContains >> "+ StringUtils.validContains(companys3, reportCompany, ","));
		
		String companys4 = "智慧职安环保科技有限公司";
		System.out.println("companys4 validContains >> "+ StringUtils.validContains(companys4, reportCompany, ","));
		
		String reportCompany2 = "杭州士兰明芯科技有限公司";
		String companys5 = "杭州士兰集成电路有限公司,杭州士兰集昕微电子有限公司,杭州士兰明芯科技有限公司（岗前、离岗）";
		System.out.println("companys5 validContains >> "+ StringUtils.validContains(companys5, reportCompany2, ","));
	}
	
	private static BigDecimal ten = new BigDecimal(10);
	
	/**
	 * 字符串转换为bigdecimal。
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author horsy(何世壹) [hsy@smarthse.cn]
	 * @since 2019年3月23日-下午12:23:33
	 * @param str
	 * @return
	 */
	public static BigDecimal str2BigDecimal(String str) {
		if(StringUtils.isBlank(str)) {
			//读数是空的，直接返回空
			return null;
		}
		str = str.toUpperCase();
		if(str.indexOf("E-") == -1) {
			//读数是非指数形式存储的
			if(StringUtils.isNumber(str)) {
				//读数是数字，返回数字的bigdecimal
				return new BigDecimal(str);
			}else {
				//读数不是数字，返回空
				return null;
			}
		}else {
			//读数是指数形式存储的
			String[] readings= str.split("E-");
			if(readings!=null && readings .length ==2 && StringUtils.isNumber(readings[0]) && StringUtils.isNumeric(readings[1]) ) {
				BigDecimal reading1 = new BigDecimal(readings[0]);

				reading1 = reading1.multiply(ten.pow(Integer.valueOf(readings[1])));
				
				return reading1;
			}
		}
		
		
		
		return null;
	}
	
}
