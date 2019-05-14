/**
 * 
 */
package cn.smarthse.framework.context.mail;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cn.smarthse.framework.util.StringUtils;
import freemarker.template.Configuration;

/**
 * 《邮箱发送器》
 * 
 * 
 * @Project:  smarthse-common1.0
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2017-7-25-下午4:39:51
 */
public class FreemarkerEmailServiceImpl implements IEmailService {
	//日志记录
	private static Logger logger = LogManager.getLogger(FreemarkerEmailServiceImpl.class);
	
	//邮箱发送
	@Autowired(required=false)
	private JavaMailSender emailTemplate;
	
	//freemaker模板配置
	@Autowired(required=false)
	private Configuration freemarkerConfiguration;
	
	//邮箱发送人
	@Value("${email.username}")
    private String email_username;

	/**
	 * 邮件异步线程池
	 */
	@Autowired(required=false)
    private TaskExecutor taskExecutor;
	
	/* (non-Javadoc)
	 * @see cn.smarthse.common.framework.mail.IEmailService#sendEmailMessageOfSimpleText(cn.smarthse.common.framework.mail.EmailModel, java.util.Date)
	 */
	@Override
	public void sendEmail(EmailModel emailModel, Date date) throws MessagingException{
		this.sendEmail(emailModel, null, null, date);
	}
	
	/* (non-Javadoc)
	 * @see cn.smarthse.common.framework.mail.IEmailService#sendEmail(cn.smarthse.common.framework.mail.EmailModel, java.lang.String, org.springframework.ui.ModelMap, java.util.Date)
	 */
	@Override
	public void sendEmail(EmailModel emailModel, String templateName,
			ModelMap model, Date date) throws MessagingException {
		MimeMessage message = emailTemplate.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
       
        helper.setFrom(email_username);
        helper.setValidateAddresses(true);
        if(StringUtils.isNotBlank(templateName)){
        	//根据模板发送内容
        	 helper.setText(geFreeMarkerTemplateContent(model,templateName), true);
        }else if(StringUtils.isNotBlank(emailModel.getEmailContent()))
        {
        	//根据邮件文本内容发送
            helper.setText(emailModel.getEmailContent(), true);
        }
        helper.setSubject(emailModel.getSubject());
        helper.setCc(emailModel.getCc());
        helper.setTo(emailModel.getReceivers());
        helper.setBcc(emailModel.getBcc());
        if(null == date)
        {
            date = new Date();
        }
        helper.setSentDate(date);
        
        //添加附件
        if(emailModel.getAttachFile()!=null){
        	 for(File file : emailModel.getAttachFile())
             {
                 FileSystemResource fileSystemResource = new FileSystemResource(file);
                 helper.addAttachment(file.getName(), fileSystemResource);
             }
        }
        
        addSendMailTask(message);
	}

	/**
     * @desc 使用多线程发送邮件
     * @author jianzh5
     * @date 2017/4/1 11:41
     * @param message MimeMessage邮件封装类
     */
    private void addSendMailTask(final MimeMessage message){
    	if(taskExecutor!=null){
    		try{
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                    	emailTemplate.send(message);
                    }
                });
            }catch (Exception e){
                logger.error("邮件发送异常,邮件详细信息为{}",e.getMessage());
            }
    	}else{
    		emailTemplate.send(message);
    	}
        

    }

	
	/**
	 * 根据Freemarker模板生成内容
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-26-上午9:07:45
	 * @param model
	 * @param templateName
	 * @return
	 */
	public String geFreeMarkerTemplateContent(Map<String, Object> model, String templateName)
    {
        StringBuffer content = new StringBuffer();
        try
        {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                freemarkerConfiguration.getTemplate(templateName), model));
            return content.toString();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
        }
        return "";
    }

	/* (non-Javadoc)
	 * @see cn.smarthse.modules.platform.core.framework.mail.IEmailService#sendEmailByTask(cn.smarthse.modules.platform.core.framework.mail.EmailModel, java.lang.String, org.springframework.ui.ModelMap, java.util.Date)
	 */
	@Override
	public void sendEmailByOnce(EmailModel emailModel, String templateName, ModelMap model, Date date)
			throws MessagingException {
		MimeMessage message = emailTemplate.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
       
        helper.setFrom(email_username);
        helper.setValidateAddresses(true);
        if(StringUtils.isNotBlank(templateName)){
        	//根据模板发送内容
        	 helper.setText(geFreeMarkerTemplateContent(model,templateName), true);
        }else if(StringUtils.isNotBlank(emailModel.getEmailContent()))
        {
        	//根据邮件文本内容发送
            helper.setText(emailModel.getEmailContent(), true);
        }
        helper.setSubject(emailModel.getSubject());
        helper.setCc(emailModel.getCc());
        helper.setTo(emailModel.getReceivers());
        helper.setBcc(emailModel.getBcc());
        if(null == date)
        {
            date = new Date();
        }
        helper.setSentDate(date);
        
        //添加附件
        if(emailModel.getAttachFile()!=null){
        	 for(File file : emailModel.getAttachFile())
             {
                 FileSystemResource fileSystemResource = new FileSystemResource(file);
                 helper.addAttachment(file.getName(), fileSystemResource);
             }
        }
        
        emailTemplate.send(message);
		
	}


}
