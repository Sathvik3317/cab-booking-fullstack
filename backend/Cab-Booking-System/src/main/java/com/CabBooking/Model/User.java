package com.CabBooking.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity //marks the class as a JPA entity so that Hibernate maps it to a database table.
@Table(name ="users") //specify the table name explicitly and avoid default naming conflicts.
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//Automatically generates primary key value
	
	private Long id;
	private String name;
	private String email;
	private String password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
