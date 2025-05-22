/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Project;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class ProjectDAO {
     private static final String FILE = "projects.txt";

    public List<Project> getAllProjects() {
        List<Project> list = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", 4);
                if (p.length == 4) {
                    try {
                        int progress = Integer.parseInt(p[2]);
                        String members = p[3].replace("\\n", "\n");
                        list.add(new Project(p[0], p[1], progress, members));
                    } catch (NumberFormatException e) {
                        System.out.println("⚠ Format i gabuar i progresit për projektin: " + p[0]);
                    }
                } else {
                    System.out.println("⚠ Linjë jo valide në projects.txt: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Gabim gjatë leximit të projects.txt:");
            e.printStackTrace();
        }

        return list;
    }

    public boolean save(List<Project> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Project p : list) {
                bw.write(String.join(";",
                        p.getName(),
                        p.getDescription(),
                        String.valueOf(p.getProgress()),
                        p.getMembers().replace("\n", "\\n")
                ));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("❌ Gabim gjatë ruajtjes së projects.txt:");
            e.printStackTrace();
            return false;
        }
    }
}
