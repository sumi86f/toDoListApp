/**
 * ToDoListControllerIntegrationTest.java to perform end to end component testing
 * @author Sumi.Francis
 */
package com.demo.toDoListApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.demo.toDoListApp.dao.ToDoListDao;
import com.demo.toDoListApp.exception.RecordNotFoundException;
import com.demo.toDoListApp.model.ToDo;
import com.demo.toDoListApp.service.ToDoListService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoListControllerIntegrationTest {

	private static final String DONE = "done";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ToDoListService toDoListService;

	@Autowired
	private ToDoListDao toDoListDao;

	@Autowired
	private ObjectMapper objectMapper;

	private ToDo toDo;

	@BeforeEach
	public void setUp() {
		toDo = createToDo();
	}

	@Test
	void testAddToDo() throws Exception {
		mockMvc.perform(
				post("/todo/add").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isOk());
		ToDo result = toDoListDao.findById(1).get();
		assertEquals(result.getName(), toDo.getName());
	}

	@Test
	void testAddToDoWithInvalidStatus() throws Exception {
		toDo.setStatus(DONE);
		mockMvc.perform(
				post("/todo/add").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertEquals("Status must be pending while creating a todo",
						result.getResolvedException().getMessage()));
	}

	@Test
	void testAddToDoWithEmptyName() throws Exception {
		toDo.setName("");
		mockMvc.perform(
				post("/todo/add").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddToDoWithNullName() throws Exception {
		toDo.setName(null);
		mockMvc.perform(
				post("/todo/add").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testAddToDoWithDescriptionExceedingLimit() throws Exception {
		toDo.setDescription(generateMockDescription());
		MvcResult result = mockMvc.perform(
				post("/todo/add").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	void testgetAllToDo() throws Exception {
		toDoListDao.save(toDo);
		mockMvc.perform(get("/todo/all").contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	public void testDeleteToDo() throws Exception {
		toDoListDao.save(toDo);
		mockMvc.perform(delete("/todo/delete/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	public void testDeleteByIdWhenTodoIsNotFound() throws Exception {
		toDoListDao.save(toDo);
		mockMvc.perform(delete("/todo/delete/{id}", 3)).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
				.andExpect(result -> assertEquals("Record not found for id: 3",
						result.getResolvedException().getMessage()));
	}

	@Test
	public void testUpdateToDo() throws Exception {
		toDoListDao.save(toDo);
		toDo.setStatus(DONE);
		mockMvc.perform(
				put("/todo/update").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateWhenToDoIsNotFound() throws Exception {
		toDoListDao.save(toDo);
		toDo.setId(3);
		mockMvc.perform(
				put("/todo/update").contentType("application/json").content(objectMapper.writeValueAsString(toDo)))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
				.andExpect(result -> assertEquals("Record not found for id: 3",
						result.getResolvedException().getMessage()));
	}

	@Test
	void testgetAllPendingToDo() throws Exception {
		toDoListDao.save(toDo);
		mockMvc.perform(get("/todo/pending").contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	void testgetAllCompletedToDo() throws Exception {
		toDo.setStatus(DONE);
		toDoListDao.save(toDo);
		mockMvc.perform(get("/todo/completed").contentType("application/json")).andExpect(status().isOk());
	}

	private String generateMockDescription() {
		return "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500."
				+ "Mock description to verify character count greater than 500.";
	}

	private ToDo createToDo() {
		ToDo toDo = new ToDo();
		toDo.setId(1);
		toDo.setName("Task 10");
		toDo.setDescription("Task Description 10");
		toDo.setDueDate(LocalDate.now());
		toDo.setStatus("pending");
		return toDo;
	}

}
