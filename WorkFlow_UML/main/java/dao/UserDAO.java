/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class UserDAO {
     private static final String FILE = "users.txt";

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] u = line.split(";");
                if (u.length == 6) {
                    try {
                        list.add(new User(u[3], u[4], u[0], u[1], u[2], LocalDate.parse(u[5])));
                    } catch (Exception ignored) {
                        System.out.println("⚠ User jo valid në rresht: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Gabim gjatë leximit të users.txt:");
            e.printStackTrace();
        }

        return list;
    }

    public boolean save(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (User u : users) {
                bw.write(String.join(";",
                        u.getEmail(), u.getPassword(), u.getRole(),
                        u.getFirstName(), u.getLastName(), u.getStartingDate().toString()));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("❌ Gabim gjatë ruajtjes së users.txt:");
            e.printStackTrace();
            return false;
        }
    }
}
