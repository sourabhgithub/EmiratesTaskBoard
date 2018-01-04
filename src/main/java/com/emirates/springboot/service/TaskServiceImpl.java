package com.emirates.springboot.service;

import java.util.List;

import com.emirates.springboot.model.Task;
import com.emirates.springboot.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskRepository taskRepository;

	public Task findById(Long id) {
		return taskRepository.findOne(id);
	}

	public Task findByName(String name) {
		return taskRepository.findByName(name);
	}

	public void saveTask(Task task) {
		taskRepository.save(task);
	}

	public void updateTask(Task task){
		saveTask(task);
	}

	public void deleteTaskById(Long id){
		taskRepository.delete(id);
	}

	public void deleteAllTasks(){
		taskRepository.deleteAll();
	}

	public List<Task> findAllTasks(){
		return taskRepository.findAll();
	}

	public boolean isTaskExist(Task task) {
		return findByName(task.getName()) != null;
	}

}
