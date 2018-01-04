package com.emirates.springboot.service;


import com.emirates.springboot.model.Task;

import java.util.List;

public interface TaskService {
	
	Task findById(Long id);

	Task findByName(String name);

	void saveTask(Task task);

	void updateTask(Task task);

	void deleteTaskById(Long id);

	void deleteAllTasks();

	List<Task> findAllTasks();

	boolean isTaskExist(Task task);
}