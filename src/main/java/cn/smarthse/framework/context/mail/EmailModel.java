/**
 * 
 */
package cn.smarthse.framework.context.mail;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.springframework.ui.ModelMap;

import lombok.Data;

/**
 * 《邮件Model定义》
 * 
 * 
 * @Project:  smarthse-common1.0
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-7-25-下午4:30:20
 */
public @Data class EmailModel implements Serializable {
	private static final long serialVersionUID = -5153932154715504982L;

    /**
     * 邮件接收者
     */
    private String [] receivers;

    /**
     * 邮件抄送人
     */
    private String [] cc = new String[]{};

    /**
     * 邮件抄送人
     */
    private String [] bcc = new String[]{};

    /**
     * Email发送的内容
     */
    private String emailContent;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件附件
     */
    private File [] attachFile;
	
    
    /**
     * 模板名称, Freemarker 文件
     */
    private String templateName;
    
    /**
     * 模板数据
     */
    private ModelMap templateModel;
    
    /**
     * 发送时间
     */
    private Date sentDate;
    
    //=================其他set / get==============
    /**
     * 设置客户邮箱列表做为接收人列表
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author JannyShao(邵建义) [ksgameboy@qq.com]
     * @since 2017-7-26-下午2:39:08
     * @param customer
     */
    public void setCustomers(String customer){
    	this.setReceivers(customer.split(","));
    }
    
}
