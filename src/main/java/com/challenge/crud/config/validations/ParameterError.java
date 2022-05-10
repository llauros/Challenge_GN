package com.challenge.crud.config.validations;

public class ParameterError {
	
	private String field;
	private String errorMessage;
	
	public ParameterError(String field, String errorMessage) {
		this.field = field;
		this.errorMessage = errorMessage;
	}

	public String getField() {
		return field;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
