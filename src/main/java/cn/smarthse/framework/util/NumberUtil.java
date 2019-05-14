/**
 * 
 */
package cn.smarthse.framework.util;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 《数字工具类,继承apache.NumberUtils》
 * 
 * 
 * @Project:  smarthse-common1.0
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-9-4-下午3:09:31
 */
public class NumberUtil extends NumberUtils {

	/**
	 * 通过Map取值,若出错,或者无则返回defaultValue
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-9-4-下午3:10:30
	 * @param data
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getValueByMap(Map<String, Object> data, String key, int defaultValue){
		int result = defaultValue;
		try{
			Object value = data.get(key);
			if(value!=null){
				result = toInt(String.valueOf(value));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 生成范围随机数
	 * @param min		最小数
	 * @param max		最大数
	 * @return
	 */
	public static int randomRange(int min, int max){
		Random random = new Random();

        return random.nextInt(max)%(max-min+1) + min;
	}
}
