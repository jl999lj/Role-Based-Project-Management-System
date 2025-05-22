/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author user
 */
public class Project {
      private String name;
    private String description;
    private int progress;
    private String members;
    
    public Project(String name, String description, int progress, String members) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.members = members;
    }
    
  
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public int getProgress() { 
        return progress;
    }
    public void setProgress(int progress) { 
        this.progress = progress;
    }
    
    public String getMembers() { 
        return members;
    }
    public void setMembers(String members) { 
        this.members = members; 
    }
    
    @Override
    public String toString() {
        return "Emri: " + name + "\n" +
               "Përshkrimi: " + description + "\n" +
               "Progresi: " + progress + "%\n" +
               "Anëtarë:\n" + members;
    }
}
