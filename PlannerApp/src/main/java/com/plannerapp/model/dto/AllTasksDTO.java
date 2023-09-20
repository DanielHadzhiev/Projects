package com.plannerapp.model.dto;

import java.util.List;

public class AllTasksDTO {

    private int allTasksCount;

private List<TaskDTO>taskDTOList;

    public int getAllTasksCount() {
        return allTasksCount;
    }

    public void setAllTasksCount(int allTasksCount) {
        this.allTasksCount = allTasksCount;
    }

    public List<TaskDTO> getTaskDTOList() {
        return taskDTOList;
    }

    public void setTaskDTOList(List<TaskDTO> taskDTOList) {
        this.taskDTOList = taskDTOList;
    }
}
