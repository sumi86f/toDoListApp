/**
* The ToDoListDao to handles database activities to
* add, delete, update, getAll, getAllPending and getAllCompleted ToDos
*
* @author  Sumi Francis
* @version 1.0 
*/
package com.demo.toDoListApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.toDoListApp.model.ToDo;

@Repository
public interface ToDoListDao extends JpaRepository<ToDo, Integer> {

}
