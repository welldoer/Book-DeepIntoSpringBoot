package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

import net.blogjava.welldoer.entity.UserMongo;
import net.blogjava.welldoer.repository.UserMongoRepository;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MongoTest {
	private static Logger logger = LoggerFactory.getLogger(MongoTest.class);
	
	@Autowired
	UserMongoRepository userMongoRepository;

	@ClassRule
	private static final GenericContainer mongo = new GenericContainer<>("mongo").withExposedPorts(27017);

	@BeforeAll
	public static void init() {
		mongo.start();
		
		System.setProperty("spring.data.mongodb.host", mongo.getContainerIpAddress());
		System.setProperty("spring.data.mongodb.port", mongo.getFirstMappedPort().toString());
//		System.setProperty("spring.data.mongodb.name", "test");
	}

	@BeforeEach
	void setUp() throws Exception {
		Set<String> roles = new HashSet<>();
		roles.add("manager");
		UserMongo user = new UserMongo("1", "user", "12345678", "name", "email@com.cn", new Date(System.currentTimeMillis()), roles);
		userMongoRepository.save(user);
	}

	@Test
	void test() {
		List<UserMongo> users = userMongoRepository.findAll();
		assertNotNull(users);
		for(UserMongo user : users) {
			logger.info("=====user===== userId: {}, userName: {}, password: {}, registrationDate: {}",
				user.getUserId(), user.getName(), user.getPassword(), user.getRegistrationDate());
		}
	}

}
