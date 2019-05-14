package cn.smarthse.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.smarthse.framework.security.MD5;

/**
 * excel工具类
 *
 */
public class ExcelUtils {

	private ExcelUtils() {
	};

	private static class LazyHolder {
		private static final ExcelUtils INSTANCE = new ExcelUtils();
	}

	public static final ExcelUtils getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static void main(String[] args) {
		ExcelUtils util = new ExcelUtils();
		List<Integer> cellNum = new ArrayList<Integer>();
		cellNum.add(5);
		cellNum.add(9);
		cellNum.add(10);
		cellNum.add(9);
		cellNum.add(8);

	}

	/**
	 * 
	 * 
	 * @Comments: <垂直合并excel 单元格（依据文本相同即视为合并）>
	 * @author zhoulj(周利军) [1217102780@qq.com]
	 * @since 2018年11月15日-下午2:09:20
	 * @param book
	 * @param sheetAt
	 *            第几个sheet
	 * @param startIndex
	 *            从第几行 开始计算（如第一行是title 那么开始索引就为1）
	 * @param Col
	 *            计算那列数据
	 */
	public static void verticalMerged(org.apache.poi.ss.usermodel.Workbook book, int sheetAt, int startIndex, int Col) {
		Sheet sheet = book.getSheetAt(sheetAt);
		// sheet.getColumnStyle(column)
		// 总数据行数
		int totalRow = getRowNum(sheet, Col);
		String beforeText = "";
		int startRow = 0;
		for (int i = startIndex; i < totalRow; i++) {
			Row row = sheet.getRow(i);
			if (row != null && row.getCell(Col) != null) {
				String text = row.getCell(Col).getStringCellValue();
				if ("".equals(beforeText)) {
					beforeText = text;
					startRow = i;
					continue;
				} else {
					if (!beforeText.equals(text)) {
						if (startRow != i) {
							CellRangeAddress region = new CellRangeAddress(startRow, i - 1, Col, Col);
							sheet.addMergedRegion(region);
						}
						startRow = i;
						beforeText = text;
					} else {
						// 处理最后一行需要合并的情况
						if (i == totalRow - 1 && startRow != i) {
							CellRangeAddress region = new CellRangeAddress(startRow, i, Col, Col);
							sheet.addMergedRegion(region);
						}
					}
				}
			}
		}
	}

	private static int getRowNum(Sheet sheet, int Col) {
		Iterator<Row> iRow = sheet.iterator();
		int i = 0;
		while (iRow.hasNext()) {
			Row row = iRow.next();
			if (row != null && row.getCell(Col) != null) {
				i += 1;
			}
		}
		return i;
	}

	/**
	 * @param filePath
	 * @param cellNum
	 * @param col
	 *            第几行开始有数据
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	public List<List<List<String>>> getContent(String filePath, List<Integer> cellNum, Integer col)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {

		InputStream fis = new FileInputStream(filePath);

		// 创建Excel工作薄
		HSSFWorkbook hwb = new HSSFWorkbook(fis);
		// 得到第一个工作表
		HSSFSheet sheet = hwb.getSheetAt(0);
		HSSFRow row = null;
		List<List<List<String>>> content = new ArrayList<List<List<String>>>();
		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
			sheet = hwb.getSheetAt(i);
			List<List<String>> sheetContent = new ArrayList<List<String>>();
			// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
			for (int j = (col >= 1 ? col : 1); j < sheet.getPhysicalNumberOfRows(); j++) {
				// 数据从第二行开始，第一行为表头
				row = sheet.getRow(j);
				List<String> rowContent = new ArrayList<String>();
				boolean isAllEmpty = true;
				for (int k = 0; k < cellNum.get(i); k++) {
					HSSFCell cell = row.getCell(k);
					if (cell != null) {
						cell.setCellType(CellType.STRING);
					}
					String c = cell == null ? "" : row.getCell(k).getStringCellValue();
					if (c == null) {
						c = "";
					}
					if (isAllEmpty && StringUtils.isEmpty(c)) {

					} else {
						isAllEmpty = false;
					}
					rowContent.add(c);
				}
				if (!isAllEmpty) {
					sheetContent.add(rowContent);
				}
			}
			content.add(sheetContent);
		}
		return content;
	}

	// 判断从Excel文件中解析出来数据的格式
	private String getCellValue(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		String value = null;
		// 简单的查检列类型
		switch (cell.getCellType()) {
		case STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case NUMERIC:// 数字
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case BLANK:
			value = "";
			break;
		case FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case BOOLEAN:// boolean型值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		return value;
	}

	// 判断从Excel文件中解析出来数据的格式
	private String getCellValue1(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		String value = null;
		// 简单的查检列类型
		switch (cell.getCellType()) {
		case STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case NUMERIC:// 数字 或日期
			value = parseExcel(cell);
			break;
		case BLANK:
			value = "";
			break;
		case FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case BOOLEAN:// boolean型值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		return value;
	}

	/**
	 * 获取date数据
	 * 
	 * @param cell
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(Cell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getDateCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			String dateStr = cell.getStringCellValue();
			if (StringUtils.isNotEmpty(dateStr)) {
				Date date = null;
				try {
					date = DateFormat.getDateInstance().parse(dateStr.trim());
				} catch (ParseException e) {

				}
				return date;
			}
		}
		return null;
	}

	private String parseExcel(HSSFCell cell) {

		String result = "";
		if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
			SimpleDateFormat sdf = null;
			if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
				sdf = new SimpleDateFormat("HH:mm");
			} else {// 日期
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			Date date = cell.getDateCellValue();
			result = sdf.format(date);
		} else if (cell.getCellStyle().getDataFormat() == 58) {
			// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			double value = cell.getNumericCellValue();
			Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
			result = sdf.format(date);
		} else {
			double value = cell.getNumericCellValue();
			CellStyle style = cell.getCellStyle();
			DecimalFormat format = new DecimalFormat();
			String temp = style.getDataFormatString();
			// 单元格设置成常规
			if (temp.equals("General")) {
				format.applyPattern("#");
			}
			result = format.format(value);
		}
		return result;
	}

	/**
	 * 
	 * @Comments: <根据sheet名字获取相应的excel导入模板>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2017年7月20日-上午11:55:04
	 * @param filePath
	 *            导入模板路径
	 * @param sheetNames
	 *            导入模板Excel里需要的sheet的名字集合
	 * @param out
	 *            输出流
	 * @throws Exception
	 */
	public void downExcelBySheetNames(String filePath, List<String> sheetNames, OutputStream out) throws Exception {

		List<String> noNeedSheetNames = new ArrayList<String>();

		InputStream fis = new FileInputStream(filePath);
		// 创建Excel工作薄
		HSSFWorkbook hwb = new HSSFWorkbook(fis);

		for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
			noNeedSheetNames.add(hwb.getSheetAt(i).getSheetName());
		}
		if (sheetNames != null && sheetNames.size() > 0) {
			for (String name : sheetNames) {
				noNeedSheetNames.remove(name);
			}
		}

		if (noNeedSheetNames != null && noNeedSheetNames.size() > 0) {
			for (String name : noNeedSheetNames) {
				hwb.removeSheetAt(hwb.getSheetIndex(name));
			}

		}
		hwb.write(out);
		hwb.close();
	}

	/**
	 * 读取日期型Cell单元格数据
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月6日-下午1:27:51
	 * @param cell
	 *            单位格
	 * @param errorDate
	 *            单位格里有值时,非日期数据,则返回errorDate
	 * @return 单位格为null时,返回null
	 */
	public static String getCellDate(Cell cell, String errorDate) {
		if (isCellNotEmpty(cell)) {
			String format = DateUtils.DATE_FORMAT_DEFAULT;
			Date date = getCellValue2Date(cell);
			if (date != null) {
				return DateUtils.format(date, format);
			} else {
				return errorDate;
			}
		}

		return null;
	}

	public static Date getCellValue2Date(Cell cell) {
		Date date = null;
		String value = getCell(cell);
		if (DateUtils.checkDate(value)) {
			date = DateUtils.parseDate(value);
		} else if (NumberUtil.isNumber(value)) {
			double dateValue = NumberUtil.toDouble(value);
			date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(dateValue);
		}

		return date;
	}

	/**
	 * 读取单元格字符串信息
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月6日-下午1:42:50
	 * @param cell
	 * @return
	 */
	public static String getCell(Cell cell) {
		String data = null;
		if (cell != null) {
			cell.setCellType(CellType.STRING);
			data = StringUtils.trim(cell.toString());
		}

		return data;
	}

	/**
	 * 单位格是否为空
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月6日-下午2:35:02
	 * @param cell
	 * @return
	 */
	public static boolean isCellNotEmpty(Cell cell) {
		boolean isNotEmpty = false;
		if (cell != null) {
			cell.setCellType(CellType.STRING);
			String data = StringUtils.trim(cell.toString());

			isNotEmpty = StringUtils.isNotEmpty(data);
		}

		return isNotEmpty;
	}

	/**
	 * 
	 * @Function: ExcelUtil.java
	 * @Description: 校验excel数据是否重复， 若重复，返回重复行的所有数据，末尾加上行号(例：xx,xxx,xxx,5)
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: cjy
	 * @date: 2019年3月8日 上午10:14:03
	 *
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------* 2019年3月8日
	 *        cjy v1.0.0 修改原因
	 */
	public static List<String> checkRepeat(Workbook wb, int... colnum) throws Exception {
		if (wb == null) {
			throw new RuntimeException("excel读取失败!");
		}

		List<String> rapeatList = new ArrayList<>();
		// 重复的Map
		Map<String, Boolean> repeatMap = new HashMap<>();

		// 获取第一个sheet
		Sheet sheet = wb.getSheetAt(0);
		// 获取最大行数
		int rownum = sheet.getPhysicalNumberOfRows();
		// 获取第一行
		Row row = sheet.getRow(0);
		// 获取最大列数
		int colnumtotal = row.getPhysicalNumberOfCells();
		for (int rowIndex = 1; rowIndex < rownum; rowIndex++) {
			row = sheet.getRow(rowIndex);
			Boolean isAdd = null;
			String value = null;
			if (row != null) {
				String checkVal = "";
				String showVal = "";
				for (int colIndex = 0; colIndex < colnumtotal; colIndex++) {
					Cell cell = row.getCell(colIndex);

					value = cell == null ? null : getCellValue(cell);
					boolean exsists = isExsists(colIndex, colnum);
					if (exsists) {
						if (StringUtils.isNotEmpty(value)) {
							checkVal += (value.replace(" ", ""));
							showVal += (value + ",");
						}
					} else
						showVal += (value + ",");// 保证该行的每列元素都能返回 即便它是空的

				}
				if (StringUtils.isNotEmpty(checkVal)) {
					String key = MD5.MD5Encode(checkVal);
					isAdd = repeatMap.get(key) != null && repeatMap.get(key);
					if (isAdd) {
						// 说明该行数据已存在
						rapeatList.add(showVal + rowIndex);
					} else
						repeatMap.put(key, true);
				}
			}

		}

		return rapeatList;
	}

	// 验证某列是否在检查重复的目标列中
	private static boolean isExsists(int colIndex, int... colnum) {
		for (int col : colnum) {
			if (colIndex == col)
				return true;
		}

		return false;
	}

	private static String getCellValue(Cell cell) throws Exception {
		if (cell.getCellType() == CellType.NUMERIC) {
			cell.setCellType(CellType.STRING);
			return cell.getStringCellValue().trim();
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		}
		return "";
	}
}
