package com.infinitiessoft.btrs.model;
// Generated Jul 9, 2012 10:51:06 AM by Hibernate Tools 3.2.4.GA


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Digits;
import org.hibernate.validator.NotNull;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;

/**
 * ExpenseTypes generated by hbm2java
 */
@Entity
@Table(name = "expense_types")
public class ExpenseType implements java.io.Serializable {

	private static final long serialVersionUID = -7100716660369786371L;
	
	private Long id;
	private List<ExpenseCategory> expenseCategories;
	private ExpenseTypeEnum value;
	private Double taxPercent;
	private Set<Expense> expenses;
	private List<TypeParameter> typeParameters;

	
	public ExpenseType() {
		this.taxPercent = 0.0;
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

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "exp_category_exp_type",
		joinColumns = @JoinColumn(name = "type_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	public List<ExpenseCategory> getExpenseCategories() {
		if (expenseCategories == null) {
			expenseCategories = new ArrayList<ExpenseCategory>(0);
		}
		return this.expenseCategories;
	}

	public void setExpenseCategories(List<ExpenseCategory> expenseCategories) {
		this.expenseCategories = expenseCategories;
	}

	@Transient
	public String getNameKey() {
		return this.value.getLabel();
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "value", nullable = false)
	@NotNull
	public ExpenseTypeEnum getValue() {
		return this.value;
	}

	public void setValue(ExpenseTypeEnum value) {
		this.value = value;
	}
	
	@Digits(integerDigits = 2, fractionalDigits = 4)
	@Column(name = "tax_percent")
	public Double getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Double taxPercent) {
		this.taxPercent = taxPercent;
	}

	@OneToMany(mappedBy = "expenseType", cascade = CascadeType.ALL)
	public Set<Expense> getExpenses() {
		if (expenses == null) {
			expenses = new HashSet<Expense>(0);
		}
		return this.expenses;
	}

	public void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}
	
	@OrderBy("value")
	@ManyToMany
	@JoinTable(name = "exp_types_type_parameters",
		joinColumns = @JoinColumn(name = "type_id"),
		inverseJoinColumns = @JoinColumn(name = "parameter_id"))
	public List<TypeParameter> getTypeParameters() {
		if (typeParameters == null) {
			typeParameters = new ArrayList<TypeParameter>(0);
		}
		return typeParameters;
	}

	public void setTypeParameters(List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
	}

	@Override
	public String toString() {
		return "ExpenseType [id=" + id + ", expenseCategories=" + expenseCategories + ", value=" + value + ", taxPercent="
				+ taxPercent + ", typeParameters=" + typeParameters + "]";
	}
	
	

}
