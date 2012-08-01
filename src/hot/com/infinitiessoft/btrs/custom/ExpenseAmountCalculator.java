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
		if (ExpenseTypeEnum.HSR.name().equals(expense.getExpenseType().getCode())) {
			String sourceValue = expense.getParamValueByCode(ParameterEnum.SOURCE).getValue();
			String destinationValue = expense.getParamValueByCode(ParameterEnum.DESTINATION).getValue();
			
			String ticketsValue = expense.getParamValueByCode(ParameterEnum.TICKETS).getValue();
			Integer ticketsValueSafe = StringUtils.isEmpty(ticketsValue) ? 1 : Integer.valueOf(ticketsValue);
			
			HighSpeedRailEnum source = HighSpeedRailEnum.valueOf(sourceValue);
			HighSpeedRailEnum destination = HighSpeedRailEnum.valueOf(destinationValue);
			Integer price = highSpeedRailPrices.getNonReservedPrice(source, destination);
			
			Integer amountValue = price * ticketsValueSafe;
			
			ParameterValue amount = expense.getParamValueByCode(ParameterEnum.AMOUNT);
			amount.setValue(amountValue.toString());
			
//		} else if (ExpenseTypeEnum.CAR.getLabel().equals(expense.getExpenseType().getShortName())) {
			
//			expenseHome.get
		} else {
			ParameterValue tax = expense.getParamValueByCode(ParameterEnum.TAX);
			if (tax != null) {
				ParameterValue amount = expense.getParamValueByCode(ParameterEnum.AMOUNT);
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
