package cn.smarthse.business.controller.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.smarthse.business.entity.system.SysFile;
import cn.smarthse.business.model.system.SysFileModel;
import cn.smarthse.business.service.system.ISysFileService;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.model.ResponseStateEnum;
import cn.smarthse.framework.util.StringUtil;
import io.swagger.annotations.Api;

/**
 * 
 * 通用上传功能
 * 
 * 通用下载功能
 * 
 * 检测文件Hash功能
 * 
 */
@Api(value = "组件：文件上传下载", tags = "组件：文件上传下载")
@Controller
@RequestMapping("/tool/file")
public class FileUploadController {

	private final String info_prefix = "【附件上传】";

	private Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	private ISysFileService fileService;

	/**
	 * 通用附件上传
	 * 
	 * @param type
	 *            附件类型
	 * @param filehash
	 *            附件hash码
	 * 
	 */
	@Log(description = "上传附件", type = LogConstans.type_opt_upload)
	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public ResponseData<SysFile> ajax_upload(String filehash, HttpServletRequest req) throws Exception {
		log.debug("{}->【hash值：{}】", info_prefix, filehash);
		Integer cid = ShiroUtil.getLoginCid() != null ? ShiroUtil.getLoginCid() : 0;

		ResponseData<SysFile> data = new ResponseData<SysFile>();
		return data;
	}

	@Log(description = "上传附件", type = LogConstans.type_opt_upload)
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseData<SysFileModel> ajax_add(String originalFilename, String origSize, String etagMd5, String fileUrl,
			String mimeType, String description, HttpServletRequest req) throws Exception {
		// 当前用户所在企业
		Integer cid = ShiroUtil.getLoginCid() != null ? ShiroUtil.getLoginCid() : 0;

		// 返回结果
		ResponseData<SysFileModel> data = new ResponseData<SysFileModel>();

		SysFile file = new SysFile();
		// 源文件名(无扩展名)
		file.setFileName(originalFilename);
		// 文件大小
		file.setFileSize(origSize);
		// 文件Hash编码
		file.setMd5(etagMd5);
		// OSS相对路径
		file.setUrl(fileUrl); // 文件保存路径
		// 文件说明
		file.setDescription(description);// 文件说明
		file.setMimeType(mimeType);
		// 插入到附件表
		file.setCid(cid);
		file = fileService.insert(file);

		// 设置为filemodel
		SysFileModel fileModel = fileService.file2model(file);

		data.setData(fileModel);
		data.setCode(ResponseStateEnum.success.getCode());
		return data;
	}

	/**
	 * 验证附件HASH、MD5码
	 * <li>返回success，返回附件并提示上传成功
	 * <li>返回fail，前端需要异步提交上传，走upload接口
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-6-19-上午9:54:40
	 * @param filehash
	 *            附件Hash(md5)码
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "check/{filehash}", method = RequestMethod.POST)
	public ResponseData<SysFile> ajax_check_filemd5(@PathVariable("filehash") String filehash, HttpServletRequest req)
			throws Exception {

		SysFile record = fileService.getFileByHash(filehash);
		// 2、返回实体或者记录
		ResponseData<SysFile> data = new ResponseData<SysFile>();
		if (record != null) {
			// 形成完整的ossurl供前端使用
			// record.setFullOssurl(ossClientBean.getUrl(record.getUrl()));

			data.setData(record);
			data.setCode(ResponseStateEnum.success.getCode());
		} else {
			data.setCode(ResponseStateEnum.fail.getCode());
		}

		// 返回
		return data;
	}

	/**
	 * 服务器响应结果
	 * 
	 * @param request
	 * @param response
	 * @param results
	 * @param status
	 * @throws IOException
	 */
	private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
			throws IOException {
		String callbackFunName = request.getParameter("callback");
		response.addHeader("Content-Length", String.valueOf(results.length()));
		if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
			response.getWriter().println(results);
		else
			response.getWriter().println(callbackFunName + "( " + results + " )");
		response.setStatus(status);
		response.flushBuffer();
	}

	/**
	 * 获取Post消息体
	 * 
	 * @param is
	 * @param contentLen
	 * @return
	 */
	public String GetPostBody(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return new String(message);
			} catch (IOException e) {
			}
		}
		return "";
	}

	// /**
	// * 保存附件线程
	// */
	// public static class SaveFileThread extends Thread{
	// private String ossCallbackBody;
	//
	// private ISysFileService fileService;
	//
	// public SaveFileThread(String ossCallbackBody, ISysFileService fileService){
	// super(SaveFileThread.class.getSimpleName());
	// this.ossCallbackBody = ossCallbackBody;
	// this.fileService = fileService;
	// }
	//
	// @Override
	// public void run() {
	// // 保存日志信息
	// JSONObject obj = JSONObject.fromObject(ossCallbackBody);
	//
	// String bucketName = obj.getString("bucket");
	// String filename = obj.getString("filename");
	// String size = obj.getString("size");
	// String mimeType = obj.getString("mimeType");
	// String uid = obj.getString("uid");
	// String cid = obj.getString("cid");
	//// sysLogService.add(log);
	//
	// SysFile file = new SysFile();
	// //源文件名(无扩展名)
	// file.setFileName(up.getOriginalFilename());
	// //文件大小
	// file.setFileSize(up.getFilesize()+"");
	// //文件扩展名
	// file.setFileExt(up.getFileExt());
	// //文件Hash编码
	// file.setFileHash(filehash);
	// //当前IP地址
	// file.setCreatorIp(StringUtils.getRemoteAddr(req));
	// //OSS相对路径
	// file.setOssUrl(ossfile); // 文件保存路径
	// //文件说明
	// file.setDescription(pathEnum.getDesc());// 文件说明
	//
	// if(StringUtils.isEmpty(ossfile)){
	// //同步阿里云打败，或者未设置同步，保存为本地附件
	// file.setOssUrl(StringUtils.local2url(up.getRealPath(),
	// UploadFileParameter.FILE_PATH));
	// }
	//
	// //插入到附件表
	// file.setCreateBy(loginUserId);
	// file.setCreateDate(new Date());
	// file = fileService.add(file);
	//
	// }
	// }

	/**
	 * 获取文件信息
	 * 
	 * @param idStr
	 *            文件id列表，用逗号分割
	 */
	@ResponseBody
	@RequestMapping(value = "getFile", method = RequestMethod.POST)
	public ResponseData<List<SysFileModel>> getFile(String idStr) throws Exception {
		ResponseData<List<SysFileModel>> data = new ResponseData<List<SysFileModel>>();
		List<SysFileModel> list = new ArrayList<SysFileModel>();
		if (!StringUtil.isEmpty(idStr) && idStr.split(",").length != 0) {
			String[] array = idStr.split(",");
			for (String a : array) {
				SysFileModel file = fileService.getModelById(Integer.valueOf(a));
				list.add(file);
			}
			data.setData(list);
		}
		return data;
	}

}
