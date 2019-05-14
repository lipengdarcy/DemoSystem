/**
 * 
 */
package cn.smarthse.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 《任务调度配置 taskExecutor》
 * 
 * 
 * @Project:  platform-service
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2018年10月10日-下午2:39:30
 */
@ConfigurationProperties("taskexecutor")
public @Data class TaskExecutorProperties {
//	core_pool_size: 10
//    max_pool_size: 30
//    queue_capacity: 1000
//    keep_alive_seconds: 0
	
	// 线程池大小
    private int core_pool_size;
    // 线程池最大连接数
    private int max_pool_size;
    // 线程池保持激活秒数
    private int keep_alive_seconds;
    // 线程池队列容量
    private int queue_capacity;
}
