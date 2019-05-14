package cn.smarthse.business.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import cn.smarthse.framework.model.ResponseData;

/**
 * excel文件导入公共部分
 */

public class ExcelImportService {

	/**
	 * 日志记录
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 判断列格式是否正确，若格式正确，返回列值的字符形式；若格式错误，则抛出异常
	 * 
	 * @param Cell
	 *            excel列对象
	 */
	protected String getCellValue(Cell cell) throws Exception {
		if (cell.getCellType() == CellType.NUMERIC) {
			cell.setCellType(CellType.STRING);
			return cell.getStringCellValue().trim();
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		}
		return "";
	}

	/**
	 * excel日期转化为java日期
	 * 
	 * @param days
	 *            excel日期的数值
	 * @return
	 */
	protected Date getExcelDate(int days) {
		Calendar c3 = Calendar.getInstance();
		c3.set(1900, 0, -1);
		c3.add(Calendar.DATE, days);
		Date date = c3.getTime();
		return date;
	}

	/**
	 * excel列名
	 * 
	 * @param index
	 *            excel列序号
	 * @return excel列名，如“A，AB”
	 */
	protected String getExcelColName(int index) {
		if (index <= 26)
			return String.valueOf((char) ('A' + index - 1));
		int a = index / 26;
		int b = index % 26;

		return String.valueOf((char) ('A' + a - 1)) + String.valueOf((char) ('A' + b - 1));

	}

	/**
	 * 判断导入数据必填项是否为空，获取对应的提示信息
	 * 
	 * @param Cell
	 *            excel列对象
	 * @param rowIndex
	 *            第几行
	 * @param colIndex
	 *            第几列
	 */
	protected ResponseData<String> getStaffBlankInfo(Cell cell, int rowIndex, int colIndex) {
		ResponseData<String> data = new ResponseData<String>();
		// 非空数据直接返回，不判断
		if (!(cell == null || cell.getCellType() == CellType.BLANK)) {
			return data;
		}
		switch (colIndex) {
		case 1:
			data.setCode(-1);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 是否外协为必填项不能为空");
			return data;
		case 4:
			data.setCode(-4);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 姓名为必填项不能为空");
			return data;
		case 5:
			data.setCode(-5);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 部门/车间为必填项不能为空");
			return data;
		case 6:
			data.setCode(-6);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 岗位/工种为必填项不能为空");
			return data;
		case 8:
			data.setCode(-8);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 职称等级为必填项不能为空");
			return data;
		case 10:
			data.setCode(-10);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 性别为必填项不能为空");
			return data;
		case 11:
			data.setCode(-11);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 手机号码为必填项不能为空");
			return data;
		case 12:
			data.setCode(-9);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 出生年月为必填项不能为空");
			return data;
		case 13:
			data.setCode(-13);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 证件类型不能为空");
			return data;
		case 14:
			data.setCode(-14);
			data.setMessage("行号：" + rowIndex + ", 列号：" + getExcelColName(colIndex) + ", 证件号码不能为空");
			return data;
		default:
			break;
		}

		return data;
	}

	/**
	 * 判断导入数据行是否为空
	 *
	 * @param row
	 *            excel行对象
	 */
	protected Boolean isRowBlank(Row row) {
		if ((row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK)
				&& (row.getCell(3) == null || row.getCell(3).getCellType() == CellType.BLANK)
				&& (row.getCell(4) == null || row.getCell(4).getCellType() == CellType.BLANK)
				&& (row.getCell(5) == null || row.getCell(5).getCellType() == CellType.BLANK)
				&& (row.getCell(7) == null || row.getCell(7).getCellType() == CellType.BLANK)
				&& (row.getCell(9) == null || row.getCell(9).getCellType() == CellType.BLANK)
				&& (row.getCell(10) == null || row.getCell(10).getCellType() == CellType.BLANK)
				&& (row.getCell(11) == null || row.getCell(11).getCellType() == CellType.BLANK)
				&& (row.getCell(12) == null || row.getCell(12).getCellType() == CellType.BLANK)
				&& (row.getCell(13) == null || row.getCell(13).getCellType() == CellType.BLANK))
			return true;
		return false;
	}

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param days
	 *            excel日期的数值
	 * @return
	 */
	protected int getAge(Date birthDay) throws Exception {
		if (birthDay == null)
			return 0;
		// 获取当前系统时间
		Calendar cal = Calendar.getInstance();
		// 如果出生日期大于当前时间，则抛出异常
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		// 取出系统当前时间的年、月、日部分
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		// 将日期设置为出生日期
		cal.setTime(birthDay);
		// 取出出生日期的年、月、日部分
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		// 当前年份与出生年份相减，初步计算年龄
		int age = yearNow - yearBirth;
		// 当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
		if (monthNow <= monthBirth) {
			// 如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

}
