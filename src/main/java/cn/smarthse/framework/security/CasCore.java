package cn.smarthse.framework.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 《CAS Token生成器》
 * <li>通过签名参数生成TOKEN
 * 
 * @Project:  GIIANTECH CORE
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2016-2-1-下午2:20:38
 */
public class CasCore {
	
	// 字符编码格式 目前支持  utf-8
	public static String input_charset = "utf-8";
	
	/**
	 * 通过参数生成签名
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
	}
	
	 /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @param key	软件购买、试用时生成的KEY
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara, String key) {
    	String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	return  MD5.sign(prestr, key, input_charset);
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @param psoftkey		企业软件生成得到的KEY
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String psoftkey) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara, psoftkey);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        return sPara;
    }
    
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param psoftkey		企业软件生成得到的KEY
     * @param soft_gateway	软件CAS登录地址
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String psoftkey, String soft_gateway) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, psoftkey);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"cassubmit\" name=\"cassubmit\" action=\"" + soft_gateway
                      + "?_input_charset=" + input_charset + "\" method=\"GET\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"gosoft\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['cassubmit'].submit();</script>");

        return sbHtml.toString();
    }
    

    /**
     * 验证sign是否正确
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verifySign(Map<String, String> params, String publicKey )  {
    	//请求的验证结果
    	String responseTxt = "true";
    	//TODO 可向服务端请求sign的有效性
    	
    	//获取返回时的签名验证结果
    	String sign = "";
 	    if(params.get("sign") != null) {sign = params.get("sign");}
 	    boolean isSign = getSignVeryfy(params, sign, publicKey);


        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @param isSort 是否排序
     * @return 生成的签名结果
     */
	private static boolean getSignVeryfy(Map<String, String> Params, String sign, String publicKey) {
    	//过滤空值、sign与sign_type参数
    	Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
    	String preSignStr = createLinkString(sParaNew);
    	
        //获得签名验证结果
        return MD5.verify(preSignStr, sign, publicKey, input_charset);
    }

    
    
}
