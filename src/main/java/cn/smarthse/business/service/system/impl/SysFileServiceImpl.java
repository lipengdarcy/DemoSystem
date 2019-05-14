/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2019
 */

package cn.smarthse.business.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.smarthse.business.dao.system.SysFileMapper;
import cn.smarthse.business.entity.system.SysFile;
import cn.smarthse.business.model.system.SysFileModel;
import cn.smarthse.business.service.system.ISysFileService;
import cn.smarthse.framework.context.bean.oss.OssClientBean;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * 系统附件Service
 *
 *
 *
 * @Module ID: <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments: <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used: <JDK1.8>
 * @author <开发者>
 * @since 2019-02-22 09:11
 */
@Service
@Transactional(readOnly = true)
public class SysFileServiceImpl extends GenericServiceImpl<SysFile> implements ISysFileService {

	// OSS Bean工具
	@Autowired
	private OssClientBean ossClientBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysFileService#getFileUrlByfid(java.lang
	 * .Integer)
	 */
	@Override
	public String getFileUrlByfid(Integer fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysFileService#getFileByHash(java.lang.
	 * String)
	 */
	@Override
	public SysFile getFileByHash(String filehash) {
		Example example = new Example(SysFile.class);
		example.createCriteria().andEqualTo("md5", filehash).andEqualTo("isValid", true);

		return getOneByExample(example);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysFileService#getFileByUserId(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public SysFile getFileByUserId(Long loginUserId, String filehash) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysFileService#getModelById(java.lang.
	 * Integer)
	 */
	@Override
	public SysFileModel getModelById(Integer fileId) {
		if (fileId != null && fileId > 0) {
			SysFile file = getById(fileId);

			return file2model(file);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.smarthse.business.service.system.ISysFileService#file2model(cn.smarthse.
	 * business.entity.system.SysFile)
	 */
	@Override
	public SysFileModel file2model(SysFile file) {
		if (file != null) {
			SysFileModel fileModel = new SysFileModel();
			fileModel.setCreateTime(file.getCreateTime());
			fileModel.setDescription(file.getDescription());
			fileModel.setFileName(file.getFileName());
			fileModel.setFileSize(file.getFileSize());
			fileModel.setId(file.getId());
			fileModel.setMd5(file.getMd5());
			fileModel.setMimeType(file.getMimeType());
			fileModel.setFullpath(ossClientBean.getUrl(file.getUrl()));
			return fileModel;
		}

		return null;
	}

	@Override
	public List<SysFileModel> getModelByIds(String fileIds) {
		if (StringUtils.isEmpty(fileIds)) {
			return null;
		}

		List<SysFile> list = new ArrayList<SysFile>();
		for (String id : fileIds.split(",")) {
			SysFile file = this.getById(id);
			list.add(file);
		}
		List<SysFileModel> modelList = new ArrayList<>();
		list.forEach(file -> {
			SysFileModel fileModel = file2model(file);
			modelList.add(fileModel);
		});

		return modelList;
	}

}
