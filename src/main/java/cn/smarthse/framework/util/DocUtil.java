package cn.smarthse.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * freemarker生成word文档
 */

public class DocUtil {

	private static Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

	/**
	 * 根据模板生成文件
	 * 
	 * @param dataMap
	 *            数据模型
	 * @param templateName
	 *            模板名(templates配置)
	 * @param targetFile
	 *            目标文件
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static File createDoc(Object dataMap, String templateName, String targetDir, String fileName) {
		File f = new File(targetDir + File.separator + fileName);
		Writer w = null;
		try {
			f.getParentFile().mkdirs();
			configuration.setDefaultEncoding("UTF-8");
			// 加载模板项
			configuration.setClassForTemplateLoading(DocUtil.class, "/template/files/");
			// 设置对象包装器
			configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));
			// 设置异常处理器
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			Template template = configuration.getTemplate(templateName, "utf-8");
			if (template == null) {
				throw new RuntimeException("模板不存在!");
			}
			w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			template.process(dataMap, w);
		} catch (IOException e) {
			throw new RuntimeException("IO异常：" + e.getMessage());
		} catch (TemplateException e) {
			throw new RuntimeException("模板异常：" + e.getMessage());
		} finally {
			try {
				if (w != null)
					w.close();
			} catch (IOException e) {
				throw new RuntimeException("IO异常：" + e.getMessage());
			}
		}
		return f;
	}
	/**
     * 根据模板生成文件
     * @param dataMap		数据模型
     * @param templateName	模板名(templates配置)
     * @param targetFile	目标文件
	 * @param servletOutputStream 
     * @return
     */
    public void generaDoc(Map<?, ?> dataMap, String templateName, String targetFile, ServletOutputStream servletOutputStream) {  
        File f = new File(targetFile);  
        try{
        	if(!f.getParentFile().exists()){
        		//补全目录
            	f.getParentFile().mkdirs();
            	//f.mkdirs();
        	}   
        	configuration.setDefaultEncoding("UTF-8");
	        //加载模板项
        	configuration.setClassForTemplateLoading(DocUtil.class, "/template/files/");
	        //设置对象包装器
	        configuration.setObjectWrapper(new DefaultObjectWrapper());
	        //设置异常处理器
	        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
	        //定义Template对象
	        Template t = configuration.getTemplate(templateName,"UTF-8");
	        if(t==null){
	        	 throw new RuntimeException("模板不存在!"); 
	        }        
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
            //Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8"); 
	        // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
            t.process(dataMap, w);  
            w.close();
            BufferedInputStream bis = null;  
	        BufferedOutputStream bos = null;  
	        bis = new BufferedInputStream(new FileInputStream(f));  
	        bos = new BufferedOutputStream(servletOutputStream);  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close();
	        f.delete();
        }catch (Exception ex) {  
            ex.printStackTrace();  
            throw new RuntimeException(ex);  
        }          
       
    }

}
