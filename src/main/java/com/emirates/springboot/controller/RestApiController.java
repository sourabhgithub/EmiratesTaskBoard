package com.emirates.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.emirates.springboot.model.Task;
import com.emirates.springboot.service.TaskService;
import com.emirates.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	TaskService taskService; // Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Tasks---------------------------------------------

	@RequestMapping(value = "/task/", method = RequestMethod.GET)
	public ResponseEntity<List<Task>> listAllTasks() {
		List<Task> tasks = taskService.findAllTasks();
		if (tasks.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Task------------------------------------------

	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTask(@PathVariable("id") long id) {
		logger.info("Fetching Task with id {}", id);
		Task task = taskService.findById(id);
		if (task == null) {
			logger.error("Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Task with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}

	// -------------------Create a Task-------------------------------------------

	@RequestMapping(value = "/task/", method = RequestMethod.POST)
	public ResponseEntity<?> createTask(@RequestBody Task task, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Task : {}", task);

		if (taskService.isTaskExist(task)) {
			logger.error("Unable to create. A Task with name {} already exist", task.getName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A Task with name " + task.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		taskService.saveTask(task);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/task/{id}").buildAndExpand(task.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Task
	// ------------------------------------------------

	@RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
		logger.info("Updating Task with id {}", id);

		Task currentTask = taskService.findById(id);

		if (currentTask == null) {
			logger.error("Unable to update. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentTask.setName(task.getName());
		currentTask.setEstimatedHours(task.getEstimatedHours());
		currentTask.setActualHours(task.getActualHours());

		taskService.updateTask(currentTask);
		return new ResponseEntity<Task>(currentTask, HttpStatus.OK);
	}

	// ------------------- Delete a Task-----------------------------------------

	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTask(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Task with id {}", id);

		Task task = taskService.findById(id);
		if (task == null) {
			logger.error("Unable to delete. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		taskService.deleteTaskById(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Tasks-----------------------------

	@RequestMapping(value = "/task/", method = RequestMethod.DELETE)
	public ResponseEntity<Task> deleteAllTasks() {
		logger.info("Deleting All Tasks");

		taskService.deleteAllTasks();
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}

}