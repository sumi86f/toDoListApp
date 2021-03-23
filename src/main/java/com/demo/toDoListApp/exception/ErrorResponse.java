/**
* The ErrorResponse.java to return the response with message and details
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.exception;

import java.util.List;

public class ErrorResponse {
	private String message;
	private List<String> details;

	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getDetails() {
		return details;
	}

}
