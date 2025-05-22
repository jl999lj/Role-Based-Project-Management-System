/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.TaskController;
import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class AssignTasksView extends JDialog {
    private final UserController userController = new UserController();
    private final TaskController taskController = new TaskController();

    public AssignTasksView(JFrame parent) {
        super(parent, "Përcakto Detyra", true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        List<User> users = userController.getAllUsers();
        List<String> programmers = new ArrayList<>();

        for (User u : users) {
            if (u.getRole().equalsIgnoreCase("Programmer")) {
                programmers.add(u.getEmail());
            }
        }

        JComboBox<String> emailBox = new JComboBox<>(programmers.toArray(new String[0]));
        JTextArea taskArea = new JTextArea(10, 30);
        JButton assignButton = new JButton("Cakto Detyrat");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Zgjidh programuesin sipas email-it:"));
        panel.add(emailBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Shkruaj detyrat (një për rresht):"));
        panel.add(new JScrollPane(taskArea));
        panel.add(Box.createVerticalStrut(10));
        panel.add(assignButton);

        assignButton.addActionListener(e -> {
            String email = (String) emailBox.getSelectedItem();
            String[] lines = taskArea.getText().split("\\n");
            List<String> tasks = new ArrayList<>();
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    tasks.add(line.trim());
                }
            }
            boolean success = taskController.assignTasksToUser(email, tasks);
            if (success) {
                JOptionPane.showMessageDialog(this, "Detyrat u caktuan me sukses për: " + email);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gabim gjatë ruajtjes së detyrave.", "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
}
