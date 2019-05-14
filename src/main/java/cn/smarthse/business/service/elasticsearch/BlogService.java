package cn.smarthse.business.service.elasticsearch;


import org.springframework.data.domain.Page;

import cn.smarthse.business.model.elasticsearch.EsBlog;

public interface BlogService {

    long count();

    EsBlog save(EsBlog record);

    void delete(EsBlog record);
    
    void deleteAll();

    Iterable<EsBlog> getAll();

    Page<EsBlog> pageQuery(Integer pageNo, Integer pageSize, EsBlog queryParam);

}
