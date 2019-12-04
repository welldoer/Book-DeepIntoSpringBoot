package net.blogjava.welldoer.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import net.blogjava.welldoer.entity.MovieNeo4j;

public interface MovieNeo4jRepository extends Neo4jRepository<MovieNeo4j, Long> {
	MovieNeo4j findByName(@Param("name") String name);
}
