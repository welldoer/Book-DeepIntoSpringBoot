package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class MyTestcontainersTests {

	// will be shared between test methods
	@Container
	private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer();

	// will be started before and stopped after each test method
	@Container
	private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
			.withDatabaseName("foo")
			.withUsername("foo")
			.withPassword("secret");

	@BeforeAll
	public static void init() {
		System.setProperty("spring.datasource.url", MY_SQL_CONTAINER.getJdbcUrl());
		System.setProperty("spring.datasource.driver-class-name", MY_SQL_CONTAINER.getDriverClassName());
		System.setProperty("spring.datasource.username", MY_SQL_CONTAINER.getUsername());
		System.setProperty("spring.datasource.password", MY_SQL_CONTAINER.getPassword());
	}

	@Test
	void test() {
		assertTrue(MY_SQL_CONTAINER.isRunning());
		assertTrue(postgresqlContainer.isRunning());
	}
}