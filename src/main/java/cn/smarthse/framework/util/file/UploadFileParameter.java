package cn.smarthse.framework.util.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.smarthse.framework.enumtype.common.file.UploadFilePathEnum;
import cn.smarthse.framework.enumtype.common.file.UploadState;
import lombok.Data;

/**
 * 文件上传参数
 * */
public @Data class UploadFileParameter {

	public static final String FILE_PATH_SEP = System.getProperty("file.separator");

	// 上传路径upload
	public static final String FILE_PATH =  "upload" + FILE_PATH_SEP;

	/**
	 * 获取上传路径
	 * 
	 * @param f
	 * @return String 返回类型
	 */
	public static String getUploadFilePath(UploadFilePathEnum f) {
		return FILE_PATH + f.getName() + FILE_PATH_SEP;
	}
	
	//员工编号
	private Integer staffId;
	
	//用户id
	private Integer uid;
	
	//企业编号（软件中）
	private Integer cid;
	
	//上传路径
	private String uploadDir;
	
//	private String savePath;
	
	//本地绝对路径地址
	private String realPath;
	
	/**
	 * 文件上传状态
	 * UPLOAD_SUCCSSS(0, "上传文件成功！"), 
	 * UPLOAD_FAILURE(1, "上传文件失败！"), 
	 * UPLOAD_TYPE_ERROR(2, "上传文件类型错误！"), 
	 * UPLOAD_OVERSIZE(3, "上传文件过大！"), 
	 * UPLOAD_ZEROSIZE(4, "上传文件为空！"), 
	 * UPLOAD_NOTFOUND(5, "上传文件路径错误！");
	 */
	private UploadState uploadState;

	private HttpServletRequest request;

	private MultipartFile multipartFile;

	/**
	 * 文件名（生成后，一般是UUID）
	 */
	private String fileName;

	/**
	 * 本地文件名（选取文件时显示）
	 */
	private String originalFilename;
	
	/**
	 * 文件说明（UploadFilePathEnum.desc);
	 */
	private String fileDescription;

	
	private File file;
	
	
	private String pathSource;

	private String pathWater;

	private String pathThumb;

	private Integer pathType;

	private String pathDate;

	private String pathDateFileName;

	private String urlDateFileName;

	private String xeDateFileName;

	private String[] allowTypes;

	private boolean flag;

	private String suffix;

	private String nullSuffix;

	private float width;

	private float height;

	private boolean thumbWhether;
	
	//源文件扩展名
	private String sourceExtendName = "jpg";





	public String getPathDate() {
		// 当前日期
		Date date = new Date();
		pathDate = new SimpleDateFormat("yyyy" + FILE_PATH_SEP + "MM"
				+ FILE_PATH_SEP + "dd").format(date)
				+ FILE_PATH_SEP;
		return pathDate;
	}

	public String getPathDateFileName() {
		pathDateFileName = pathDate + fileName;
		return pathDateFileName;
	}

	public String getUrlDateFileName() {
		urlDateFileName = FileUpload.getDoPath(pathDate + fileName);
		return urlDateFileName;
	}

	public String getXeDateFileName() {
		xeDateFileName = FileUpload.getDoPath(pathSource + pathDate + fileName);
		return xeDateFileName;
	}

	
	
}
