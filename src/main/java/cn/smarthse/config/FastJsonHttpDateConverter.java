package cn.smarthse.config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * fastjson 日期参数转换
 */
//@Configuration
public class FastJsonHttpDateConverter extends FastJsonHttpMessageConverter {

	private static SerializeConfig mapping = new SerializeConfig();

	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm"));
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		String text = JSON.toJSONString(obj, mapping, this.getFeatures());
		byte[] bytes = text.getBytes(this.getCharset());
		out.write(bytes);
	}

}