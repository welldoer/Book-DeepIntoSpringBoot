package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

@DataNeo4jTest
class Neo4jTest {

    @Container
    private static Neo4jContainer neo4jContainer = new Neo4jContainer()
        .withAdminPassword(null); // Disable password
	
	@BeforeAll
	public static void init() {
		neo4jContainer.start();
		
		System.setProperty("spring.data.neo4j.uri", neo4jContainer.getBoltUrl());
	}

    @BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		assertTrue(neo4jContainer.isRunning());
	}

}
