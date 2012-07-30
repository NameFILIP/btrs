package com.infinitiessoft.btrs.action;

import org.drools.util.StringUtils;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;
import com.infinitiessoft.btrs.enums.ParameterEnum;
import com.infinitiessoft.btrs.logic.HighSpeedRailPrices;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ParameterValue;

@Name("expenseAmountCalculator")
public class ExpenseAmountCalculator {
	
	// injected in components.xml
	private Integer taxPercent;
	
	@In
	private ExpenseHome expenseHome;
	
	@In(create = true)
	HighSpeedRailPrices highSpeedRailPrices;
	
	public void calculateTaxAndAmount() {
		Expense expense = expenseHome.getInstance();
		if (ExpenseTypeEnum.HSR.getLabel().equals(expense.getExpenseType().getShortName())) {
			String sourceValue = expense.getParamValueByTypeName(ParameterEnum.SOURCE.getLabel()).getValue();
			String destinationValue = expense.getParamValueByTypeName(ParameterEnum.DESTINATION.getLabel()).getValue();
			
			String ticketsValue = expense.getParamValueByTypeName(ParameterEnum.TICKETS.getLabel()).getValue();
			Integer ticketsValueSafe = StringUtils.isEmpty(ticketsValue) ? 1 : Integer.valueOf(ticketsValue);
			
			HighSpeedRailEnum source = HighSpeedRailEnum.valueOf(sourceValue);
			HighSpeedRailEnum destination = HighSpeedRailEnum.valueOf(destinationValue);
			Integer price = highSpeedRailPrices.getNonReservedPrice(source, destination);
			
			Integer amountValue = price * ticketsValueSafe;
			
			ParameterValue amount = expense.getParamValueByTypeName(ParameterEnum.AMOUNT.getLabel());
			amount.setValue(amountValue.toString());
			
//		} else if (ExpenseTypeEnum.CAR.getLabel().equals(expense.getExpenseType().getShortName())) {
			
//			expenseHome.get
		} else {
			ParameterValue tax = expense.getParamValueByTypeName(ParameterEnum.TAX.getLabel());
			if (tax != null) {
				ParameterValue amount = expense.getParamValueByTypeName(ParameterEnum.AMOUNT.getLabel());
				Integer amountInt = Integer.valueOf(amount.getValue());
				Integer taxValue = ((Double) (amountInt * taxPercent / (100.0 + taxPercent))).intValue();
				tax.setValue(taxValue.toString());
			}
		}
	}

	public Integer getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Integer taxPercent) {
		this.taxPercent = taxPercent;
	}
}
