/**
 * ToDoListServiceImplTest.java
 * @author Sumi.Francis
 */
package com.demo.toDoListApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.demo.toDoListApp.dao.ToDoListDao;
import com.demo.toDoListApp.exception.InValidStatusException;
import com.demo.toDoListApp.exception.RecordNotFoundException;
import com.demo.toDoListApp.model.ToDo;

@SpringBootTest
class ToDoListServiceImplTest {

	private static final String INVALID_STATUS = "invalid status";
	private static final String MODIFIED_DESCRIPTION = "Edited description";
	private static final String PENDING = "pending";
	private static final String DONE = "done";

	@TestConfiguration
	static class ToDoListServiceImplTestContextConfiguration {

		@Bean
		public ToDoListService toDoListService() {
			return new ToDoListServiceImpl();
		}
	}

	@Autowired
	private ToDoListService toDoListService;

	@MockBean
	private ToDoListDao toDoListDao;

	private List<ToDo> todos;

	@BeforeEach
	public void setUp() {
		todos = createToDoList();
		toDoListDao.deleteAll();
		todos.forEach(todo -> toDoListDao.save(todo));
	}

	@Test
	public void testAddToDoWithValidData() {
		ToDo toDo = createToDo();
		when(toDoListDao.save(toDo)).thenReturn(toDo);
		assertEquals(toDoListService.addToDo(toDo), toDo);
		verify(toDoListDao, times(1)).save(toDo);
	}

	@Test
	public void testWithInValidStatusThrowsException() {
		ToDo toDo = createToDo();
		String expectedMessage = "Status must be pending while creating a todo";
		toDo.setStatus(INVALID_STATUS);
		InValidStatusException invalidStatusException = assertThrows(InValidStatusException.class, () -> {
			toDoListService.addToDo(toDo);
		});
		String actualMessage = invalidStatusException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(toDoListDao, times(0)).save(toDo);
	}

	@Test
	public void testGetAllToDo() {
		when(toDoListDao.findAll()).thenReturn(todos);
		assertEquals(toDoListService.getAllToDo().size(), todos.size());
		verify(toDoListDao, times(1)).findAll();
	}

	@Test
	public void testGetEmptyResultIfNoExistingData() {
		when(toDoListDao.findAll()).thenReturn(Collections.emptyList());
		assertEquals(toDoListService.getAllToDo().size(), 0);
		verify(toDoListDao, times(1)).findAll();
	}

	@Test
	public void testDeleteTodoByExistingId() {
		ToDo todo = new ToDo();
		int toDoId = 7;
		when(toDoListDao.findById(toDoId)).thenReturn(Optional.of(todo));
		toDoListService.deleteToDo(toDoId);
		verify(toDoListDao, times(1)).deleteById(toDoId);
	}

	@Test
	public void testDeleteByNonExistingIdThrowsException() {
		int toDoId = 8;
		String expectedMessage = "Record not found for id: " + toDoId;
		when(toDoListDao.findById(toDoId)).thenReturn(Optional.empty());
		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			toDoListService.deleteToDo(toDoId);
		});
		String actualMessage = recordNotFoundException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(toDoListDao, times(0)).deleteById(toDoId);
	}

	@Test
	public void testUpdateForExistingToDo() {
		ToDo toDo = createToDo();
		toDo.setDescription(MODIFIED_DESCRIPTION);
		when(toDoListDao.save(toDo)).thenReturn(toDo);
		when(toDoListDao.findById(toDo.getId())).thenReturn(Optional.of(toDo));
		assertEquals(toDoListService.updateToDo(toDo), toDo);
		verify(toDoListDao, times(1)).save(toDo);
	}

	@Test
	public void testUpdateForNonExistingToDoThrowsException() {
		ToDo toDo = createToDo();
		toDo.setDescription(MODIFIED_DESCRIPTION);
		String expectedMessage = "Record not found for id: " + toDo.getId();
		when(toDoListDao.findById(toDo.getId())).thenReturn(Optional.empty());
		RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> {
			toDoListService.updateToDo(toDo);
		});
		String actualMessage = recordNotFoundException.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		verify(toDoListDao, times(0)).save(toDo);
	}

	@Test
	public void testGetAllPendingToDo() {
		long pendingCount = getToDosCountBasedOnStatus(todos, PENDING);
		when(toDoListDao.findAll()).thenReturn(todos);
		assertEquals(toDoListService.getAllPendingToDo().size(), pendingCount);
		verify(toDoListDao, times(1)).findAll();
	}

	@Test
	public void testGetEmptyPendingToDoListIfNoExistingData() {
		long pendingCount = 0;
		when(toDoListDao.findAll()).thenReturn(Collections.emptyList());
		assertEquals(toDoListService.getAllPendingToDo().size(), pendingCount);
		verify(toDoListDao, times(1)).findAll();
	}

	@Test
	public void testGetAllCompletedToDo() {
		long doneCount = getToDosCountBasedOnStatus(todos, DONE);
		when(toDoListDao.findAll()).thenReturn(todos);
		assertEquals(toDoListService.getAllCompletedToDo().size(), doneCount);
		verify(toDoListDao, times(1)).findAll();
	}

	@Test
	public void testGetEmptyCompletedToDoListIfNoExistingData() {
		long doneCount = 0;
		when(toDoListDao.findAll()).thenReturn(Collections.emptyList());
		assertEquals(toDoListService.getAllCompletedToDo().size(), doneCount);
		verify(toDoListDao, times(1)).findAll();
	}

	@AfterEach
	public void cleanUp() throws Exception {
		toDoListDao.deleteAll();
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

	private long getToDosCountBasedOnStatus(List<ToDo> todos, String status) {
		return todos.stream().filter(toDo -> toDo.getStatus().equalsIgnoreCase(status)).count();

	}

}
