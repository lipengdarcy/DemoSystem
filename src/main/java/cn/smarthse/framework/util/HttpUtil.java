package cn.smarthse.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;

/**
 * HTTP相关的操作
 * 
 * <p>
 * 1、发送GET请求；
 * <p>
 * 2、发送POST请求。
 * 
 */

public class HttpUtil {

	public static CloseableHttpClient httpClient = null;
	public static HttpClientContext context = null;
	public static CookieStore cookieStore = null;
	public static RequestConfig requestConfig = null;

	static {
		init();
	}

	private static void init() {
		context = HttpClientContext.create();
		cookieStore = new BasicCookieStore();
		// 配置超时时间（连接服务端超时1秒，请求数据返回超时2秒）
		requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000)
				.setConnectionRequestTimeout(60000).build();
		// 设置默认跳转以及存储cookie
		httpClient = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
	}

	/**
	 * 是否访问静态文件
	 * 
	 * @param uri
	 *            uri链接
	 * @return 返回状态
	 */
	public static boolean isStaticResource(String uri) {
		if (uri.startsWith("/static"))
			return true;
		String[] exts = new String[] { "jpg", "gif", "js", "png", "css", "doc", "xls", "swf", "ico" };
		for (String ext : exts) {
			if (uri.toUpperCase().endsWith(ext.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 *            应答
	 * @param cookieName
	 *            cookie名
	 * @param cookieValue
	 *            cookie值
	 * @param time
	 *            cookie生存时间
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int time) {
		// //System.out.println("write :" + cookieName + " " + cookieValue);
		try {
			if (cookieValue != null)
				cookieValue = URLEncoder.encode(cookieValue, "UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
			// //System.out.println(ex);
		}

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void addCookie(HttpServletResponse response, String domain, String path, String cookieName,
			String cookieValue, int time) {
		try {
			cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
		} catch (Exception ex) {
		}
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setDomain(domain);
		cookie.setPath(path);
		response.addCookie(cookie);
		// //System.out.println("write " + cookieName);
	}

	public static void addCookie1(HttpServletResponse response, String cookieName, String cookieValue, int time) {

		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName, String domain, String path) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (domain.equals(cookies[i].getDomain()) && path.equals(cookies[i].getPath())
						&& cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 根据cookie名称取得cookie的值
	 * 
	 * @param HttpServletRequest
	 *            request request对象
	 * @param name
	 *            cookie名称
	 * @return string cookie的值 当取不到cookie的值时返回null
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {

				if (cookieName.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	public static String getURL(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString != null)
			return (new StringBuilder()).append(sb.toString()).append("?").append(queryString).toString();
		else
			return sb.toString();
	}

	/**
	 * 
	 */
	public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		else
			return Integer.parseInt(s);
	}

	public static int getInt(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Integer.parseInt(s);
	}

	public static Long getLong(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Long.parseLong(s);
	}

	public static Long getLong(HttpServletRequest request, String paramName, Long defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		else
			return Long.parseLong(s);
	}

	public static String getString(HttpServletRequest request, String paramName, String defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		else
			return s;
	}

	public static String getString(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			throw new NullPointerException(
					(new StringBuilder()).append("Null parameter: ").append(paramName).toString());
		else
			return s;
	}

	public static boolean getBoolean(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Boolean.parseBoolean(s);
	}

	public static boolean getBoolean(HttpServletRequest request, String paramName, boolean defaultValue) {
		String s = request.getParameter(paramName);
		if (s == null || s.equals(""))
			return defaultValue;
		else
			return Boolean.parseBoolean(s);
	}

	public static float getFloat(HttpServletRequest request, String paramName) {
		String s = request.getParameter(paramName);
		return Float.parseFloat(s);
	}

	public static Object createFormBean(HttpServletRequest request, Class c) {
		Object bean;
		try {
			bean = c.newInstance();
		} catch (Exception e) {
			return new Object();
		}
		Method ms[] = c.getMethods();
		for (int i = 0; i < ms.length; i++) {
			String name = ms[i].getName();
			if (!name.startsWith("set"))
				continue;
			Class cc[] = ms[i].getParameterTypes();
			if (cc.length != 1)
				continue;
			String type = cc[0].getName();
			try {
				String prop = (new StringBuilder()).append(Character.toLowerCase(name.charAt(3)))
						.append(name.substring(4)).toString();
				String param = getString(request, prop);
				if (param == null || param.equals(""))
					continue;
				if (type.equals("java.lang.String")) {
					ms[i].invoke(bean, new Object[] { htmlEncode(param) });
					continue;
				}
				if (type.equals("int") || type.equals("java.lang.Integer")) {
					ms[i].invoke(bean, new Object[] { new Integer(param) });
					continue;
				}
				if (type.equals("long") || type.equals("java.lang.Long")) {
					ms[i].invoke(bean, new Object[] { new Long(param) });
					continue;
				}
				if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
					ms[i].invoke(bean, new Object[] { Boolean.valueOf(param) });
					continue;
				}
				if (type.equals("float") || type.equals("java.lang.Float")) {
					ms[i].invoke(bean, new Object[] { new Float(param) });
					continue;
				}
				if (!type.equals("java.util.Date"))
					continue;

				Date date = DateUtils.parseDate(param);

				if (date != null)
					ms[i].invoke(bean, new Object[] { date });
				else
					System.err
							.println((new StringBuilder()).append("WARNING: date is null: ").append(param).toString());
			} catch (Exception e) {
				System.err.println((new StringBuilder()).append("WARNING: Invoke method ").append(ms[i].getName())
						.append(" failed: ").append(e.getMessage()).toString());
			}
		}

		return bean;
	}

	public static String htmlEncode(String text) {
		if (text == null || "".equals(text)) {
			return "";
		} else {
			text = text.replace("<", "&lt;");
			text = text.replace(">", "&gt;");
			text = text.replace(" ", "&nbsp;");
			text = text.replace("\"", "&quot;");
			text = text.replace("'", "&apos;");
			return text.replace("\n", "<br/>");
		}
	}

	public static String htmlEncodeNotBlank(String text) {
		if (text == null || "".equals(text)) {
			return "";
		} else {
			text = text.replace("<", "&lt;");
			text = text.replace(">", "&gt;");
			text = text.replace("\"", "&quot;");
			text = text.replace("'", "&apos;");
			return text.replace("\n", "<br/>");
		}
	}

	public static String encode(String text) {
		if (text == null || "".equals(text)) {
			return "";
		} else {
			text = text.replace("<", "&lt;");
			text = text.replace(">", "&gt;");
			text = text.replace("\"", "&quot;");
			text = text.replace("'", "&apos;");
			text = text.replace("&", "&amp;");
			return text.replace("\n", "<br/>");
		}
	}

	public static String encodeHtml(String text) {
		if (text == null || "".equals(text)) {
			return "";
		} else {
			text = text.replace("&lt;", "<");
			text = text.replace("&gt;", ">");
			text = text.replace("&nbsp;", " ");
			text = text.replace("&quot;", "\"");
			text = text.replace("&apos;", "'");
			return text.replace("<br/>", "\n");
		}
	}

	public static String encodeHtml2(String text) {
		if (text == null || "".equals(text)) {
			return "";
		} else {
			text = text.replace("&lt;", "<");
			text = text.replace("&gt;", ">");
			text = text.replace("&nbsp;", " ");
			text = text.replace("&quot;", "");
			text = text.replace("&apos;", "");
			return text.replace("<br/>", "\n");
		}
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	@SuppressWarnings("unchecked")
	public static String subStringHTML(String param, int length, String end) {
		StringBuffer result = new StringBuffer();
		int n = 0;
		boolean isCode = false;
		boolean isHTML = false;
		int i = 0;
		do {
			if (i >= param.length())
				break;
			char temp = param.charAt(i);
			if (temp == '<')
				isCode = true;
			else if (temp == '&')
				isHTML = true;
			else if (temp == '>' && isCode) {
				n--;
				isCode = false;
			} else if (temp == ';' && isHTML)
				isHTML = false;
			if (!isCode && !isHTML) {
				n++;
				if ((new StringBuilder()).append(temp).append("").toString().getBytes().length > 1)
					n++;
			}
			result.append(temp);
			if (n >= length)
				break;
			i++;
		} while (true);
		result.append(end);
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		temp_result = temp_result.replaceAll(
				"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
				"");
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List endHTML = new ArrayList();
		for (; m.find(); endHTML.add(m.group(1)))
			;

		for (int j = endHTML.size() - 1; j >= 0; j--) {
			result.append("</");
			result.append(endHTML.get(j));
			result.append(">");
		}

		return result.toString();
	}

	/**
	 * <p>
	 * 发送GET请求
	 * 
	 * @param url
	 *            GET请求地址
	 */
	public static String doGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		// 执行请求
		CloseableHttpResponse response = null;
		StringBuffer result = new StringBuffer();
		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			// 官方推荐 使用流的形式处理请求结果
			if (entity != null) {
				InputStream content = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line);
				}
				bufferedReader.close();
				EntityUtils.consume(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param parameterMap
	 *            POST请求参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static String doPost(String url) {
		return doPost(url, null, "UTF-8");
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param parameterMap
	 *            POST请求参数
	 * @param encoding
	 *            参数编码
	 */
	public static String doPost(String url, Map<String, String> parameterMap, String encoding) {
		HttpPost httpPost = new HttpPost(url);

		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (parameterMap != null) {
			for (Entry<String, String> entry : parameterMap.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		CloseableHttpResponse response = null;
		StringBuffer result = new StringBuffer();
		try {
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
			if (entity != null) {
				InputStream content = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line);
				}
				bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static void main(String[] args) {
		HttpUtil a = new HttpUtil();
		String s = a.doGet("http://127.0.0.1:8787/print/print?checkRecordId=" + 471684833542328320L);
		System.out.println("-------------------" + s);
	}

}
