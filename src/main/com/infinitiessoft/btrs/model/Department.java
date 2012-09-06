package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Departments generated by hbm2java
 */
@Entity
@Table(name = "departments")
public class Department implements java.io.Serializable {

	private static final long serialVersionUID = -746932327914798697L;
	
	private Long id;
	private String name;
	private String comment;
	private Set<User> users;


	public Department() {}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 4000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToMany(mappedBy = "department")
	public Set<User> getUsers() {
		if (users == null) {
			users = new HashSet<User>(0);
		}
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
