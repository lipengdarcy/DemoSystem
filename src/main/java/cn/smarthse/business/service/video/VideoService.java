
package cn.smarthse.business.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.dao.best.BestContractMapper;
import cn.smarthse.business.dao.best.BestProjectMapper;
import cn.smarthse.business.dao.best.TnetOrderMapper;
import cn.smarthse.business.dao.best.YouOrderMapper;
import cn.smarthse.business.entity.video.VideoFile;
import cn.smarthse.framework.generic.GenericServiceImpl;

@Service
public class VideoService extends GenericServiceImpl<VideoFile>  {

	@Autowired
	BestContractMapper BestContractMapper;

	@Autowired
	BestProjectMapper BestProjectMapper;

	@Autowired
	TnetOrderMapper TnetOrderMapper;

	@Autowired
	YouOrderMapper YouOrderMapper;

	

}
