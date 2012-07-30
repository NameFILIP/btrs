package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Statuses generated by hbm2java
 */
@Entity
@Table(name = "statuses", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Status implements java.io.Serializable {

	private static final long serialVersionUID = 7996368953528715756L;
	
	private Long id;
	private String name;
	private String comment;
	private Set<StatusChange> statusChanges = new HashSet<StatusChange>(0);

	public Status() {
	}

	public Status(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Status(Long id, String name, Set<StatusChange> statusChanges) {
		this.id = id;
		this.name = name;
		this.statusChanges = statusChanges;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false, length = 100)
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
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
	public Set<StatusChange> getStatusChanges() {
		return this.statusChanges;
	}

	public void setStatusChanges(Set<StatusChange> statusChanges) {
		this.statusChanges = statusChanges;
	}

}
