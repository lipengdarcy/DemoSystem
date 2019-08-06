package cn.smarthse.backup.service.hotel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.smarthse.backup.dao.hotel.HotelDataMapper;
import cn.smarthse.backup.entity.hotel.HotelData;
import cn.smarthse.backup.model.hotel.HotelDataModel;
import cn.smarthse.business.mongo.repository.test.MongoHotelDataDao;
import cn.smarthse.framework.generic.GenericServiceImpl;
import cn.smarthse.framework.model.DataTableData;
import cn.smarthse.framework.model.DataTableParam;
import cn.smarthse.framework.model.JqGridData;
import cn.smarthse.framework.model.JqGridParam;
import cn.smarthse.framework.util.StringUtil;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class HotelDataService extends GenericServiceImpl<HotelData> {

	@Resource
	MongoTemplate mongoTemplate;

	@Autowired
	private HotelDataMapper HotelDataMapper; // mysql dao

	@Resource
	MongoHotelDataDao MongoHotelDataDao; // mongodb dao

	public List<HotelData> queryData(HotelDataModel data) {

		Example e = new Example(HotelData.class);
		Criteria c = e.createCriteria();

		// 姓名
		if (!StringUtil.isEmpty(data.getName()))
			c.andEqualTo("name", data.getName());
		// 生日
		if (!StringUtil.isEmpty(data.getBirthday()))
			c.andEqualTo("birthday", data.getBirthday());
		// 入住时间
		if (data.getBegin() != null && data.getEnd() != null)
			c.andBetween("version", data.getBegin(), data.getEnd());

		List<HotelData> list = HotelDataMapper.selectByExample(e);
		return list;
	}

	/**
	 * 分页查询数据,返回grid格式的数据:mysql
	 * 
	 */
	public JqGridData<HotelData> queryData(JqGridParam param, HotelDataModel dataParam) {
		PageHelper.startPage((int) param.getPage(), (int) param.getRows());
		Page<HotelData> list = (Page<HotelData>) this.queryData(dataParam);
		JqGridData<HotelData> data = new JqGridData<HotelData>(list, param);
		return data;
	}

	/**
	 * 分页查询数据,返回datatable格式的数据:mongodb
	 * 
	 */
	public DataTableData<HotelData> getMongoData(DataTableParam param, HotelDataModel dataParam) {
		PageRequest pageRequest = PageRequest.of(param.getStart() / param.getLength(), param.getLength());
		Query query = new Query();
		org.springframework.data.mongodb.core.query.Criteria c = new org.springframework.data.mongodb.core.query.Criteria();
		// 姓名
		if (!StringUtil.isEmpty(dataParam.getName()))
			c.and("name").is(dataParam.getName());
		// 手机
		if (!StringUtil.isEmpty(dataParam.getMobile()))
			c.and("mobile").is(dataParam.getMobile());
		// 生日
		if (!StringUtil.isEmpty(dataParam.getBirthday()))
			c.and("birthday").is(dataParam.getBirthday());

		query.addCriteria(c);
		// 排序
		query.with(new Sort(Direction.ASC, "_id"));

		long count = mongoTemplate.count(query, HotelData.class);
		// long count = mongoTemplate.count(query, "hotel_data");
		query.with(pageRequest);
		List<HotelData> list = mongoTemplate.find(query, HotelData.class);
		org.springframework.data.domain.Page<HotelData> page = new org.springframework.data.domain.PageImpl<HotelData>(
				list, pageRequest, count);
		DataTableData<HotelData> data = new DataTableData<HotelData>(page);
		return data;
	}

	/**
	 * 分页查询数据,返回grid格式的数据:mongodb
	 * 
	 */
	public JqGridData<HotelData> getMongoGridData(JqGridParam param, HotelDataModel dataParam) {
		PageRequest pageRequest = PageRequest.of((int) param.getPage() - 1, (int) param.getRows());
		Query query = new Query();
		org.springframework.data.mongodb.core.query.Criteria c = new org.springframework.data.mongodb.core.query.Criteria();
		// 姓名
		if (!StringUtil.isEmpty(dataParam.getName()))
			c.and("name").is(dataParam.getName());
		// 生日
		if (!StringUtil.isEmpty(dataParam.getBirthday()))
			c.and("birthday").is(dataParam.getBirthday());

		query.addCriteria(c);
		// 排序
		query.with(new Sort(Direction.ASC, "_id"));

		long count = mongoTemplate.count(query, HotelData.class);
		// long count = mongoTemplate.count(query, "hotel_data");
		query.with(pageRequest);
		List<HotelData> list = mongoTemplate.find(query, HotelData.class);
		// 这种其实是假分页，点击最后一页，则所有记录都返回了，内存由初始的90M瞬间到2.5G
		org.springframework.data.domain.Page<HotelData> page = new org.springframework.data.domain.PageImpl<HotelData>(
				list, pageRequest, count);
		JqGridData<HotelData> data = new JqGridData<HotelData>(page);
		return data;
	}

}
