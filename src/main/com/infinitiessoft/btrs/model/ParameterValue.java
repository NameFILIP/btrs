package com.infinitiessoft.btrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name="parameters_values", uniqueConstraints={
	    @UniqueConstraint(columnNames={"parameter_id", "expense_id"})})
public class ParameterValue implements java.io.Serializable {
	
	private static final long serialVersionUID = -5542919793378364539L;
	
	private Long id;
	private TypeParameter typeParameter;
	private Expense expense;
	private String value;
	
	
	public ParameterValue() {}
	
	public ParameterValue(TypeParameter typeParameter, Expense expense) {
		this.typeParameter = typeParameter;
		this.expense = expense;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parameter_id")
	public TypeParameter getTypeParameter() {
		return typeParameter;
	}
	
	public void setTypeParameter(TypeParameter typeParameter) {
		this.typeParameter = typeParameter;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id")
	public Expense getExpense() {
		return expense;
	}
	
	public void setExpense(Expense expense) {
		this.expense = expense;
	}
	
	@Column(name = "value", nullable = false, length = 1000)
	@NotNull
	@Length(max = 1000)
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ParameterValue [typeParameter=" + typeParameter + ", value=" + value + "]";
	}
	
	
	
}
