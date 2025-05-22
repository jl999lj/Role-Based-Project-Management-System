/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.UserController;
import model.User;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author user
 */
public class LoginView extends JFrame {
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 3;
    private final UserController userController;
    
    public LoginView() {
        userController = new UserController();
        
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);
        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        
        loginButton = new JButton("Login");
        add(loginButton);
        loginButton.addActionListener(e -> attemptLogin());
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void attemptLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        User user = userController.login(email, password);
        
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login i suksesshëm!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new DashboardView(user);
        } else {
            loginAttempts++;
            int remaining = MAX_ATTEMPTS - loginAttempts;
            if (remaining <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Keni bërë 3 përpjekje të pasakta. Sistemi do të mbyllet.",
                    "Gabim", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Kredencialet janë të pasakta. Përpjekje të mbetura: " + remaining,
                    "Login Gabim", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
}
