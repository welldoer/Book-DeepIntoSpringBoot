package net.blogjava.welldoer;

import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RedisSubPubTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

	@ClassRule
	private static final GenericContainer redis = new GenericContainer<>("redis:5.0.3-alpine").withExposedPorts(6379);
	
	@BeforeAll
	public static void init() {
		redis.start();
		
		System.setProperty("spring.redis.host", redis.getContainerIpAddress());
		System.setProperty("spring.redis.port", redis.getFirstMappedPort().toString());
	}
    
    @Test
    public void test(){
        stringRedisTemplate.convertAndSend("messageQueue", "hello world");
    }
}