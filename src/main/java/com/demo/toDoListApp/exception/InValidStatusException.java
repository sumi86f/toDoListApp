/**
* The InvalidStatusException to handle invalid entry of toDo
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InValidStatusException extends RuntimeException {
	public InValidStatusException(String exception) {
		super(exception);
	}
}
