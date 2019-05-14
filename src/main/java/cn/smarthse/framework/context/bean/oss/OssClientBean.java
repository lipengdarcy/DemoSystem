package cn.smarthse.framework.context.bean.oss;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.ResponseHeaderOverrides;

import cn.smarthse.framework.util.DateUtils;
import cn.smarthse.framework.util.StringUtils;
import net.sf.json.JSONObject;


/**
 * 《OSS 客户端配置工具类》
 * 
 * 
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.7> 
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2016-8-31-下午1:40:49
 */
public class OssClientBean {
	private Logger logger = LogManager.getLogger(this.getClass());
	private static final String FILE_NAME_POINT_STRING=".";//文件名“点”
	//==================OSS 配置相关==================================
	//oss所在域
	private String domain = "oss-cn-hangzhou.aliyuncs.com";
	private String endpoint = "http://" + domain;
	private String accessKeyId = "YfPv24tEsppP7xNC";
	private String accessKeySecret = "ZELBgec7xToUrZEo05EnHm9dxVWHoz";
//		private String bucketName = "hse-oss-file";	//正式库时的配置
	private String bucketName = "yanfa-oss-file";//开发环境时的配置
	
	/**
	 * 直传回调地址
	 */
	private String callbackUrl;
	
	//
	private OSSClient client = null;
	
	//=========================相关方法=================================
	
	
	/**
     * 生成签名URL
     * @param urlfile
     */
    public String getUrl(String urlfile){
    	return getUrl(urlfile, null);
    }
    
    /**
     * 生成签名URL
     * @param urlfile		OSS短地址
     * @param filename		自定义Header头，增加下载文件名
     * @param exptime		访问有效期
     * @return
     */
    public String getUrl(String urlfile, String filename){
    	//System.out.println(urlfile);
    	if(StringUtils.isEmpty(urlfile)){
    		return "";
    	}
    	//远程连接地址，直接返回
    	if(urlfile.indexOf("http://")>=0 || urlfile.indexOf("https://")>=0){
    		return urlfile;
    	}
    	
    	//是否公开读
    	StringBuffer url = new StringBuffer();
		url.append("//").append(bucketName).append(".").append(domain).append("/").append(urlfile);
		////?response-content-disposition=attachment; filename="mikako.jpg"
		if(StringUtils.isNotEmpty(filename)){
			url.append("?response-content-disposition=attachment;filename=" + filename);
		}
		return url.toString();
    }
	
    /**
	 * 
	 * 获取文件
	 * 
	 * @param fileUrl
	 *            文件名称(如：Default/3b0f7ead-433a-4e0a-8a98-6773d799170c.jpg)
	 * @param input
	 *            文件内容
	 * @return 返回文件流
	 * 
	 */
	public InputStream getFile(String fileUrl) {
		try {
			OSSObject ossObj = getClient().getObject(bucketName, fileUrl);
			return ossObj.getObjectContent();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
   /**
    * 生成访问地址，增加
    * @param bucketName
    * @param attachmentFilename
    * @param key
    * @param expiration
    * @param method
    * @return
    * @throws ClientException
    */
    private URL generatePresignedUrl(String bucketName, String attachmentFilename, String key, Date expiration, HttpMethod method) 
			throws ClientException {
    	
    	GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
		request.setExpiration(expiration);
		request.setMethod(method);
		//自定义头文件 // 要重载的返回请求头。
	    ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
	    responseHeaders.setContentDisposition("attachment;filename=\""+attachmentFilename+"\"");
	    request.setResponseHeaders(responseHeaders);
		//
    	return getClient().generatePresignedUrl(request);
    }
    
    /**
     * 生成签名URL
     * @param urlfile
     */
    public String getUrlBybucket(String bucket, String urlfile){
    	if(StringUtils.isEmpty(urlfile) || StringUtils.isEmpty(bucket)){
    		return "";
    	}
    	//远程连接地址，直接返回
    	if(urlfile.indexOf("http://")>=0 || urlfile.indexOf("https://")>=0){
    		return urlfile;
    	}
    	
    	
    	StringBuffer url = new StringBuffer();
		url.append("http://").append(bucketName).append(".").append("oss-cn-hangzhou.aliyuncs.com").append("/").append(urlfile);
		//
		return url.toString();
    }
    
    
    /**
     * 
     * 上传文件 2015年7月2日14:11:27
     * 
     * @param serviceFilename   服务器的文件名称
     * @param file      		要上传的文件
     * @param serviceDir        服务器要访问的路径名
     * @param fileFullname      文件原名(必须带扩展名)
     * @param contentMD5      文件Hash编码
     * @return					返回文件名，空者返回失败
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public String OSSPutObject(String serviceFilename, File file, String serviceDir, String fileFullname, String contentMD5) {
    	return OSSPutObject(serviceFilename, file, serviceDir, fileFullname, contentMD5, null);
    }
    
    /**
     * 
     * 上传文件 2015年7月2日14:11:27
     * 
     * @param serviceFilename   服务器的文件名称
     * @param file      		要上传的文件
     * @param serviceDir        服务器要访问的路径名
     * @param contentType       文件类型
     * @return					返回文件名，空者返回失败
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public String OSSPutObject(String serviceFilename, File file, String serviceDir, String fileFullname, String contentMD5, String contentType) {
    	//标识中加入文件名
    	ObjectMetadata objectMeta = new ObjectMetadata();
    	objectMeta.setContentLength(file.length());
    	//文件名
    	if(StringUtils.isNotEmpty(fileFullname))
    	{
    		objectMeta.setContentDisposition("attachment;filename="+fileFullname);
    	}
    	//文件类型
    	if(StringUtils.isNotEmpty(contentType)) {
    		objectMeta.setContentType(contentType);
    	}
    	
    	//文件md5
    	if(StringUtils.isNotEmpty(contentMD5)) {
    		objectMeta.setContentMD5(contentMD5);
    	}
    	
    	
    	return OSSPutObject(serviceFilename, file, serviceDir, objectMeta);
    }
    
    /**
     * 
     * 上传文件 2015年7月2日14:11:27
     * 
     * @param serviceFilename   服务器的文件名称
     * @param file      		要上传的文件
     * @param serviceDir        服务器要访问的路径名
     * @param objectMeta        文件元信息
     * @return					返回文件名，空者返回失败
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public String OSSPutObject(String serviceFilename, File file, String serviceDir, ObjectMetadata objectMeta) {
    	StringBuffer ossfile = new StringBuffer();
    	ossfile.append(serviceDir).append("/").append(serviceFilename);
    	
    	InputStream input;
    	try {
    		input = new FileInputStream(file);
    		PutObjectResult resultObject = getClient().putObject(bucketName, ossfile.toString(), input, objectMeta);
    		//组合路径
    		
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		return "";
    	}
    	
    	return ossfile.toString();
    	//return "http://" + Global.OSSIMAGE + ".oss-cn-hangzhou.aliyuncs.com/"     	+ serviceName + "/" + key;
    }
    
    
    
    
    
    
    /**
     * 同步上传文件流
     * @param key
     * @param multipartFile
     * @param serviceName
     * @return
     */
    public String OSSPutObject(String key, MultipartFile multipartFile , String serviceName, String contentMD5) {
    	CommonsMultipartFile commonsMultipartFile= (CommonsMultipartFile)multipartFile;
    	DiskFileItem diskFileItem = (DiskFileItem)commonsMultipartFile.getFileItem();
    	File file = diskFileItem.getStoreLocation();
    	String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    	return OSSPutObject(key+fileSuffix, file, serviceName, multipartFile.getOriginalFilename(), contentMD5);
    }
    
    
    /**
     * 下载文件  
     * @param file				File.ossurl
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public OSSObject  downloadFile( String file) {  
    	//fileclient.getObject(new GetObjectRequest(OSSFILE, file), new File(filename));  
    	OSSObject obj=null;
    	try{
    	obj=getClient().getObject(bucketName, file);
    	} catch(OSSException oe){
    		logger.error("阿里云文件获取失败:" + oe.getMessage());
    	} catch (ClientException ce){
    		logger.error("阿里云连接失败:" + ce.getMessage());
    	}catch(NullPointerException n){
    		logger.error("阿里云文件获取失败:" + n.getMessage());
    	}
    	
    	return obj;
    }
    
    /**
     * 从阿里云下载文件
     * @param fileOssurl 源文件相对路径（文件在阿里云的相对路径，也是File实体类的ossurl属性）
     * @param destFilePath 需要生成的目标文件路径
     * @param fileName 需要生成的目标文件名称
     * @return 文件名（如果有重复文件名，则返回重命名后的文件名；如果没有，则返回原文件名）
     * @throws IOException
     */
    public String downloadFile(String fileOssurl,String destFilePath,String fileName) throws IOException{
    	OSSObject ossObject=this.downloadFile(fileOssurl);
    	if(StringUtils.isEmpty(fileOssurl)||StringUtils.isEmpty(destFilePath)||ossObject==null||ossObject.getObjectContent()==null){
    		return fileName;
    	}
    	BufferedOutputStream bos = null;
    	InputStream reader = null;
    	String[] names={fileName};
    	
    	File file = this.getNoRepeatDestFilePath(destFilePath, names);
    	File parentFile = file.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("目录创建失败");
            }
        }
    	
    	try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			reader = ossObject.getObjectContent();
			int len = 2048;
            byte[] b = new byte[len];
            while ((len = reader.read(b)) > 0) {
				bos.write(b, 0, len);
            }
            bos.flush();
		} catch (FileNotFoundException e) {
			logger.error("文件未找到:" +  e.getMessage());
		} catch (IOException e) {
			logger.error("文件下载失败，输入输出错误:" +  e.getMessage());
		}finally {
            if (reader != null) {
                reader.close();
            }
            if (bos != null)
            	bos.close();
        }
    	return names[0];
    }
    
    /**
     * 获取目标文件对象（检查重复，不覆盖）
     * @param destFilePath 目标路径
     * @param fileName 文件名
     * @return 目标文件对象
     */
    private File getNoRepeatDestFilePath(String destFilePath,String[] fileName){
    	if (!destFilePath.endsWith(File.separator)){
    		destFilePath = destFilePath+File.separator;
    	}
    	String fileSuffix = "";
    	String pointStr = FILE_NAME_POINT_STRING;
    	int pointIndex = fileName[0].lastIndexOf(FILE_NAME_POINT_STRING);
    	if (pointIndex > 0){
    		fileSuffix = fileName[0].substring(pointIndex+1);
    		fileName[0] = fileName[0].substring(0, pointIndex);
    	} else {
    		pointStr="";
    	}
    	
    	File file = new File(destFilePath+fileName[0]+pointStr+fileSuffix);
    	int i=1;
    	while (file.exists()){
    		file=new File(destFilePath+fileName[0]+i+pointStr+fileSuffix);
    		i++;
    	}
    	if (i>1) {
    		fileName[0]=fileName[0]+--i+pointStr+fileSuffix;
    	} else {
    		fileName[0]=fileName[0]+pointStr+fileSuffix;
    	}
    	
    	return file;
    }
    
    /**
     * 读取文件元信息
     * @param url
     * @return
     */
    public ObjectMetadata getObjectMetadataByUrl(String url){
    	ObjectMetadata metadata = getClient().getObjectMetadata(bucketName, url);
    	
    	return metadata;
    }
    public InputStream getInputStreamByUrl(String fileUrl) throws IOException{
		InputStream reader = null;
		try{
		   URL   url   = new   URL(fileUrl);
		   HttpURLConnection   urlCon   =   (HttpURLConnection)url.openConnection();
		   urlCon.setConnectTimeout(5000);
		   urlCon.setReadTimeout(5000);
		   reader =  urlCon.getInputStream();			   
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return reader;
	}
    
    
    /**
     * 生成企业+用户 直传OSS签名
     * 
     * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
     * @author JannyShao(邵建义) [ksgameboy@qq.com]
     * @since 2019年2月25日-下午2:06:45
     * @param dir		上传目录
     * @param uid		用户Id
     * @param cid		企业Id
     * @param description		文档说明
     * @return
     * @throws Exception
     */
    public Map<String, String> getPostSignature(String dir) throws Exception {
    	//
    	String host = "http://" + bucketName + "." + domain; // host的格式为 bucketname.endpoint
		//
    	long expireTime = 30;
		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

		String postPolicy = getClient().generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = getClient().calculatePostSignature(postPolicy);
		
		Map<String, String> respMap = new LinkedHashMap<String, String>();
		respMap.put("accessid", accessKeyId);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", dir);
		respMap.put("host", host);
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		
		JSONObject jasonCallback = new JSONObject();
//		jasonCallback.put("callbackUrl", callbackUrl);
		//自定义参数示例:
//		String callbackbody = "{\'bucket\':${bucket}, \'object\':${object},\'size\':${size},\'mimeType\':${mimeType},\'uid\':${x:uid},\'biz_type\':${x:biz_type}}";
//		if("1".equals(biz_type)){//用户头像，banner修改
//		callbackbody = "{\'bucket\':${bucket}, \'object\':${object},\'size\':${size},\'mimeType\':${mimeType},\'uid\':${x:uid},\'biz_type\':${x:biz_type},\'portrait\':${x:portrait},\'banner\':${x:banner}}";
//		}else if("2".equals(biz_type)){//投诉建议
//		callbackbody = "{\'bucket\':${bucket}, \'object\':${object},\'size\':${size},\'mimeType\':${mimeType},\'uid\':${x:uid},\'biz_type\':${x:biz_type},\'path\':${x:path},\'guideid\':${x:guideid}}";
//		}
		StringBuffer callbackBody = new StringBuffer("{");
		callbackBody.append("bucket:").append("${bucket}");
		callbackBody.append(",filename:").append("${object}");
		callbackBody.append(",etag:").append("${etag}");
		callbackBody.append(",size:").append("${size}");
		callbackBody.append(",mimeType:").append("${mimeType}");
		callbackBody.append(",height:").append("${imageInfo.height}");
		callbackBody.append(",width:").append("${imageInfo.width}");
		callbackBody.append(",format:").append("${imageInfo.format}");
		//自定义, 当前上传企业 + 用户Id
//		callbackBody.append(",uid:").append(uid);
//		callbackBody.append(",cid:").append(cid);
//		callbackBody.append(",description:\'").append(description).append("\'");
		callbackBody.append("}");
		jasonCallback.put("callbackBody",
//				"filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}"
				callbackBody.toString()
				);
		//bucket=${bucket}&object=${object}&etag=${etag}&size=${size}&mimeType=${mimeType}&imageInfo.height=${imageInfo.height}&imageInfo.width=${imageInfo.width}&imageInfo.format=${imageInfo.format}&my_var=${x:my_var}
		jasonCallback.put("callbackBodyType", "application/json");
//		jasonCallback.put("callbackHost", "可选参数，默认为callbackUrl");
//		jasonCallback.put("callbackBodyType", "可选参数，支持application/x-www-form-urlencoded和application/json，默认为前者");
		
		
		String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
		respMap.put("callback", base64CallbackBody);
		return respMap;
    }
    
    /**
	 * 验证上传回调的Request
	 * 
	 * @param request
	 * @param ossCallbackBody
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
    public boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
			throws NumberFormatException, IOException {
		boolean ret = false;
		String autorizationInput = new String(request.getHeader("Authorization"));
		String pubKeyInput = request.getHeader("x-oss-pub-key-url");
		byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
		byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
		String pubKeyAddr = new String(pubKey);
		if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
				&& !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
			System.out.println("pub key addr must be oss addrss");
			return false;
		}
		String retString = executeGet(pubKeyAddr);
		retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
		retString = retString.replace("-----END PUBLIC KEY-----", "");
		String queryString = request.getQueryString();
		String uri = request.getRequestURI();
		String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
		String authStr = decodeUri;
		if (queryString != null && !queryString.equals("")) {
			authStr += "?" + queryString;
		}
		authStr += "\n" + ossCallbackBody;
		ret = doCheck(authStr, authorization, retString);
		return ret;
	}
	
	/**
	 * 获取public key
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings({ "finally" })
	public String executeGet(String url) {
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			@SuppressWarnings("resource")
			DefaultHttpClient client = new DefaultHttpClient();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return content;
		}
	}
    
	/**
	 * 验证RSA
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public static boolean doCheck(String content, byte[] sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(sign);
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
    
    
    
	//========================GET、Set================================
    /**
	 * 生成host全路径
	 * @return
	 */
    public String getHost(){
    	return "http://" + bucketName + "." + domain;
    }
    
	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}
	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	/**
	 * @return the accessKeyId
	 */
	public String getAccessKeyId() {
		return accessKeyId;
	}
	/**
	 * @param accessKeyId the accessKeyId to set
	 */
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	/**
	 * @return the accessKeySecret
	 */
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	/**
	 * @param accessKeySecret the accessKeySecret to set
	 */
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	/**
	 * @return the bucketName
	 */
	public String getBucketName() {
		return bucketName;
	}
	/**
	 * @param bucketName the bucketName to set
	 */
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	/**
	 * @return the client
	 */
	public OSSClient getClient() {
		if(client==null){
			client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
		}
		return client;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

		
}
