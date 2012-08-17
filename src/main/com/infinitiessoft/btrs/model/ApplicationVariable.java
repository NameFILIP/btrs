package com.infinitiessoft.btrs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "application_variable")
public class ApplicationVariable implements Serializable {

	private static final long serialVersionUID = -7015421615637457204L;

	private Long id;
	private String name;
	private String value;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ApplicationVariable [name=" + name + ", value=" + value + "]";
	}
	
}
