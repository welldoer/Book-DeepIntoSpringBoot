package net.blogjava.welldoer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

@NodeEntity
public class MovieNeo4j {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String photo;
	@DateLong
	private Date createDate;
	
	@Relationship(type = "扮演", direction = Relationship.INCOMING)
	List<RoleNeo4j> roles = new ArrayList<>();

	public RoleNeo4j addRole(ActorNeo4j actor, String roleName) {
		RoleNeo4j role = new RoleNeo4j(actor, this, roleName);
		this.roles.add(role);
		return role;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<RoleNeo4j> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleNeo4j> roles) {
		this.roles = roles;
	}
}
