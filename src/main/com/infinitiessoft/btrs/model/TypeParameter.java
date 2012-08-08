package com.infinitiessoft.btrs.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

import com.infinitiessoft.btrs.enums.ParameterEnum;

@Entity
@Table(name = "type_parameters")
public class TypeParameter implements java.io.Serializable {

	private static final long serialVersionUID = -8024876508245805562L;

	private Long id;
	private ParameterEnum value;
	private Set<ExpenseType> expenseTypes;
	private Set<ParameterValue> parameterValues;


	public TypeParameter() {}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public String getNameKey() {
		return this.value.getLabel();
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "value", nullable = false)
	@NotNull
	public ParameterEnum getValue() {
		return this.value;
	}

	public void setValue(ParameterEnum value) {
		this.value = value;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "typeParameters")
	public Set<ExpenseType> getExpenseTypes() {
		if (expenseTypes == null) {
			expenseTypes = new HashSet<ExpenseType>(0);
		}
		return this.expenseTypes;
	}

	public void setExpenseTypes(Set<ExpenseType> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "typeParameter")
	public Set<ParameterValue> getParameterValues() {
		if (parameterValues == null) {
			parameterValues = new HashSet<ParameterValue>(0);
		}
		return parameterValues;
	}

	public void setParameterValues(Set<ParameterValue> parameterValues) {
		this.parameterValues = parameterValues;
	}
	
}
