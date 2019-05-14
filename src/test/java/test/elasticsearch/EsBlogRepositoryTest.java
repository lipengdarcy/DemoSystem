package test.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import cn.smarthse.business.dao.elasticsearch.BlogRepository;
import cn.smarthse.business.model.elasticsearch.EsBlog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {

	@Autowired
	private BlogRepository esBlogRepository;

	public void initRepositoryData() {

		/**
		 * 删除所有数据
		 */
		esBlogRepository.deleteAll();

		/**
		 * 初始化三个blog
		 */
		esBlogRepository.save(new EsBlog("1", "段", "段朝旭"));
		esBlogRepository.save(new EsBlog("2", "朝", "段朝旭"));
		esBlogRepository.save(new EsBlog("3", "旭", "段朝旭"));
	}

	/**
	 * 分页查询博客去重
	 * 
	 * @param title
	 * @param summary
	 * @param content
	 * @param pageable
	 * @return Page<EsBlog>
	 */
	@Test
	public void findDistinctEsBlogByTitleContaingOrSummaryContaingOrContentContaing() {

		initRepositoryData();
		Pageable pageable = PageRequest.of(0, 20);
		Page<EsBlog> page = esBlogRepository
				.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining("1", "旭", "王", pageable);

		System.out.println(page.getTotalElements());
		for (EsBlog blog : page.getContent()) {
			System.out.println(blog);
		}

	}
}
