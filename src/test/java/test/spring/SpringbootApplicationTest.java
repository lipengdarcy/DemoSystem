package test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.smarthse.business.service.AsyncTaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTest {
	@Autowired
	private AsyncTaskService asyncTaskService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void threadTest() {
		for (int i = 0; i < 20; i++) {
			asyncTaskService.executeAsyncTask(i);
		}
	}
}