/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.TaskDAO;
import java.util.*;

/**
 *
 * @author user
 */
public class TaskController {
    private final TaskDAO taskDAO;

    public TaskController() {
        this.taskDAO = new TaskDAO();
    }

    public List<String> getTasksForUser(String email) {
        Map<String, List<String>> allTasks = taskDAO.loadTasks();
        return allTasks.getOrDefault(email, new ArrayList<>());
    }

    public boolean assignTasksToUser(String email, List<String> newTasks) {
        Map<String, List<String>> allTasks = taskDAO.loadTasks();
        allTasks.computeIfAbsent(email, k -> new ArrayList<>()).addAll(newTasks);
        return taskDAO.saveTasks(allTasks);
    }
}
