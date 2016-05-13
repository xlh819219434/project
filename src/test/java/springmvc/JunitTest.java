package springmvc;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.lhxie.model.Student;
import com.lhxie.service.IStudentServcie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml" })
public class JunitTest {

	private static Logger logger = Logger.getLogger(JunitTest.class);
//
//	@Autowired
//	private UserServiceImpl userServiceImpl;

	@Autowired
	private IStudentServcie studentServcieImpl;

	// private ApplicationContext ac;

	// @Before
	// public void before() {
	// ac = new ClassPathXmlApplicationContext("spring.xml",
	// "spring-mybatis.xml");
	// }

//	@Test
//	public void testGetUserByUserId() {
//		User user = userServiceImpl.getUserById(1);
//		System.out.println(user.getNickname());
//	}
//
//	@Test
//	public void listAllUserTest1() {
//		List<User> listUsers = userServiceImpl.listAll();
//
//		logger.info(JSON.toJSONStringWithDateFormat(listUsers,
//				"yyyy-MM-dd hh:MM:ss"));
//
//	}
//
//	@Test
//	public void listAllUserTest2() {
//		List<User> listUsers = userServiceImpl.listAll2();
//
//		logger.info(JSON.toJSONStringWithDateFormat(listUsers,
//				"yyyy-MM-dd hh:MM:ss"));
//
//	}
//
//	@Test
//	public void listAllUserTest3() {
//		List<User> listUsers = userServiceImpl.listAll3();
//
//		logger.info(JSON.toJSONStringWithDateFormat(listUsers,
//				"yyyy-MM-dd hh:MM:ss"));
//
//	}
//
//	@Test
//	public void listAllUserTest4() {
//		List<User> listUsers = userServiceImpl.listAll4();
//
//		logger.info(JSON.toJSONStringWithDateFormat(listUsers,
//				"yyyy-MM-dd hh:MM:ss"));
//
//	}

	@Test
	public void listAllStudentTest() {
		List<Student> listStudents = studentServcieImpl.listAllStudent();
		logger.info(JSON.toJSON(listStudents));
		
	}

}
