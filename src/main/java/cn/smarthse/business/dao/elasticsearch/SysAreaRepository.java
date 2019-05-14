package cn.smarthse.business.dao.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.smarthse.business.model.elasticsearch.SysArea;

public interface SysAreaRepository extends ElasticsearchRepository<SysArea, Long> {
}
