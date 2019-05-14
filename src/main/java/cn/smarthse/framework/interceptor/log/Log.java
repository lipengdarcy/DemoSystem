/**
 * 
 */
package cn.smarthse.framework.interceptor.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 《方法级日志拦截器标识》
 * 
 * 
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 */
@Target({ElementType.METHOD})  
@Documented  
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	
	/**
	 * 操作类型
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-6-23-上午11:29:38
	 * @return
	 */
	public int type() default LogConstans.type_opt ;  
	/**
	 * 模块名
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-6-23-上午11:21:28
	 * @return
	 */
    public LogConstans module() default LogConstans.ANON;  
    
    /**
     * 日志描述
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author JannyShao(邵建义) [ksgameboy@qq.com]
     * @since 2017-6-23-上午11:21:48
     * @return
     */
    public String description() default "";  
	
}
