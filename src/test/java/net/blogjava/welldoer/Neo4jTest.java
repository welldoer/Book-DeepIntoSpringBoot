package net.blogjava.welldoer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;

import net.blogjava.welldoer.entity.ActorNeo4j;
import net.blogjava.welldoer.entity.MovieNeo4j;
import net.blogjava.welldoer.entity.RoleNeo4j;
import net.blogjava.welldoer.repository.MovieNeo4jRepository;

@DataNeo4jTest
class Neo4jTest {
	private static Logger logger = LoggerFactory.getLogger(Neo4jTest.class);
	
	@Autowired
	private MovieNeo4jRepository movieNeo4jRepository;

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
    	movieNeo4jRepository.deleteAll();
    	
    	MovieNeo4j matrix1 = new MovieNeo4j();
    	matrix1.setTitle("The Matrix");
    	matrix1.setYear("1999-03-31");

    	MovieNeo4j matrix2 = new MovieNeo4j();
    	matrix2.setTitle("The Matrix Reloaded");
    	matrix2.setYear("2003-05-07");

    	MovieNeo4j matrix3 = new MovieNeo4j();
    	matrix3.setTitle("The Matrix Revolutions");
    	matrix3.setYear("2003-10-27");

    	ActorNeo4j keanu = new ActorNeo4j();
    	keanu.setName("Keanu Reeves");
    	
    	ActorNeo4j laurence = new ActorNeo4j();
    	laurence.setName("Laurence Fishburne");
    	
    	ActorNeo4j carrieanne = new ActorNeo4j();
    	carrieanne.setName("Carrie-Anne Moss");

    	matrix1.addRole(keanu, "Neo");
    	matrix1.addRole(laurence, "Morpheus");
    	matrix1.addRole(carrieanne, "Trinity");
    	movieNeo4jRepository.save(matrix1);
    	assertNotNull(matrix1.getId());

    	matrix2.addRole(keanu, "Neo");
    	matrix2.addRole(laurence, "Morpheus");
    	matrix2.addRole(carrieanne, "Trinity");
    	movieNeo4jRepository.save(matrix2);
    	assertNotNull(matrix2.getId());

    	matrix3.addRole(keanu, "Neo");
    	matrix3.addRole(laurence, "Morpheus");
    	matrix3.addRole(carrieanne, "Trinity");
    	movieNeo4jRepository.save(matrix3);
    	assertNotNull(matrix3.getId());
    }

	@Test
	void test() {
		assertTrue(neo4jContainer.isRunning());
		
		MovieNeo4j movie = movieNeo4jRepository.findByTitle("The Matrix");
		assertNotNull(movie);
		logger.info("======movie======= movie: {}, {}", movie.getTitle(), movie.getYear());
		for (RoleNeo4j role : movie.getRoles()) {
			logger.info("===== actor: {}, role: {}", role.getActor().getName(), role.getRole());
		}
	}

}
