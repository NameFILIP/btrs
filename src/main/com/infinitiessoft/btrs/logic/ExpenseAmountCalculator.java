package com.infinitiessoft.btrs.logic;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.infinitiessoft.btrs.enums.ExpenseTypeEnum;
import com.infinitiessoft.btrs.enums.HighSpeedRailEnum;
import com.infinitiessoft.btrs.enums.ParameterEnum;
import com.infinitiessoft.btrs.model.Expense;
import com.infinitiessoft.btrs.model.ParameterValue;

@Name("expenseAmountCalculator")
public class ExpenseAmountCalculator {
	
	@In(create = true)
	HighSpeedRailPrices highSpeedRailPrices;
	
	public void calculateTaxAndAmount(Expense expense) {
		Integer amount = expense.getTotalAmount();
		
		if (ExpenseTypeEnum.TRANSP_HSR_RECEIPT.equals(expense.getExpenseType().getValue())) {
			String sourceValue = expense.getParameterValue(ParameterEnum.SOURCE).getValue();
			String destinationValue = expense.getParameterValue(ParameterEnum.DESTINATION).getValue();
			
			String ticketsValue = expense.getParameterValue(ParameterEnum.TICKETS).getValue();
			Integer ticketsValueSafe = (ticketsValue == null || ticketsValue.isEmpty()) ? 1 : Integer.valueOf(ticketsValue);
			
			HighSpeedRailEnum source = HighSpeedRailEnum.valueOf(sourceValue);
			HighSpeedRailEnum destination = HighSpeedRailEnum.valueOf(destinationValue);
			Integer price = highSpeedRailPrices.getNonReservedPrice(source, destination);
			
			amount = price * ticketsValueSafe;
			
		} else if (ExpenseTypeEnum.TRANSP_CAR.equals(expense.getExpenseType().getValue())) {
			ParameterValue freewayToll = expense.getParameterValue(ParameterEnum.FREEWAY_TOLL);
			amount += Integer.valueOf(freewayToll.getValue());
			
		}
		
		Integer taxPercent = expense.getExpenseType().getTaxPercent();
		Integer taxAmount = calculateTax(amount, taxPercent);
		
		expense.setTotalAmount(amount);
		expense.setTaxAmount(taxAmount);
	}
	
	public Integer calculateTax(Integer amount, Integer taxPercent) {
		if (taxPercent > 0) {
			double taxCoefficient = 100 / (100.0 + taxPercent);
			Double amountWithoutTax = amount * taxCoefficient;
			Integer amountWithoutTaxRoundedDown = amountWithoutTax.intValue();
			return amount - amountWithoutTaxRoundedDown;
		} else {
			return 0;
		}
	}

}
