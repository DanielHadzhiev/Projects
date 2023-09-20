package com.plannerapp.controller;

import com.plannerapp.model.dto.AllTasksDTO;
import com.plannerapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private TaskService taskService;

    @Autowired
    public HomeController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ModelAttribute("allUnassignedTasksDTO")
    public AllTasksDTO AllUnassignedTasksDTOInit(){
        return new AllTasksDTO();
    }
    @ModelAttribute("allAssignedTasksDTO")
    public AllTasksDTO AllAssignedTasksDTO(){
        return  new AllTasksDTO();
    }

    @GetMapping("/home")
    public String getHome(Model model){

        AllTasksDTO allAssignedTasksDTO = this.taskService.visualizeAssignedTasks();

        AllTasksDTO allUnassignedTasksDTO = this.taskService.visualizeUnassignedTasks();

        model.addAttribute("allUnassignedTasksDTO",allUnassignedTasksDTO);
        model.addAttribute("allAssignedTasksDTO",allAssignedTasksDTO);

        return "home";
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }


    @GetMapping("/home/assignTask/{taskIdToAssign}")
    public String assignTask(@PathVariable String taskIdToAssign){

        this.taskService.redirectTaskToUser(taskIdToAssign);

        return "redirect:/home";
    }

    @GetMapping("/home/removeTask/{taskIdToRemove}")
    public String removeTask(@PathVariable String taskIdToRemove){
        this.taskService.deleteTask(taskIdToRemove);
        return "redirect:/home";
    }
    @GetMapping("/home/returnTask/{taskIdToReturn}")
    public String returnTask(@PathVariable String taskIdToReturn){
        this.taskService.returnTask(taskIdToReturn);
        return "redirect:/home";
    }
}
