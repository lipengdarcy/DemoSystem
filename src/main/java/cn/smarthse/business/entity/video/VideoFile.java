package cn.smarthse.business.entity.video;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * 视频文件信息
 */
@Table(name = "video_file")
public @Data class VideoFile implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * OSS存放路径
	 */
	private String url;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private String fileSize;

	/**
	 * 文件类型， 例如: image/png
	 */
	private String fileType;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 文件md5值
	 */
	private String md5;

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 是否有效
	 */
	private Boolean isValid;

}
