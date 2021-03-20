/**
* The TodoListController exposes and handles the rest end points to
* add, delete, update, getAll, getAllPending and getAllCompleted ToDos
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.toDoListApp.model.ToDo;
import com.demo.toDoListApp.service.ToDoListService;

@RestController
@RequestMapping("/todo")
public class ToDoListController {

	@Autowired
	ToDoListService toDoListService;

	public ToDoListController(ToDoListService toDoListService) {
		this.toDoListService = toDoListService;
	}

	/**
	 * Returns added ToDo object that can be displayed on the view
	 * 
	 * @Param toDo the ToDo object that need to be saved to the database
	 * @return the added toDo object
	 */
	@PostMapping(value = "/add")
	public ResponseEntity<ToDo> addTodo(@Valid @RequestBody ToDo toDo) {
		toDoListService.addToDo(toDo);
		return new ResponseEntity<ToDo>(toDo, HttpStatus.OK);
	}

	/**
	 * Returns all ToDo object from the database that can be displayed on the view
	 * 
	 * @return the list of toDo object
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<ToDo>> getAllTodo() {
		List<ToDo> todoList = toDoListService.getAllToDo();
		return new ResponseEntity<List<ToDo>>(todoList, HttpStatus.OK);
	}

	/**
	 * Returns void after deleting the ToDo object for the given id
	 * 
	 * @Param id the identifier of ToDo object to identify and delete
	 */
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteToDo(@PathVariable int id) {
		toDoListService.deleteToDo(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Returns updated ToDo object that can be displayed on the view
	 * 
	 * @Param toDo the ToDo object that need to be updated to the database
	 * @return the updated toDo object
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<ToDo> updateToDo(@Valid @RequestBody ToDo toDo) {
		toDoListService.updateToDo(toDo);
		return new ResponseEntity<ToDo>(toDo, HttpStatus.OK);
	}

	/**
	 * Returns all pending ToDo object from the database that can be displayed on
	 * the view
	 * 
	 * @return the list of pending toDo object
	 */
	@GetMapping(value = "/pending")
	public ResponseEntity<List<ToDo>> getAllPendingToDo() {
		List<ToDo> todoList = toDoListService.getAllPendingToDo();
		return new ResponseEntity<List<ToDo>>(todoList, HttpStatus.OK);
	}

	/**
	 * Returns all completed ToDo object from the database that can be displayed on
	 * the view
	 * 
	 * @return the list of completed toDo object
	 */
	@GetMapping(value = "/completed")
	public ResponseEntity<List<ToDo>> getAllCompletedToDo() {
		List<ToDo> todoList = toDoListService.getAllCompletedToDo();
		return new ResponseEntity<List<ToDo>>(todoList, HttpStatus.OK);
	}

}
