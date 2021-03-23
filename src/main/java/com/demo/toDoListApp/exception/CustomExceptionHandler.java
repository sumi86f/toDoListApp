/**
* The CustomExceptionHandler.java to handle the exceptions during the toDoList operations
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String INVALID_METHOD_ARGUMENT = "Invalid method argument";

	private static final String INTERNAL_SERVER_ERROR = "Internal server error";

	private static final String RECORD_NOT_FOUND = "Record not found in the database";

	private static final String IN_VALID_STATUS = "Invalid status";

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INTERNAL_SERVER_ERROR, details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(RECORD_NOT_FOUND, details);
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InValidStatusException.class)
	public final ResponseEntity<Object> handleInvalidStatusException(InValidStatusException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(IN_VALID_STATUS, details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponse error = new ErrorResponse(INVALID_METHOD_ARGUMENT, details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

}
