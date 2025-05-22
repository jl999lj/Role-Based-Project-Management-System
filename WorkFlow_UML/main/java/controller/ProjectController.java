/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProjectDAO;
import java.util.LinkedHashMap;
import java.util.List;
import model.Project;
import java.util.Map;

/**
 *
 * @author user
 */
public class ProjectController {
    private final ProjectDAO projectDAO;

    public ProjectController() {
        this.projectDAO = new ProjectDAO();
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public Project getProjectByName(String name) {
        return getAllProjects().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean addProject(String name, String description, int progress, String members) {
        List<Project> all = getAllProjects();
        if (all.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name))) return false;
        all.add(new Project(name, description, progress, members));
        return projectDAO.save(all);
    }

    public boolean updateProject(String name, String description, int progress, String members) {
        List<Project> all = getAllProjects();
        boolean found = false;

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getName().equalsIgnoreCase(name)) {
                all.set(i, new Project(name, description, progress, members));
                found = true;
                break;
            }
        }

        return found && projectDAO.save(all);
    }

    public boolean deleteProject(String name) {
        List<Project> all = getAllProjects();
        boolean removed = all.removeIf(p -> p.getName().equalsIgnoreCase(name));
        return removed && projectDAO.save(all);
    }

    public Map<String, String> getProjectsAsMap() {
        Map<String, String> result = new LinkedHashMap<>();
        for (Project p : getAllProjects()) {
            result.put(p.getName(), p.toString());
        }
        return result;
    }
}