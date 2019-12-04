package net.blogjava.welldoer.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import net.blogjava.welldoer.entity.ActorNeo4j;

public interface ActorNeo4jRepository extends Neo4jRepository<ActorNeo4j, Long> {

}
