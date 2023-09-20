package com.plannerapp.model.entity;

import com.plannerapp.model.enums.PriorityNameEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "priorities")
public class Priority {

    public Priority(PriorityNameEnum priorityName, String description) {
        this.priorityName = priorityName;
        this.description = description;
    }

    public Priority() {
        this.tasks = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "priority_name",
            unique = true,
            nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityNameEnum priorityName;

    @Column(name = "description",
    nullable = false)
    private String description;

    @OneToMany(mappedBy = "priority",targetEntity = Task.class)
    private List<Task> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PriorityNameEnum getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(PriorityNameEnum priorityName) {
        this.priorityName = priorityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
