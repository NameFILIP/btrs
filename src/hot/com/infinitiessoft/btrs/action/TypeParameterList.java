package com.infinitiessoft.btrs.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import com.infinitiessoft.btrs.model.TypeParameter;

@Name("typeParameterList")
public class TypeParameterList extends EntityQuery<TypeParameter> {

	private static final long serialVersionUID = -1574733866756174017L;

	private static final String EJBQL = "select typeParameter from TypeParameter typeParameter";

	private static final String[] RESTRICTIONS = {
			"lower(typeParameter.comment) like lower(concat(#{typeParameterList.typeParameter.comment},'%'))",
			"lower(typeParameter.fullName) like lower(concat(#{typeParameterList.typeParameter.fullName},'%'))",};

	private TypeParameter typeParameter = new TypeParameter();

	public TypeParameterList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public TypeParameter getTypeParameter() {
		return typeParameter;
	}
	
}
