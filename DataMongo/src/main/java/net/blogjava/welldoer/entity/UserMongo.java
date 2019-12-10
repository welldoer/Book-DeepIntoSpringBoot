package net.blogjava.welldoer.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserMongo {
	@Id
	private String userId;
	@NotNull @Indexed(unique = true)
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private Date registrationDate = new Date(System.currentTimeMillis());
	private Set<String> roles = new HashSet<>();
	
	@PersistenceConstructor
	public UserMongo(String userId, String username, String password, String name, String email,
			Date registrationDate, Set<String> roles) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.registrationDate = registrationDate;
		this.roles = roles;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public Set<String> getRoles() {
		return roles;
	}
}
