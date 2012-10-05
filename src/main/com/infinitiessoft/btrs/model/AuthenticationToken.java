package com.infinitiessoft.btrs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.TokenUsername;
import org.jboss.seam.annotations.security.TokenValue;

@Entity
@Table(name = "authentication_token")
public class AuthenticationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tokenId;
	private String username;
	private String value;
	private Date createdDate;

	public AuthenticationToken() {
		createdDate = new Date();
	}
	
	@Id
	@GeneratedValue
	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	@TokenUsername
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@TokenValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
