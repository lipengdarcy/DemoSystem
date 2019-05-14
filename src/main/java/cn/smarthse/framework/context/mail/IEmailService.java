/**
 * 
 */
package cn.smarthse.framework.context.mail;

import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.ui.ModelMap;

/**
 * 《邮件服务Service接口定义》
 * 
 * 
 * @Project:  smarthse-common1.0
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-7-25-下午4:32:36
 */
public interface IEmailService {

	/**
	 * 发送文本Email消息
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-26-上午9:15:12
	 * @param emailModel
	 * @param date
	 */
    public void sendEmail(EmailModel emailModel, Date date) throws MessagingException;

	  /**
	   * 根据Freemaker模板生成HTML发送邮件
	   * 
	   * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	   * @author JannyShao(邵建义) [ksgameboy@qq.com]
	   * @since 2017-7-26-上午9:11:10
	   * @param emailModel			EmailModel信息(收件人,附件等)
	   * @param templateName		内容模板(NULL时发送emailModel.emailContent)
	   * @param model					模板数据
	   * @param date						发送时间
	   * @throws MessagingException
	   */
    public void sendEmail(EmailModel emailModel , String templateName,  ModelMap model, Date date) throws MessagingException;

    
    /**
	   * 根据Freemaker模板生成HTML发送邮件
	   * 
	   * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	   * @author JannyShao(邵建义) [ksgameboy@qq.com]
	   * @since 2017-7-26-上午9:11:10
	   * @param emailModel			EmailModel信息(收件人,附件等)
	   * @param templateName		内容模板(NULL时发送emailModel.emailContent)
	   * @param model					模板数据
	   * @param date						发送时间
	   * @throws MessagingException
	   */
  public void sendEmailByOnce(EmailModel emailModel , String templateName,  ModelMap model, Date date) throws MessagingException;

}
