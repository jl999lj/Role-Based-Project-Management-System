/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author user
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private LocalDate startingDate;
    
    public User(String firstName, String lastName, String email, String password, String role, LocalDate startingDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.startingDate = startingDate;
    }
    

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role;
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    public void setFirstName(String firstName) { 
        this.firstName = firstName;
    }
    
    public String getLastName() { 
        return lastName;
    }
    public void setLastName(String lastName) { 
        this.lastName = lastName;
    }
    
    public LocalDate getStartingDate() { 
        return startingDate; 
    }
    public void setStartingDate(LocalDate startingDate) { 
        this.startingDate = startingDate; 
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", startingDate=" + startingDate +
                '}';
    }
}
