/**
 * ToDoListService.java
 * @author Sumi Francis
 */
package com.demo.toDoListApp.service;

import java.util.List;

import com.demo.toDoListApp.model.ToDo;

public interface ToDoListService {

	ToDo addToDo(ToDo toDo);

	List<ToDo> getAllToDo();

	void deleteToDo(int toDo);

	ToDo updateToDo(ToDo toDo);

	List<ToDo> getAllPendingToDo();

	List<ToDo> getAllCompletedToDo();

}
