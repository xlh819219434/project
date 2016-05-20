package project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhxie.service.redis.IRedisTestService;

//指定bean注入的配置文件 
@ContextConfiguration(locations = { "classpath:spring-mvc.xml",
		"classpath:spring-mybatis.xml", "classpath:spring.xml" })
// 使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {
	@Autowired
	private IRedisTestService redisTestService;

	@Test
	public void getTimestampTest() throws InterruptedException {
		System.out.println("第一次调用：" + redisTestService.getTimestamp("param"));
		Thread.sleep(2000);
		System.out.println("2秒之后调用：" + redisTestService.getTimestamp("param"));
		Thread.sleep(11000);
		System.out.println("再过11秒之后调用："
				+ redisTestService.getTimestamp("param"));
	}
}
