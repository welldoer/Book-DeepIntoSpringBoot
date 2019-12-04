package net.blogjava.welldoer.entity;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class MovieNeo4j {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String year;
	private String tagline;
	
	@Relationship(type = "ACTS_IN", direction = Relationship.INCOMING)
	List<RoleNeo4j> roles = new ArrayList<>();

	public RoleNeo4j addRole(ActorNeo4j actor, String roleName) {
		RoleNeo4j role = new RoleNeo4j(actor, this, roleName);
		this.roles.add(role);
		return role;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public List<RoleNeo4j> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleNeo4j> roles) {
		this.roles = roles;
	}
}
