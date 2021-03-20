/**
 * ToDo.java entity file to map to database table
 * Perform validations on the field
 * @author Sumi.Francis
 *
 */
package com.demo.toDoListApp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ToDo {

	private static final String EMPTY_STRING = "";

	@Id
	@GeneratedValue
	int id;

	@NotEmpty(message = "Name cannot be empty")
	String name;

	@Size(max = 500, message = "Description cannot exceed 500 characters")
	String description;

	@NotEmpty(message = "Status cannot be empty")
	String status;

	@FutureOrPresent
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	LocalDate dueDate;

	public ToDo() {

	}

	public ToDo(String name, String description, String status, LocalDate dueDate) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
		this.dueDate = dueDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = (name != null ? name.trim() : EMPTY_STRING);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

}
