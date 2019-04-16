package com.todo.entity;

import java.util.List;

public class TasksHolder {

    private static List<Task> tasks;

    public TasksHolder() {
    }

    public void init() {
        tasks.add(Task.builder().checkbox(false)
                      .taskItem("To Do Item 1").build());
        tasks.add(Task.builder().checkbox(false)
                      .taskItem("To Do Item 2").build());
        tasks.add(Task.builder().checkbox(false)
                      .taskItem("To Do Item 3").build());
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List tasks) {
        TasksHolder.tasks = tasks;
    }

    public Task getTaskByName(String name) {
        return tasks.stream().filter(task -> task.getTaskItem().equalsIgnoreCase(name)).findFirst().orElseGet(() -> null);
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

}
