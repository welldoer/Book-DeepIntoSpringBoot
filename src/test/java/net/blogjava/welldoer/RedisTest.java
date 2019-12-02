package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.testcontainers.containers.GenericContainer;

import net.blogjava.welldoer.entity.Department;
import net.blogjava.welldoer.entity.Role;
import net.blogjava.welldoer.entity.User;
import net.blogjava.welldoer.repository.UserRedis;

@DataRedisTest
@EnableSpringConfigured
@ComponentScan(basePackageClasses=RedisConfig.class)
class RedisTest {
	private static Logger logger = LoggerFactory.getLogger(RedisTest.class);

	@Autowired
	UserRedis userRedis;
	
	@ClassRule
	private static final GenericContainer redis = new GenericContainer<>("redis:5.0.3-alpine").withExposedPorts(6379);
	
	@BeforeAll
	public static void init() {
		redis.start();
		
		System.setProperty("spring.redis.host", redis.getContainerIpAddress());
		System.setProperty("spring.redis.port", redis.getFirstMappedPort().toString());
	}

	@BeforeEach
	void setUp() throws Exception {
		Department department = new Department();
		department.setName("销售部");
		
		Role role = new Role();
		role.setName("saler");
		
		User user = new User();
		user.setName("user");
		user.setCreatedate(new Date(System.currentTimeMillis()));
		user.setDepartment(department);

		List<Role> roles = new ArrayList<>();
		roles.add(role);
		
		user.setRoles(roles);
		
		userRedis.delete(this.getClass().getName() + ":userByname: " + user.getName());
		userRedis.add(this.getClass().getName() + ":userByname: " + user.getName(), 10L, user);
	}

	@Test
	void get() {
		assertTrue(redis.isRunning());
		
		User user = userRedis.get(this.getClass().getName() + ":userByname: user");
		assertNotNull(user);
		logger.info("=====user===== user name: {}, department name: {}, role name: {}",
			user.getName(), user.getDepartment().getName(), user.getRoles().get(0).getName());

	}

}
