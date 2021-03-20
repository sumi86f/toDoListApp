/**
 * ToDoListControllerTest.java
 * @author Sumi.Francis
 */
package com.demo.toDoListApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.toDoListApp.model.ToDo;
import com.demo.toDoListApp.service.ToDoListService;

@SpringBootTest
class ToDoListControllerTest {

	private static final String MODIFIED_DESCRIPTION = "Edited description";

	@Autowired
	private ToDoListController toDoListController;

	@MockBean
	private ToDoListService toDoListService;

	private List<ToDo> toDos;

	@BeforeEach
	public void setUp() {
		toDos = createToDoList();
	}

	@Test
	public void testAddToDo() {
		ToDo toDo = createToDo();
		when(toDoListService.addToDo(toDo)).thenReturn(toDo);
		ResponseEntity<ToDo> responseEntity = toDoListController.addTodo(toDo);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
		assertEquals(responseEntity.getBody(), toDo);
		verify(toDoListService, times(1)).addToDo(toDo);

	}

	@Test
	public void testGetAllToDo() {
		when(toDoListService.getAllToDo()).thenReturn(toDos);
		ResponseEntity<List<ToDo>> responseEntity = toDoListController.getAllTodo();
		assertEquals(responseEntity.getStatusCodeValue(), 200);
		assertEquals(responseEntity.getBody(), toDos);
		verify(toDoListService, times(1)).getAllToDo();
	}

	@Test
	public void testDeleteToDoById() {
		int toDoId = 4;
		ResponseEntity<Void> responseEntity = toDoListController.deleteToDo(toDoId);
		verify(toDoListService, times(1)).deleteToDo(toDoId);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	public void testUpdateToDo() {
		ToDo toDo = createToDo();
		toDo.setDescription(MODIFIED_DESCRIPTION);
		when(toDoListService.updateToDo(toDo)).thenReturn(toDo);
		ResponseEntity<ToDo> responseEntity = toDoListController.updateToDo(toDo);
		verify(toDoListService, times(1)).updateToDo(toDo);
		assertEquals(responseEntity.getStatusCodeValue(), 200);
	}

	@Test
	public void testGetAllPendingToDo() {
		when(toDoListService.getAllPendingToDo()).thenReturn(toDos);
		ResponseEntity<List<ToDo>> responseEntity = toDoListController.getAllPendingToDo();
		assertEquals(responseEntity.getStatusCodeValue(), 200);
		assertEquals(responseEntity.getBody(), toDos);
		verify(toDoListService, times(1)).getAllPendingToDo();
	}

	@Test
	public void testGetAllCompletedToDo() {
		when(toDoListService.getAllCompletedToDo()).thenReturn(toDos);
		ResponseEntity<List<ToDo>> responseEntity = toDoListController.getAllCompletedToDo();
		assertEquals(responseEntity.getStatusCodeValue(), 200);
		assertEquals(responseEntity.getBody(), toDos);
		verify(toDoListService, times(1)).getAllCompletedToDo();
	}

	private List<ToDo> createToDoList() {
		List<ToDo> todos = Arrays.asList(new ToDo("Task 1", "Task Description 1", "pending", LocalDate.now()),
				new ToDo("Task 2", "Task Description 2", "pending", LocalDate.now()),
				new ToDo("Task 3", "Task Description 3", "done", LocalDate.now()),
				new ToDo("Task 4", "Task Description 4", "pending", LocalDate.now()),
				new ToDo("Task 5", "Task Description 5", "pending", LocalDate.now()),
				new ToDo("Task 6", "Task Description 6", "done", LocalDate.now()),
				new ToDo("Task 7", "Task Description 7", "done", LocalDate.now()));
		return todos;
	}

	private ToDo createToDo() {
		ToDo toDo = new ToDo();
		toDo.setName("Task 10");
		toDo.setDescription("Task Description 10");
		toDo.setDueDate(LocalDate.now());
		toDo.setStatus("pending");
		return toDo;
	}

}
