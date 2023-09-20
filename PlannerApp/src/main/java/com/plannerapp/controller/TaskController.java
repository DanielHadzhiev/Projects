package com.plannerapp.controller;

import com.plannerapp.model.dto.AddTaskDTO;
import com.plannerapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ModelAttribute("addTaskDTO")
    public AddTaskDTO addTaskDTOInit(){
        return new AddTaskDTO();
    }

    @ModelAttribute("dueDateInvalid")
    public boolean dueDateInvalidInit(){
        return  false;
    }

    @ModelAttribute("priorityInvalid")
    public boolean priorityInvalidInit(){
        return false;
    }

    @GetMapping("/add-task")
    public String getAddTask(){
        return "add-task";
    }

    @PostMapping("/add-task")
    public String postAddTask(@Valid AddTaskDTO addTaskDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){

        List<String> invalidFields = this.taskService.validateTask(addTaskDTO);


        if(bindingResult.hasErrors() || !invalidFields.isEmpty()){

            redirectAttributes.addFlashAttribute("addTaskDTO",addTaskDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTaskDTO",
                    bindingResult);

            if(invalidFields.contains("dueDate")){
                redirectAttributes.addFlashAttribute("dueDateInvalid",true);
            }
            if(invalidFields.contains("priority")){
                redirectAttributes.addFlashAttribute("priorityInvalid",true);
            }

            return "redirect:/add-task";
        }


this.taskService.addTask(addTaskDTO);
        return "redirect:/home";
    }
}
