/**
* The RecordNotFoundException.java to handle scenario when data is not found in the database to update or delete
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
	public RecordNotFoundException(String exception) {
		super(exception);
	}
}
