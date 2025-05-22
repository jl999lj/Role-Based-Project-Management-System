/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.time.LocalDate;
import java.util.List;
import model.User;
/**
 *
 * @author user
 */

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public User login(String email, String password) {
        return userDAO.getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean registerUser(String firstName, String lastName, String email, String password, String role, LocalDate date) {
        if (getUserByEmail(email) != null) return false;
        List<User> users = userDAO.getAllUsers();
        users.add(new User(firstName, lastName, email, password, role, date));
        return userDAO.save(users);
    }

    public boolean editPassword(String email, String newPassword) {
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                u.setPassword(newPassword);
                return userDAO.save(users);
            }
        }
        return false;
    }

    public boolean deleteUser(String email) {
        List<User> users = userDAO.getAllUsers();
        boolean removed = users.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        return removed && userDAO.save(users);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public String[] getPersonalDashboard(User user) {
        return new String[]{
                "Emri: " + user.getFirstName(),
                "Mbiemri: " + user.getLastName(),
                "Email: " + user.getEmail(),
                "Roli: " + user.getRole(),
                "Data e fillimit: " + user.getStartingDate()
        };
    }

    

    public boolean updateUser(User updatedUser) {
        List<User> users = userDAO.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                users.set(i, updatedUser);
                return userDAO.save(users);
            }
        }
        return false;
    }
}
