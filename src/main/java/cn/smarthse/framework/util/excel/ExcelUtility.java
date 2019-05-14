package cn.smarthse.framework.util.excel;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smarthse.framework.Constant;

public class ExcelUtility {
	
	public static void init(HttpServletRequest request, HttpServletResponse response){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = "企业建筑物信息-";
		fileName += dateFormat.format(new Date());
		fileName += ".xls";

		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String((fileName).getBytes("gbk"), "iso8859-1") + ".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Integer cid = (Integer) request.getSession().getAttribute(Constant.ACCOUNT_COMPANYID);
	}

}
