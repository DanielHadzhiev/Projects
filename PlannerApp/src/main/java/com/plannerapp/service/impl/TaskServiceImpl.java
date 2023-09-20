package com.plannerapp.service.impl;

import com.plannerapp.model.dto.AddTaskDTO;
import com.plannerapp.model.dto.AllTasksDTO;
import com.plannerapp.model.dto.TaskDTO;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.model.enums.PriorityNameEnum;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.TaskService;
import com.plannerapp.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private ModelMapper modelMapper;

    private PriorityRepository priorityRepository;

    private LoggedUser loggedUser;

    private UserRepository userRepository;


    @Autowired
    public TaskServiceImpl(ModelMapper modelMapper,
                           TaskRepository taskRepository,
                           PriorityRepository priorityRepository,
                           LoggedUser loggedUser,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }

    public List<String> validateTask(AddTaskDTO addTaskDTO){

        List<String> invalidFields = new ArrayList<>();

        if(addTaskDTO.getDueDate()==null){
            invalidFields.add("dueDate");
        }
       if(addTaskDTO.getPriority().equals("")){
            invalidFields.add("priority");
        }

        return invalidFields;
    }

    public void addTask(AddTaskDTO addTaskDTO){

        Task task = this.modelMapper.map(addTaskDTO, Task.class);

        Priority byPriorityName = this.priorityRepository.findByPriorityName
                (PriorityNameEnum.valueOf(addTaskDTO.getPriority()));

        task.setPriority(byPriorityName);

        this.taskRepository.save(task);
    }

    public AllTasksDTO visualizeUnassignedTasks(){
        Optional<List<Task>> allUnassignedTasks = this.taskRepository.findAllByUserIsNull();

        if(allUnassignedTasks.isEmpty()){
            return null;
        }

        List<TaskDTO>allUnassignedTaskDTOs = new ArrayList<>();

        AllTasksDTO allUnassignedTasksDTO = new AllTasksDTO();

      for (Task task :allUnassignedTasks.get()){

          TaskDTO unassignedTaskDTO = this.modelMapper.map(task, TaskDTO.class);
          unassignedTaskDTO.setPriority(task.getPriority().getPriorityName().name());
          unassignedTaskDTO.setDueDate(task.getDueDate().toString());
allUnassignedTaskDTOs.add(unassignedTaskDTO);
      }
      allUnassignedTasksDTO.setTaskDTOList(allUnassignedTaskDTOs);
      allUnassignedTasksDTO.setAllTasksCount(allUnassignedTaskDTOs.size());

      return allUnassignedTasksDTO;
    }
    public void redirectTaskToUser(String taskId){

        Task byId = this.taskRepository.findById(Long.parseLong(taskId)).get();
        User byUsername = this.userRepository.findByUsername(this.loggedUser.getUsername()).get();

        byId.setUser(byUsername);
        this.taskRepository.save(byId);
    }

    public void deleteTask(String taskId){
        Task byId = this.taskRepository.findById(Long.parseLong(taskId)).get();
        this.taskRepository.delete(byId);
    }
    public void returnTask(String taskId){
        Task byId = this.taskRepository.findById(Long.parseLong(taskId)).get();
        byId.setUser(null);
        this.taskRepository.save(byId);
    }
    public AllTasksDTO visualizeAssignedTasks(){
        Optional<List<Task>> allAssignedTasks = this.taskRepository.findAllByUserId(this.loggedUser.getId());

        if(allAssignedTasks.isEmpty()){
            return null;
        }
        List<TaskDTO>allAssignedTaskDTOs = new ArrayList<>();

        AllTasksDTO allAssignedTasksDTO = new AllTasksDTO();

        for (Task task :allAssignedTasks.get()){

            TaskDTO assignedTaskDTO = this.modelMapper.map(task, TaskDTO.class);
            assignedTaskDTO.setPriority(task.getPriority().getPriorityName().name());
            assignedTaskDTO.setDueDate(task.getDueDate().toString());
            allAssignedTaskDTOs.add(assignedTaskDTO);
        }
        allAssignedTasksDTO.setTaskDTOList(allAssignedTaskDTOs);
        allAssignedTasksDTO.setAllTasksCount(allAssignedTaskDTOs.size());

        return allAssignedTasksDTO;
    }
}
