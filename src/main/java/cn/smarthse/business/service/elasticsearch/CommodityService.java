package cn.smarthse.business.service.elasticsearch;


import java.util.List;

import org.springframework.data.domain.Page;

import cn.smarthse.business.model.elasticsearch.Commodity;

public interface CommodityService {

    long count();

    Commodity save(Commodity commodity);

    void delete(Commodity commodity);

    Iterable<Commodity> getAll();

    List<Commodity> getByName(String name);

    Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw);

}
