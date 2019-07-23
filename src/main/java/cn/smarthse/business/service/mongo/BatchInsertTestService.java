package cn.smarthse.business.service.mongo;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.smarthse.backup.dao.hotel.HotelDataMapper;
import cn.smarthse.backup.entity.hotel.HotelData;
import cn.smarthse.business.entity.system.SysAreaStandard;
import cn.smarthse.business.repository.test.MongoHotelDataDao;

/**
 * mybatis 批量数据插入测试（10000以上）
 * 
 * @author lipeng
 */
@Service
public class BatchInsertTestService {

	@Autowired
	private HotelDataMapper HotelDataMapper; // mysql dao

	@Resource
	MongoHotelDataDao MongoHotelDataDao; // mongodb dao

	@Autowired
	@Qualifier("defaultSqlSessionFactory")
	private SqlSessionFactory SqlSessionFactory;

	/**
	 * mysql-->mongodb 批量插入数据
	 */
	public int mysql_mongo(List<HotelData> list) {
		MongoHotelDataDao.insert(list);
		return list.size();
	}

	/**
	 * 批量插入数据
	 */
	public int batchInsert() {
		System.out.println("批量插入数据开始：");
		long beginTime = System.nanoTime();
		int count = 10000;
		for (int i = 0; i <= 13370748;) {
			List<HotelData> list = this.getList(i, count);
			MongoHotelDataDao.insert(list);
			i += count;
			System.out.println("批量插入数据数量：" + i);
		}

		long endTime = System.nanoTime();
		System.out.println("批量插入数据结束。用时：" + (endTime - beginTime) / 1000000000 + " 秒");
		return 13370748;
	}

	/**
	 * 获取插入数据
	 */
	public List<HotelData> getList(int index, int count) {
		return HotelDataMapper.getPage(index, count);
	}

	/**
	 * 单条插入数据
	 */
	public int singleInsert(HotelData record) {
		return HotelDataMapper.insert(record);
	}

	/**
	 * 批量插入数据
	 */
	public int batchInsert(List<SysAreaStandard> list) {
		int result = 1;
		SqlSession batchSqlSession = null;
		long beginTime = System.nanoTime();
		System.out.println("批量插入数据开始：");
		try {
			batchSqlSession = SqlSessionFactory.openSession(ExecutorType.BATCH, false);// 获取批量方式的sqlsession
			int batchCount = 1000;// 每批commit的个数
			int batchLastIndex = batchCount;// 每批最后一个的下标
			for (int index = 0; index < list.size();) {
				if (batchLastIndex >= list.size()) {
					batchLastIndex = list.size();
					result = result * batchSqlSession.insert("cn.smarthse.business.dao.ATempMapper.batchInsert",
							list.subList(index, batchLastIndex));
					batchSqlSession.commit();
					System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
					break;// 数据插入完毕，退出循环
				} else {
					result = result * batchSqlSession.insert("cn.smarthse.business.dao.ATempMapper.batchInsert",
							list.subList(index, batchLastIndex));
					batchSqlSession.commit();
					System.out.println("index:" + index + " batchLastIndex:" + batchLastIndex);
					index = batchLastIndex;// 设置下一批下标
					batchLastIndex = index + (batchCount - 1);
				}
			}
			batchSqlSession.commit();
		} finally {
			batchSqlSession.close();
		}
		long endTime = System.nanoTime();
		System.out.println("批量插入数据结束。用时：" + (endTime - beginTime) / 1000000000 + " 秒");
		return list.size();
	}

}
