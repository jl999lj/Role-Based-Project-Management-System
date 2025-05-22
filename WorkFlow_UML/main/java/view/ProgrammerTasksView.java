/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JDialog;
import controller.TaskController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class ProgrammerTasksView extends JDialog {

    private static class TaskItem {
        JCheckBox checkBox;
        JTextField githubLink;
        JButton postButton;

        TaskItem(String taskName) {
            checkBox = new JCheckBox(taskName);
            githubLink = new JTextField("https://github.com/", 20);
            postButton = new JButton("Posto Detyrën");
        }
    }

    private final List<TaskItem> tasks = new ArrayList<>();

    public ProgrammerTasksView(JFrame parent, User currentUser) {
        super(parent, "Detyrat e Programuesit", true);
        setSize(600, 400);
        setLayout(new BorderLayout());

        TaskController taskController = new TaskController();
        List<String> assignedTasks = taskController.getTasksForUser(currentUser.getEmail());

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        for (String taskName : assignedTasks) {
            TaskItem item = new TaskItem(taskName);

            JPanel singleTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            singleTaskPanel.add(item.checkBox);
            singleTaskPanel.add(item.githubLink);
            singleTaskPanel.add(item.postButton);

            item.postButton.addActionListener(e -> {
                if (!item.githubLink.getText().startsWith("https://github.com/")) {
                    JOptionPane.showMessageDialog(this, "Linku duhet të fillojë me https://github.com/", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!item.checkBox.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Shëno që detyra është përfunduar para postimit.", "Kujtesë", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Detyra '" + item.checkBox.getText() + "' u postua me sukses!\nLinku: " + item.githubLink.getText());
            });

            taskPanel.add(singleTaskPanel);
            tasks.add(item);
        }

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
}
