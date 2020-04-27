package cn.smarthse.business.controller.video;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.smarthse.business.entity.video.VideoFile;
import cn.smarthse.business.service.video.FileUploadTool;
import cn.smarthse.business.service.video.VideoService;
import cn.smarthse.framework.model.ResponseData;
import io.swagger.annotations.Api;

@Controller
@Api(value = "视频的上传和在线播放")
@RequestMapping(value = "/v")
public class VideoController {

	private final String basePath = "video/";

	/**
	 * Service
	 */
	@Autowired
	private VideoService VideoService;

	/**
	 * 默认页面：视频列表
	 */
	@RequestMapping()
	public String index() {
		return basePath + "list";
	}

	/**
	 * 上传页面
	 */
	@RequestMapping(value = "uploadPage")
	public String upload() {
		return basePath + "upload";
	}

	@RequestMapping(value = "upload")
	@ResponseBody
	public ResponseData<String> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
			HttpServletRequest request) {

		ResponseData<String> data = new ResponseData<String>();
		VideoFile entity = new VideoFile();
		FileUploadTool fileUploadTool = new FileUploadTool();
		try {
			entity = fileUploadTool.createFile(multipartFile, request);
			if (entity != null) {
				data.setMessage("上传成功");
				data.setData(entity.getUrl());
			} else {
				data.setMessage("上传失败");
				data.setCode(-1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * This will actually load the whole video file in a byte array in memory, so
	 * it's not recommended.
	 */
	@RequestMapping(value = "/{id}/preview", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> getPreview1(@PathVariable("id") String id, HttpServletResponse response) {
		ResponseEntity<byte[]> result = null;
		try {
			VideoFile file = VideoService.getById(id);
			Path path = Paths.get(file.getUrl());
			byte[] image = Files.readAllBytes(path);

			response.setStatus(HttpStatus.OK.value());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentLength(image.length);
			result = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
		} catch (java.nio.file.NoSuchFileException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/*
	 * IOUtils is available in Apache commons io
	 */
	@RequestMapping(value = "/{id}/preview2", method = RequestMethod.GET)
	@ResponseBody
	public void getPreview2(@PathVariable("id") String id, HttpServletResponse response) {
		try {
			VideoFile file = VideoService.getById(id);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName().replace(" ", "_"));
			InputStream iStream = new FileInputStream(file.getUrl());
			IOUtils.copy(iStream, response.getOutputStream());
			response.flushBuffer();
		} catch (java.nio.file.NoSuchFileException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

	@RequestMapping(value = "/{id}/preview3", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource getPreview3(@PathVariable("id") String id, HttpServletResponse response) {
		VideoFile file = VideoService.getById(id);
		return new FileSystemResource(file.getUrl());
	}

}
