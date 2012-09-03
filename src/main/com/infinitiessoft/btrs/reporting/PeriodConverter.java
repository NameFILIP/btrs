package com.infinitiessoft.btrs.reporting;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Name("periodConverter")
@BypassInterceptors
@Converter
public class PeriodConverter implements javax.faces.convert.Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value == null || value.trim().length() == 0)
			return null;
		Period period = null;
		try {
			String[] values = value.split(",");
			Integer year = Integer.valueOf(values[0]);
			Integer quarter = null;
			Integer month = null;
			if (values.length > 1) {
				quarter = Integer.valueOf(values[1]);
			}
			if (values.length > 2) {
				month = Integer.valueOf(values[2]);
			}
			period = new Period(year, quarter, month);
//			((javax.faces.component.EditableValueHolder)component).setLocalValueSet(true);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage();
			message.setDetail("Invalid period");
			message.setSummary("The period has to be valid");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(message);
		}
		return period;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		Period period = (Period) value;
		
		Integer year = period.getYear();
		Integer quarter = period.getQuarter();
		Integer month = period.getMonth();
		
		String result = year + (quarter == null ? "" : "," + quarter) + (month == null ? "" : "," + month);
				
		return  result;
	}
}
