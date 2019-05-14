package cn.smarthse.framework.context;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import org.springframework.util.StringUtils;

import cn.smarthse.framework.util.DateUtils;


/**
 * 《各种日期格式转换》
 */
public class CustomDateFormatEditor extends PropertyEditorSupport {
	

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.isEmpty(text)) {
			setValue(null);
		}
		else {
			setValue(DateUtils.parseDate(text));
		}
	}
	
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? DateUtils.formatDateTime(value) : "");
	}
}
