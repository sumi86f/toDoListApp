/**
* The ToDoListService provides business logic to handle functionalities to
* add, delete, update, getAll, getAllPending and getAllCompleted ToDos
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.toDoListApp.dao.ToDoListDao;
import com.demo.toDoListApp.exception.InValidStatusException;
import com.demo.toDoListApp.exception.RecordNotFoundException;
import com.demo.toDoListApp.model.ToDo;

@Service
public class ToDoListServiceImpl implements ToDoListService {

	private static final String DONE_TODO = "done";
	private static final String PENDING_TODO = "pending";

	@Autowired
	ToDoListDao toDoListDao;

	/**
	 * Returns added ToDo object on successful save Throws InvalidStatusException if
	 * the status of ToDo object is not 'pending'
	 * 
	 * @Param toDo the ToDo object that need to be saved to the database
	 * @return the added toDo object
	 */
	@Override
	public ToDo addToDo(ToDo toDo) {
		if (!toDo.getStatus().equalsIgnoreCase(PENDING_TODO)) {
			throw new InValidStatusException("Status must be pending while creating a todo");
		}
		return toDoListDao.save(toDo);
	}

	/**
	 * Returns all ToDo object from the database
	 * 
	 * @return the list of toDo object
	 */
	@Override
	public List<ToDo> getAllToDo() {
		return toDoListDao.findAll().stream().collect(Collectors.toList());
	}

	/**
	 * Returns void after deleting the ToDo object for the given id Throws
	 * RecordNotFoundException if the given id is not present in the database
	 * 
	 * @Param id the identifier of ToDo object to identify and delete
	 */
	@Override
	public void deleteToDo(int toDoId) {
		if (!toDoListDao.findById(toDoId).isPresent()) {
			throw new RecordNotFoundException("Record not found for id: " + toDoId);
		}
		toDoListDao.deleteById(toDoId);
	}

	/**
	 * Returns updated ToDo object that can be displayed on the view Throws
	 * RecordNotFoundException if the provided ToDo object is not present in the
	 * database
	 * 
	 * @Param toDo the ToDo object that need to be updated to the database
	 * @return the updated toDo object
	 */
	@Override
	public ToDo updateToDo(ToDo toDo) {
		if (!toDoListDao.findById(toDo.getId()).isPresent()) {
			throw new RecordNotFoundException("Record not found for id: " + toDo.getId());
		}
		return toDoListDao.save(toDo);
	}

	/**
	 * Returns all pending ToDo object from the database that can be displayed on
	 * the view or [] if no data in the database
	 * 
	 * @return the list of pending toDo object
	 */
	@Override
	public List<ToDo> getAllPendingToDo() {
		return toDoListDao.findAll().stream().filter(todo -> todo.getStatus().equalsIgnoreCase(PENDING_TODO))
				.collect(Collectors.toList());
	}

	/**
	 * Returns all completed ToDo object from the database that can be displayed on
	 * the view or [] if no data in the database
	 * 
	 * @return the list of completed toDo object
	 */
	@Override
	public List<ToDo> getAllCompletedToDo() {
		return toDoListDao.findAll().stream().filter(todo -> todo.getStatus().equalsIgnoreCase(DONE_TODO))
				.collect(Collectors.toList());
	}

}
