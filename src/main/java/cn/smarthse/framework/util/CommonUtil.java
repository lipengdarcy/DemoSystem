package cn.smarthse.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 《通用工具类》
 * 
 * 
 * @Project:  GIIANTECH COMMON
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-4-19-上午9:57:14
 */
public class CommonUtil {
//	private static Gson gson = new Gson();
	
	private static String[] units = new String[] { "零", "一", "二", "三", "四",	"五", "六", "七", "八", "九", "十" };

	// 周期列表信息
	private static String[] cycles = new String[] { "零", "一", "二", "三", "四",	"五", "六", "七", "八", "九", "十","十一","十二"};

	
	/**
	 * 将对象转换为json格式
	 * 
	 * @param object
	 * @return
	 */
/*	public static String object2json(Object object) {
		return gson.toJson(object);
	}
*/
	/**
	 * 转换code与message为json格式
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static String codemessage2json(String code, String message) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("code", code);
		map.put("msg", message);

		return getJsonResult(map);
	}

	/**
	 * 转换为JSON
	 * 
	 * @param map
	 * @return
	 */
	public static String getJsonResult(Map<String, Object> map) {
		return JSONObject.toJSON(map).toString();
		//return gson.toJson(map);
	}

	/**
	 * 转换code与json message为json格式返回
	 * 
	 * @param code
	 * @param jsondata
	 * @return
	 *//*
	public static String code2json(String code, Map<String, Object> jsondata) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.putAll(jsondata);

		return gson.toJson(map);
	}
	

	*//**
	 * 
	 * @param code
	 * @param list
	 * @return
	 *//*
	public static String code2jlist(String code, List list) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("list", gson.toJson(list));

		return gson.toJson(map);
	}

	*//**
	 * 转换code与message为json格式
	 * 
	 * @param code
	 * @param message
	 * @return
	 *//*
	public static String code2json(String code, String message) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("code", code);
		map.put("msg", message);

		return gson.toJson(map);
	}
	
	*//**
	 * 将SO转换为JSON格式
	 * @param code			
	 * @param objctName		返回数据名
	 * @param objct			数据对象
	 * @return
	 *//*
	public static String code2data(String code, String objctName, Object objct) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		if(objctName==null || !"".equals(objctName)) {
			objctName = "data";
		}
		map.put(objctName, gson.toJson(objct));

		return gson.toJson(map);
	}
	
*/
	/**
	 * 将10以内的数字转换为中文数字
	 * 
	 * @param num
	 * @return
	 */
	public static String int2ChineseNum(int num) {
		if (num > 11) {
			return String.valueOf(num);
		}

		return units[num];
	}
	public static String int2ChineseCycleName(int num) {
		if (num > 13) {
			return "第"+String.valueOf(num)+"周期";
		}

		return "第"+cycles[num]+"周期";
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
	 * 返回1-max中的随机数
	 * 
	 * @param max
	 * @return
	 */
	public static long getInt1tomaxRandom(long max) {
		return Math.round(Math.random() * (max - 10) + 8);
	}

	/**
	 * 提取页面图片列表
	 * 
	 * @param html
	 * @return
	 */
	public static List<String> getHtmlImages(String html) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*src=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(html);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); // 匹配src
			while (m.find()) {
				String url = m.group(1);
				url = url.replace("'", "");
				pics.add(url);
			}
		}
		return pics;
	}

	/**
	 * 截取标题、文本内容 <li>若文本长度不足len时，返回全部
	 * 
	 * @param text
	 *            原始文本长度
	 * @param len
	 *            截取长度
	 * @return
	 */
	public static String getTextLen(String text, int len) {
		if (text == null || "".equals(text))
			return "";

		text = text.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>",
				"");
		text = text.replaceAll("[(/>)<]", "");

		if (text.length() >= len) {
			// 去除格式，提取文本内容
			return text.substring(0, len - 1) + "...";
		}
		return text;
	}

	/**
	 * 过滤文本的HTML标签
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr){ 
		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式   
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式   
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式   
           
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);   
        Matcher m_script=p_script.matcher(htmlStr);   
        htmlStr=m_script.replaceAll(""); //过滤script标签   
           
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);   
        Matcher m_style=p_style.matcher(htmlStr);   
        htmlStr=m_style.replaceAll(""); //过滤style标签   
           
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);   
        Matcher m_html=p_html.matcher(htmlStr);   
        htmlStr=m_html.replaceAll(""); //过滤html标签   

        return htmlStr.trim(); //返回文本字符串   
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
	
	
	/**
	 * 字符串重新排序
	 * 
	 * @param sort
	 *            字符串以逗号隔开
	 * @return
	 */
	public static String str2Sort(String sort) {
		if (sort == null || "".equals(sort.trim())) {
			return sort;
		}

		String[] ary = sort.split(",");
		Arrays.sort(ary);

		return StringUtils.join(ary, ",");
	}

	/**
	 * 字符串重新排序
	 * 
	 * @param sort
	 *            字符串以逗号隔开
	 * @return
	 */
	public static String str2Sort2(String sort) {
		if (sort == null || "".equals(sort.trim())) {
			return sort;
		}

		sort = sort.replaceAll(" ", ",").replaceAll("\\[", "").replaceAll("\\]", ",");

		String[] ary = sort.split(",");
		Comparator comp = Collator.getInstance(java.util.Locale.CHINESE);
		Arrays.sort(ary, comp);
		//sort = sort.replaceAll("\\[", "").replaceAll("\\]", ",");
		return StringUtils.join(ary, ",");

	}
	
	 /**
     * 将字符串资源转换为List资源
     * @param urls
     * @return
     */
    public static List<String> str2list(String str){
    	List<String> list = new ArrayList<String>();
    	if(!StringUtils.isEmpty(str)){
			String[] words = str.split("\\s+|[，,]");
			for(String word : words){ 
				if(!StringUtils.isEmpty(word)){
					list.add(word);
				}
			}
		}
    	
    	return list;
    }

	/**
	 * TODO 字符串生成字符分隔 <li>利用正则实现替换
	 * 
	 * @param sort
	 *            字符串以逗号隔开
	 * @return
	 */
	public static String ary2str(String sort, String replacement) {
		if (sort == null || "".equals(sort.trim())) {
			return sort;
		}

		// sort = sort.replaceAll("\\[", "").replaceAll("\\]", ",");
		String[] ary = sort.split(",");
		Arrays.sort(ary);

		StringBuffer result = new StringBuffer();
		for (String item : ary) {
			if ("[]".equals(replacement)) { // 以中括号的形式分开
				result.append("[").append(item).append("]");
			} else if ("{}".equals(replacement)) {// 以大括号的形式分开
				result.append("{").append(item).append("}");
			} else if ("()".equals(replacement)) {// 以小括号的形式分开
				result.append("(").append(item).append(")");
			} else if ("<>".equals(replacement)) {// 以尖括号
				result.append("<").append(item).append(">");
			} else {
				result.append(item).append(replacement);
			}
		}

		return result.toString();
	}


	/**
	 * 移除数组中重复的元素
	 * 
	 * @param arry
	 * @return
	 */
	public static String[] removeRepeat(String arry[]) {
		// 第一步：将字符串数组转化为字符串list；
		List<String> strList = Arrays.asList(arry);
		// 第二步：将字符串list转化为hashset.利用hashset无重复元 素的特性解决问题。
		Set<String> strSet = new HashSet<String>(strList);
		String[] newArray = new String[strSet.size()];
		Iterator<String> ite = strSet.iterator();
		for (int i = 0; ite.hasNext(); i++) {
			newArray[i] = ite.next();
		}
		return newArray;
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
     * 检查是否为空 字符串,空：true,不空:false  
     *  
     * @param value  
     * @return  
     */  
     public static boolean checkNull(String value) {  
         return value == null || "".equals(value.trim());  
     }
    
     /**
      * 需要强制更新的字段
      * @param dest
      * @param fieldNames
      * @throws IllegalAccessException
      * @throws InvocationTargetException
      */
	public static void forceUpdatable(Object dest, String[] fieldNames) throws IllegalAccessException, InvocationTargetException {
		if(fieldNames!=null && fieldNames.length>0) {
			forceUpdatable(dest, Arrays.asList(fieldNames));
		}
	}
	public static void forceUpdatable(Object dest, List<String> fieldNames) throws IllegalAccessException, InvocationTargetException {
		if(fieldNames!=null && fieldNames.size()>0) {
			for(String fieldName : fieldNames) {
				BeanUtils.copyProperty(dest, fieldName+"Updable", true);
			}
		}
	}
	public static void forceUpdatableIgnore(Object obj, List<String> ignoreNames) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 统一在后台添加 Updable 后缀
		if(ignoreNames!=null && ignoreNames.size()>0) {
			for(int i=0;i<ignoreNames.size();i++) {
				ignoreNames.set(i, ignoreNames.get(i)+"Updable");
			}
		}
		java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if(name.endsWith("Updable") && fields[i].getType()==Boolean.class 
            		&& !ignoreNames.contains(name)) {
            	PropertyUtils.setProperty(obj, name, true);
            }
        }
	}
	/**
	 * 
	 * 
	 * @Comments:  <排除指定的字段， 其他全部需要强制更新>
	 * @author zhoulj(周利军) [1217102780@qq.com]
	 * @since 2017年7月14日-下午2:46:09
	 * @param obj
	 * @param fieldNames
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException 
	 */
	public static void forceUpdatableIgnore(Object obj, String[] ignoreNames) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<String> list = new ArrayList<String>();
		if(ignoreNames!=null && ignoreNames.length>0) { 
			list = Arrays.asList(ignoreNames);
		}
		forceUpdatableIgnore(obj,list);
	}
	
	/**
	 * 解决URL中{}的参数名
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-9-6-下午3:19:04
	 * @param url
	 * @return
	 */
	public static List<String> urlFormat(String url){  
        List<String> list = new ArrayList<>();  
        Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}");   
        Matcher matcher = pattern.matcher(url);   
        while(matcher.find()){   
            String t = matcher.group(1);   
            list.add(t);  
        }  
        return list;  
    }
	
	/**
	 * 解决URL中{}的参数名
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-9-6-下午3:19:04
	 * @param url
	 * @return
	 */
	public static String urlReplace(String url, String paramName, String keyvalue){  
		String fullurl = url;
        List<String> list = urlFormat(url);
        for (String s : list) {  
        	if(s.equals(paramName)){
        		fullurl = url.replace("{"+paramName+"}", keyvalue);  
        	}
        }
        
        return fullurl;  
    }
	
	public static boolean listIsEmpty(List list){  
		if(list!=null && !list.isEmpty())  {
			return false;
		}
		return true;
    }
	public static boolean listIsNotEmpty(List list){  
		return !listIsEmpty(list);
    }
	
	public static void main(String[] args) {
		//System.out.println(createLink("http://www.tombaba.cn","/weiweb/1/home?a=1"));
		//System.out.println(createLink("http://www.tombaba.cn","/weiweb/1/home?a=1","token=%s&sid=%s"));
		//System.out.println(compare_date("2014-11-16 00:00:00"));
//		System.out.println(urlReplace("/work/thirdtime/tsprojectInput/finish/{id}?modify=1&lookup=1", "id", "97271100731311835"));
//		System.out.println(urlReplace("/work/thirdtime/tsprojectInput/finish222?modify=1&lookup=1", "id", "97271100731311835"));
//		File file = new File("d:/aaa/bbb.zip");
//		System.out.println(FilenameUtils.getExtension(file.getName()));
//		Date today = new Date();
//		int bday = DateUtil.getDayOfDate(today);
//		int bmonth = DateUtil.getMonthOfDate(today);
//		System.out.println("today:"+today);
//		System.out.println("bmonth:"+bmonth);
//		System.out.println("bday:"+bday);
//		System.out.println("星座:"+getConstellation(bmonth, bday));
//		System.out.println(MD5Util.md5Hex("123456"));
//		List<String> list = new ArrayList<String>();
//		list.add("1.jpg");
//		list.add("2.jpg");
//		System.out.println(StringUtils.join(list.toArray(),","));
//		System.out.println("时间搓："+getMonthLastDay());
//		System.out.println("orderno:"+dateformat(new Date(),"yyyyMMddHH"));
//		System.out.println(str2double("20",0.01d));
//		System.out.println(double2str(1.01d * 2 , "######.00", "0.01"));
//		System.out.println("本期号:"+getWeek());
//		System.out.println(double2str(str2double("20",0.01d),"####.00", "0.01"));
		//System.out.println(delHTMLTag("愿赠于它一对完整的翅膀<img src='http://wei.linkbo.net//upload/wishes/1405017822909thumb.jpg' style='width:198px; height:auto;'></img>"));
		System.out.println(checkIDCard("33032419870202546X"));
	}

}
