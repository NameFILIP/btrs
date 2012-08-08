package com.infinitiessoft.btrs.custom;

import org.drools.util.StringUtils;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.action.ExpenseHome;
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
		if (ExpenseTypeEnum.HSR.equals(expense.getExpenseType().getValue())) {
			String sourceValue = expense.getParameterValue(ParameterEnum.SOURCE).getValue();
			String destinationValue = expense.getParameterValue(ParameterEnum.DESTINATION).getValue();
			
			String ticketsValue = expense.getParameterValue(ParameterEnum.TICKETS).getValue();
			Integer ticketsValueSafe = StringUtils.isEmpty(ticketsValue) ? 1 : Integer.valueOf(ticketsValue);
			
			HighSpeedRailEnum source = HighSpeedRailEnum.valueOf(sourceValue);
			HighSpeedRailEnum destination = HighSpeedRailEnum.valueOf(destinationValue);
			Integer price = highSpeedRailPrices.getNonReservedPrice(source, destination);
			
			Integer amountValue = price * ticketsValueSafe;
			
			ParameterValue amount = expense.getParameterValue(ParameterEnum.AMOUNT);
			amount.setValue(amountValue.toString());
			
//		} else if (ExpenseTypeEnum.CAR.getLabel().equals(expense.getExpenseType().getShortName())) {
			
//			expenseHome.get
		} else {
			ParameterValue tax = expense.getParameterValue(ParameterEnum.TAX);
			if (tax != null) {
				ParameterValue amount = expense.getParameterValue(ParameterEnum.AMOUNT);
				Integer amountInt = Integer.valueOf(amount.getValue());
				
				double taxCoefficient = 100 / (100.0 + taxPercent);
				Double amountWithoutTax = amountInt * taxCoefficient;
				Integer amountWithoutTaxRoundedDown = amountWithoutTax.intValue();
				Integer taxValue = amountInt - amountWithoutTaxRoundedDown;
				
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
