package net.blogjava.welldoer.entity;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "扮演")
public class RoleNeo4j {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@StartNode
	ActorNeo4j actor;
	@EndNode
	MovieNeo4j movie;
	
	public RoleNeo4j(ActorNeo4j actor, MovieNeo4j movie, String name) {
		this.actor = actor;
		this.movie = movie;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ActorNeo4j getActor() {
		return actor;
	}

	public MovieNeo4j getMovie() {
		return movie;
	}
}
