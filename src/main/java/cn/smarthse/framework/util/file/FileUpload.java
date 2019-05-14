package cn.smarthse.framework.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.smarthse.framework.enumtype.common.file.UploadState;

public class FileUpload {

	// 定义可以上传文件的后缀数组,默认"*"，代表所有
	public static String[] filePostfixs = { "*" };
	public static String[] typeImages = { "gif", "jpeg", "png", "jpg", "tif", "bmp" };
	public static String[] typeOthers = { "html", "htm", "doc", "xls", "txt", "zip", "rar", "pdf", "cll" };

	// 上传文件的最大长度
	// public static long maxFileSize = 1024 * 1024 * 1024 * 2L;// 2G
	public static long maxFileSize = 1024 * 1024 * 20L;// 20M
	// 一次读取多少字节
	public static int bufferSize = 1024 * 8;

	private final static void init() {
		if (bufferSize > Integer.MAX_VALUE) {
			bufferSize = 1024 * 8;
		} else if (bufferSize < 8) {
			bufferSize = 8;
		}
		if (maxFileSize < 1) {
			maxFileSize = 1024 * 1024 * 1024 * 2L;
		} else if (maxFileSize > Long.MAX_VALUE) {
			maxFileSize = 1024 * 1024 * 1024 * 2L;
		}
	}

	/**
	 * <b>function:</b>通过输入流参数上传文件
	 * 
	 * @param uploadFileName
	 *            文件名称
	 * @param savePath
	 *            保存路径
	 * @param InputStream
	 *            上传的文件的输入流
	 * @return 是否上传成功
	 * @throws Exception
	 */
	public static UploadState upload4Stream(String fileName, String path, InputStream is) throws Exception {
		init();
		UploadState state = UploadState.UPLOAD_FAILURE;
		FileOutputStream fos = null;

		try {
			mkDir(path);
			fos = new FileOutputStream(path + fileName);

			byte[] buffer = new byte[bufferSize];
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			state = UploadState.UPLOAD_SUCCSSS;
		} catch (FileNotFoundException e) {
			state = UploadState.UPLOAD_NOTFOUND;
			throw e;
		} catch (IOException e) {
			state = UploadState.UPLOAD_FAILURE;
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
		return state;
	}

	/**
	 * <b>function:</b>上传文件
	 * 
	 * @param uploadFileName
	 *            文件名称
	 * @param savePath
	 *            保存路径
	 * @param uploadFile
	 *            上传的文件
	 * @return 是否上传成功
	 * @throws Exception
	 */
	public static UploadState upload4Stream(String fileName, String path, File file) throws Exception {
		init();
		UploadState state = UploadState.UPLOAD_FAILURE;
		FileInputStream fis = null;
		try {
			long size = file.length();
			if (size <= 0) {
				state = UploadState.UPLOAD_ZEROSIZE;
			} else {
				if (size <= maxFileSize) {
					fis = new FileInputStream(file);
					state = upload4Stream(fileName, path, fis);
				} else {
					state = UploadState.UPLOAD_OVERSIZE;
				}
			}
		} catch (FileNotFoundException e) {
			state = UploadState.UPLOAD_NOTFOUND;
			throw e;
		} catch (IOException e) {
			state = UploadState.UPLOAD_FAILURE;
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return state;
	}

	/**
	 * <b>function:</b>通过数组进行验证文件类型上传
	 * 
	 * @author zhaowei
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            文件路径
	 * @param file
	 *            文件
	 * @param allowTypes
	 *            文件后缀、类型数组
	 * @return 返回是否上传成功
	 * @throws Exception
	 */
	public static UploadState upload4Stream(String fileName, String path, File file, String[] allowTypes)
			throws Exception {
		UploadState state = UploadState.UPLOAD_FAILURE;
		if (validTypeByName(fileName, allowTypes)) {
			state = upload4Stream(fileName, path, file);
		} else {
			state = UploadState.UPLOAD_TYPE_ERROR;
		}
		return state;
	}

	/**
	 * <b>function:</b>通过数组进行验证文件类型上传
	 * 
	 * @author zhaowei
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            文件路径
	 * @param InputStream
	 *            文件输入流
	 * @param allowTypes
	 *            文件后缀、类型数组
	 * @return 返回是否上传成功
	 * @throws Exception
	 */
	public static UploadState upload4Stream(String fileName, String path, InputStream fs, String[] allowTypes)
			throws Exception {
		UploadState state = UploadState.UPLOAD_FAILURE;
		if (validTypeByName(fileName, allowTypes)) {
			state = upload4Stream(fileName, path, fs);
		} else {
			state = UploadState.UPLOAD_TYPE_ERROR;
		}
		return state;
	}

	/**
	 * <b>function:</b> 利用FileUtils上传文件；其中maxFileSize是限制上传文件的大小
	 * 
	 * @author zhaowei
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            保存路径
	 * @param file
	 *            文件
	 * @return 是否上传成功
	 * @throws Exception
	 */
	public static boolean upload4CopyFile(String fileName, String path, File file) throws Exception {
		init();
		boolean success = false;
		if (file.length() <= maxFileSize) {
			mkDir(path);
			File destFile = new File(path, fileName);
			FileUtils.copyFile(file, destFile);
			success = true;
		}
		return success;
	}

	/**
	 * <b>function:</b>上传指定文件类型的文件
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名
	 * @param path
	 *            路径
	 * @param file
	 *            文件
	 * @param allowTypes
	 *            类型、后缀数组
	 * @return 成功上传的文件名
	 * @throws Exception
	 */
	public static boolean upload4CopyFile(String fileName, String path, File file, String[] allowTypes)
			throws Exception {
		boolean success = false;
		if (validTypeByName(fileName, allowTypes)) {
			success = upload4CopyFile(fileName, path, file);
		}
		return success;
	}

	/**
	 * <b>function:</b> 根据文件名和类型数组验证文件类型是否合法，flag是否忽略大小写
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名
	 * @param allowTypes
	 *            类型数组
	 * @param flag
	 *            是否获得大小写
	 * @return 是否验证通过
	 */
	public static boolean validTypeByName(String fileName, String[] allowTypes, boolean flag) {
		String suffix = getType(fileName);
		boolean valid = false;
		if (allowTypes.length > 0 && "*".equals(allowTypes[0])) {
			valid = true;
		} else {
			for (String type : allowTypes) {
				if (flag) {// 不区分大小写后缀
					if (suffix != null && suffix.equalsIgnoreCase(type)) {
						valid = true;
						break;
					}
				} else {// 严格区分大小写
					if (suffix != null && suffix.equals(type)) {
						valid = true;
						break;
					}
				}
			}
		}
		return valid;
	}

	/**
	 * <b>function:</b>根据文件名称和类型数组验证文件类型是否合法
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名
	 * @param allowTypes
	 *            文件类型数组
	 * @return 是否合法
	 */
	public static boolean validTypeByName(String fileName, String[] allowTypes) {
		return validTypeByName(fileName, allowTypes, true);
	}

	/**
	 * <b>function:</b> 根据后缀和类型数组验证文件类型是否合法，flag是否区分后缀大小写，true严格大小写
	 * 
	 * @author zhaowei
	 * 
	 * @param suffix
	 *            后缀名
	 * @param allowTypes
	 *            文件类型数组
	 * @param flag
	 *            是否区分大小写
	 * @return 是否合法
	 */
	public static boolean validTypeByPostfix(String suffix, String[] allowTypes, boolean flag) {
		boolean valid = false;
		if (allowTypes.length > 0 && "*".equals(allowTypes[0])) {
			valid = true;
		} else {
			for (String type : allowTypes) {
				if (flag) {// 不区分大小写后缀
					if (suffix != null && suffix.equalsIgnoreCase(type)) {
						valid = true;
						break;
					}
				} else {// 严格区分大小写
					if (suffix != null && suffix.equals(type)) {
						valid = true;
						break;
					}
				}
			}
		}
		return valid;
	}

	/**
	 * <b>function:</b>根据文件后缀名和类型数组，验证文件类型是否合法
	 * 
	 * @author zhaowei
	 * 
	 * @param suffix
	 *            后缀名
	 * @param allowTypes
	 *            类型数组
	 * @return 是否合法
	 */
	public static boolean validTypeByPostfix(String suffix, String[] allowTypes) {
		return validTypeByPostfix(suffix, allowTypes, true);
	}

	/**
	 * <b>function:</b>验证当前后缀、文件类型是否是图片类型 typeImages 可以设置图片类型
	 * 
	 * @author zhaowei
	 * 
	 * @param suffix
	 *            验证文件的后缀
	 * @return 是否合法
	 */
	public static boolean validTypeByPostfix4Images(String suffix) {
		return validTypeByPostfix(suffix, typeImages);
	}

	/**
	 * <b>function:</b>验证当前后缀、文件类型是否是非图片类型（常用办公文件类型） typeOthers 可以设置文件类型
	 * 
	 * @author zhaowei
	 * 
	 * @param suffix
	 *            验证文件的后缀
	 * @return 是否合法
	 */
	public static boolean validTypeByPostfix4Others(String suffix) {
		return validTypeByPostfix(suffix, typeOthers);
	}

	/**
	 * <b>function:</b>验证当前文件名、文件类型是否是图片类型 typeImages 可以设置图片类型
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            验证文件的名称
	 * @return 是否合法
	 */
	public static boolean validTypeByName4Images(String fileName) {
		return validTypeByName(fileName, typeImages);
	}

	/**
	 * <b>function:</b>验证当前文件名称、文件类型是否是非图片类型（常用办公文件类型） typeOthers 可以设置文件类型
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            验证文件的名称
	 * @return 是否合法
	 */
	public static boolean validTypeByName4Others(String fileName) {
		return validTypeByName(fileName, typeOthers);
	}

	/**
	 * <b>function:</b>传递一个路径和文件名称，删除该文件
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            路径
	 * @return 是否删除成功
	 */
	public static boolean removeFile(String fileName, String path) {
		boolean flag = false;
		if (isFileExist(fileName, path)) {
			File file = new File(path + fileName);
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * <b>function:</b>删除当前文件
	 * 
	 * @author zhaowei
	 * 
	 * @param file
	 *            要删除的文件
	 * @return 是否删除成功
	 */
	public static boolean removeFile(File file) {
		boolean flag = false;
		if (file != null && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * <b>function:</b>删除某个文件
	 * 
	 * @author zhaowei
	 * 
	 * @param path
	 *            传递该文件路径
	 * @return 删除是否成功
	 */
	public static boolean removeFile(String path) {
		return removeFile(new File(path));
	}

	/**
	 * <b>function:</b>删除当前文件下面所有文件
	 * 
	 * @author zhaowei
	 * 
	 * @param file
	 *            File 要删除的文件夹下面文件的文件对象
	 * @return 是否删除成功，如果有一个文件删除失败，将返回false
	 */
	public static boolean removeFile4Dir(File file) {
		boolean flag = false;
		if (file != null && file.exists() && file.isDirectory()) {
			File[] allFile = file.listFiles();
			for (File f : allFile) {
				flag = f.delete();
				if (!flag) {
					System.err.println("删除文件" + f.getAbsolutePath() + "出错了！");
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * <b>function:</b>删除当前目录下所有文件
	 * 
	 * @author zhaowei
	 * 
	 * @param path
	 *            目录、路径
	 * @return 是否成功
	 */
	public static boolean removeFile4Dir(String path) {
		return removeFile4Dir(new File(path));
	}

	/**
	 * <b>function:</b>删除某个文件夹下的所有文件（除目录），包含子文件夹的文件
	 * 
	 * @author zhaowei
	 * 
	 * @param file
	 *            即将删除文件夹对象
	 * @return 是否删除成功
	 */
	public static boolean removeAllFile4Dir(File file) {
		boolean flag = false;
		if (file != null && file.exists() && file.isDirectory()) {
			File[] allFile = file.listFiles();
			for (File f : allFile) {
				if (!f.isDirectory()) {
					flag = f.delete();
				} else {
					flag = removeAllFile4Dir(f);
				}
				if (!flag) {
					System.err.println("删除文件" + f.getAbsolutePath() + "出错了！");
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * <b>function:</b>删除某个目录下所有文件（不包含文件夹，包含文件夹下的文件）
	 * 
	 * @author zhaowei
	 * 
	 * @param path
	 * @return
	 */
	public static boolean removeAllFile4Dir(String path) {
		return removeAllFile4Dir(new File(path));
	}

	/**
	 * <b>function:</b> 传入一个文件名，得到这个文件名称的后缀
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名
	 * @return 后缀名
	 */
	public static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			String suffix = fileName.substring(index);// 后缀
			return suffix;
		} else {
			return null;
		}
	}

	/**
	 * <b>function:</b>和文件后缀一样，不同的是没有“.”
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static String getType(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			String suffix = fileName.substring(index + 1);// 后缀
			return suffix;
		} else {
			return null;
		}
	}

	/**
	 * <b>function:</b> 传递一个文件名称和一个新名称，组合成一个新的带后缀文件名 当传递的文件名没有后缀，会添加默认的后缀
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @param newName
	 *            新文件名称
	 * @param nullSuffix
	 *            为没有后缀的文件所添加的后缀;eg:txt
	 * @return String 文件名称
	 */
	public static String getNewFileName(String fileName, String newName, String nullSuffix) {
		String suffix = getSuffix(fileName);
		if (suffix != null) {
			newName += suffix;
		} else {
			newName = newName.concat(".").concat(nullSuffix);
		}
		return newName;
	}

	/**
	 * <b>function:</b> 利用uuid产生一个随机的name
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            带后缀的文件名称
	 * @return String 随机生成的name
	 */
	public static String getRandomName(String fileName) {
		String randomName = UUID.randomUUID().toString();
		return getNewFileName(fileName, randomName, "txt");
	}

	/**
	 * <b>function:</b> 利用uuid产生一个随机的name
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            带后缀的文件名称
	 * @param defalutExds
	 *            默认的扩展名
	 * @return String 随机生成的name
	 */
	public static String getRandomName(String fileName, String defalutExds) {
		String randomName = UUID.randomUUID().toString();
		return getNewFileName(fileName, randomName, defalutExds);
	}

	/**
	 * <b>function:</b> 用当前日期、时间和1000以内的随机数组合成的文件名称
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @return 新文件名称
	 */
	public static String getNumberName(String fileName) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddhhmmss");
		int rand = new Random().nextInt(1000);
		String numberName = format.format(new Date()) + rand;
		return getNewFileName(fileName, numberName, "txt");
	}

	/**
	 * <b>function:</b>判断该文件是否存在
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            目录
	 * @return 是否存在
	 */
	public static boolean isFileExist(String fileName, String path) {
		File file = new File(path + fileName);
		return file.exists();
	}

	/**
	 * <b>function:</b>返回可用的文件名
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名
	 * @param path
	 *            路径
	 * @return 可用文件名
	 */
	public static String getBracketFileName(String fileName, String path) {
		return getBracketFileName(fileName, fileName, path, 1);
	}

	/**
	 * <b>function:</b>递归处理文件名称，直到名称不重复（对文件名、目录文件夹都可用） eg: a.txt --> a(1).txt
	 * 文件夹upload--> 文件夹upload(1)
	 * 
	 * @author zhaowei
	 * 
	 * @param fileName
	 *            文件名称
	 * @param path
	 *            文件路径
	 * @param num
	 *            累加数字，种子
	 * @return 返回没有重复的名称
	 */
	public static String getBracketFileName(String fileName, String bracketName, String path, int num) {
		boolean exist = isFileExist(bracketName, path);
		if (exist) {
			int index = fileName.lastIndexOf(".");
			String suffix = "";
			bracketName = fileName;
			if (index != -1) {
				suffix = fileName.substring(index);
				bracketName = fileName.substring(0, index);
			}
			bracketName += "(" + num + ")" + suffix;
			num++;
			bracketName = getBracketFileName(fileName, bracketName, path, num);
		}
		return bracketName;
	}

	/**
	 * <b>function:</b>处理后的系统文件路径
	 * 
	 * @author zhaowei
	 * 
	 * @param path
	 *            文件路径
	 * @return 返回处理后的路径
	 */
	public static String getDoPath(String path) {
		path = path.replace("\\", "/");
		// String lastChar = path.substring(path.length() - 1);
		// if (!"/".equals(lastChar)) {
		// path += "/";
		// }
		return path;
	}

	/**
	 * <b>function:</b> 创建指定的path路径目录
	 * 
	 * @author zhaowei
	 * 
	 * @param path
	 *            目录、路径
	 * @return 是否创建成功
	 * @throws Exception
	 */
	public static boolean mkDir(String path) throws Exception {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			file = null;
		}
		return true;
	}

	/**
	 * 从上传参数中获取file对象
	 */
	public static File getFile(UploadFileParameter up) {
		if (up.getMultipartFile() == null || up.getMultipartFile().getSize() == 0) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) up.getRequest();
			MultipartFile file = multipartRequest.getFile("Filedata");
			if (file == null)
				file = multipartRequest.getFile("file");
			if (file != null && file.getSize() > 0) {
				up.setMultipartFile(file);
			}
		}
		MultipartFile file = up.getMultipartFile();
		if (file instanceof CommonsMultipartFile) {
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			File f = fi.getStoreLocation();
			return f;
		}
		//if(file instanceof StandardMultipartHttpServletRequest.StandardMultipartFile)
		return null;

	}

	/**
	 * 文件上传
	 */
	public static UploadFileParameter upload(UploadFileParameter up) {
		File f = getFile(up);
		// 完整的上传路径
		String realPath = up.getRequest().getSession().getServletContext().getRealPath("/")
				+ UploadFileParameter.FILE_PATH + up.getUploadDir() + UploadFileParameter.FILE_PATH_SEP;
		// 创建目录
		File frealPath = new File(realPath);
		try {
			frealPath.mkdirs();
		} catch (Exception e) {
		}
		up.setOriginalFilename(up.getMultipartFile().getOriginalFilename());// name
		up.setFileName(getRandomName(up.getOriginalFilename()));// filename
		up.setFile(f);// 文件
		up.setRealPath(realPath);
		// 上传文件
		UploadState state = null;
		try {
			state = upload4Stream(up.getFileName(), realPath, up.getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		up.setUploadState(state);
		return up;
	}

	public static void main(String[] args) throws Exception {

		UploadFileParameter up = new UploadFileParameter();
		up.setPathType(2);// 路径
		up.setFileName(getRandomName("2015\\2\\5\\afews.jpg"));
		System.out.println("原图路径：" + up.getPathSource());
		System.out.println("水印图路径：" + up.getPathWater());
		System.out.println("缩略图路径：" + up.getPathThumb());
		System.out.println("时间路径：" + up.getPathDate());
		System.out.println("时间路径带文件名：" + up.getPathDateFileName());
		System.out.println("时间路径带文件名输出：" + up.getUrlDateFileName());
		System.out.println("原图路径带时间路径和文件名：" + getDoPath(up.getPathSource() + up.getPathDateFileName()));
		System.out.println("水印图路径带时间路径和文件名：" + getDoPath(up.getPathWater() + up.getPathDateFileName()));

	}
}