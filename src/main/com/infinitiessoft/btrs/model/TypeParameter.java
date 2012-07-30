package com.infinitiessoft.btrs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "type_parameters")
public class TypeParameter implements java.io.Serializable {

	private static final long serialVersionUID = -8024876508245805562L;

	private Long id;
	private String fullName;
	private String shortName;
	private String comment;
	private Set<ExpenseType> expenseTypes = new HashSet<ExpenseType>(0);
	private Set<ParameterValue> parameterValues = new HashSet<ParameterValue>(0);

	public TypeParameter() {
	}

	public TypeParameter(Long id, String fullName) {
		this.id = id;
		this.fullName = fullName;
	}
	public TypeParameter(Long id, String fullName, String shortName, String comment, Set<ExpenseType> expenseTypes) {
		this.id = id;
		this.fullName = fullName;
		this.shortName = shortName;
		this.comment = comment;
		this.expenseTypes = expenseTypes;
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

	@Column(name = "full_name", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Column(name = "short_name", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(length = 4000)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "typeParameters")
	public Set<ExpenseType> getExpenseTypes() {
		return this.expenseTypes;
	}

	public void setExpenseTypes(Set<ExpenseType> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "typeParameter")
	public Set<ParameterValue> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(Set<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}
	
}
