package cn.smarthse.business.dao.elasticsearch;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import cn.smarthse.business.model.elasticsearch.Commodity;

//@Repository
public interface CommodityRepository extends ElasticsearchRepository<Commodity, String> {

}
