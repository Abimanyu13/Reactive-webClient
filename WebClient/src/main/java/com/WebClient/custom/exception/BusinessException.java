package com.WebClient.custom.exception;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Component
@Data
public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	
	public BusinessException(String errorCode, String errorMessage) {
		super("API::" +errorCode + "::" + errorMessage );
	}
	
	public BusinessException(String errorMessage) {
		super("unexpected::" +errorMessage);
	}
	
	public BusinessException() {
		super();
	}
	
}
