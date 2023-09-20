package com.plannerapp.service;

import com.plannerapp.model.dto.AddTaskDTO;
import com.plannerapp.model.dto.AllTasksDTO;
import com.plannerapp.model.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    void addTask(AddTaskDTO addTaskDTO);

    List<String>validateTask(AddTaskDTO addTaskDTO);

    AllTasksDTO visualizeUnassignedTasks();

    void redirectTaskToUser(String taskId);

    void deleteTask(String taskId);

    void returnTask(String taskId);

    AllTasksDTO visualizeAssignedTasks();
}
