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
	private String role;
	@StartNode
	ActorNeo4j actor;
	@EndNode
	MovieNeo4j movie;
	
	public RoleNeo4j(ActorNeo4j actor, MovieNeo4j movie, String role) {
		this.actor = actor;
		this.movie = movie;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public ActorNeo4j getActor() {
		return actor;
	}

	public MovieNeo4j getMovie() {
		return movie;
	}
}
