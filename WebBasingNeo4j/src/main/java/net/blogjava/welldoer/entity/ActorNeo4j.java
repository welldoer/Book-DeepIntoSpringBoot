package net.blogjava.welldoer.entity;

import java.util.Date;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

@NodeEntity
public class ActorNeo4j {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int sex;
	@DateLong
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date born;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public Date getBorn() {
		return born;
	}

	public void setBorn(Date born) {
		this.born = born;
	}
}
