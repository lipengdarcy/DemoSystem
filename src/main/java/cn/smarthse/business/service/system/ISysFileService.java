package cn.smarthse.business.service.system;

import java.util.List;

import cn.smarthse.business.entity.system.SysFile;
import cn.smarthse.business.model.system.SysFileModel;
import cn.smarthse.framework.generic.GenericService;


 /**
 * 系统附件Service
 *
 */
public interface ISysFileService extends GenericService<SysFile>{

	/**
	 * 根据文件Id获得文件全路径地址
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年10月10日-下午3:36:20
	 * @param fileId
	 * @return
	 */
	String getFileUrlByfid(Integer fileId);
	
	/**
	 * 根据用户MD5读取附件信息
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年10月10日-下午5:03:53
	 * @param filehash		文件Hash码
	 * @return
	 */
	SysFile getFileByHash(String filehash);
	
	/**
	 * 根据用户MD5读取附件信息
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-10-下午4:34:05
	 * @param loginUserId
	 * @param filehash
	 * @return
	 */
	SysFile getFileByUserId(Long loginUserId, String filehash);

	/**
	 * 通过fileId获得fileModel
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月8日-下午2:18:15
	 * @param id
	 * @return
	 */
	SysFileModel getModelById(Integer fileId);
	
	/**
	 * 将file实体转换为filemodel
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月8日-下午2:19:43
	 * @param file
	 * @return
	 */
	SysFileModel file2model(SysFile file);

	/**
	 * 通过附件ids获得附件Model
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月13日-下午3:21:00
	 * @param verifiFileIds
	 * @return
	 */
	List<SysFileModel> getModelByIds(String fileIds);
}
