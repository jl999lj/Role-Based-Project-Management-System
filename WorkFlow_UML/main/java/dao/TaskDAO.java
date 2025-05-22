/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.io.*;
import java.util.*;
/**
 *
 * @author user
 */
public class TaskDAO {
     private static final String FILE = "tasks.txt";

    public Map<String, List<String>> loadTasks() {
        Map<String, List<String>> map = new HashMap<>();
        File file = new File(FILE);
        if (!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) {
                    map.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public boolean saveTasks(Map<String, List<String>> map) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (var entry : map.entrySet()) {
                for (String task : entry.getValue()) {
                    bw.write(entry.getKey() + ";" + task);
                    bw.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
