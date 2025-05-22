/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
/**
 *
 * @author user
 */
public class DashboardView extends JFrame {
    private final UserController userController;
    private final User currentUser;

    public DashboardView(User user) {
        this.currentUser = user;
        this.userController = new UserController();

        setTitle(user.getRole() + " Panel");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        // Info personale
        String[] personal = userController.getPersonalDashboard(user);
        for (String data : personal) {
            eastPanel.add(new JLabel(data));
        }

        eastPanel.add(Box.createVerticalStrut(10));
        JButton editoTeDhenatBtn = new JButton("Edito të dhënat");
        eastPanel.add(editoTeDhenatBtn);
        editoTeDhenatBtn.addActionListener(e -> openEditPersonalDataDialog());


        // Butonat sipas rolit
        if (user.getRole().equals("Admin")) {
            JButton createTLButton = new JButton("Krijo TeamLeader");
            JButton createPrgButton = new JButton("Krijo Programmer");

            createTLButton.addActionListener(e -> openCreateUserDialog("Team Leader"));
            createPrgButton.addActionListener(e -> openCreateUserDialog("Programmer"));

            centerPanel.add(createTLButton);
            centerPanel.add(createPrgButton);
        }

        if (user.getRole().equals("Admin") || user.getRole().equals("Team Leader")) {
            JButton menaxhoProjektetBtn = new JButton("Menaxho Projektet");
            menaxhoProjektetBtn.addActionListener(e -> new ProjectManagementView(this));
            centerPanel.add(menaxhoProjektetBtn);

            JButton menaxhoAnetaretBtn = new JButton("Menaxho Anëtarët");
            menaxhoAnetaretBtn.addActionListener(e -> new UserManagementView(this));
            centerPanel.add(menaxhoAnetaretBtn);
        }

        if (user.getRole().equals("Team Leader")) {
            JButton percaktoDetyratBtn=new JButton("Percakto Detyrat");
            percaktoDetyratBtn.addActionListener(e -> new AssignTasksView(this));
            centerPanel.add(percaktoDetyratBtn);
        }

        if (user.getRole().equals("Programmer")) {
            JButton kontrolloDetyratBtn=new JButton("Detyrat");
            kontrolloDetyratBtn.addActionListener(e -> new ProgrammerTasksView(this, currentUser));

            centerPanel.add(kontrolloDetyratBtn);
        }

        // Paneli për kontaktim
        JButton kontaktoButton = new JButton("Kontakto");
        southPanel.add(kontaktoButton);

        JPanel contactOptionsPanel = new JPanel(new FlowLayout());
        contactOptionsPanel.setVisible(false);

        JButton contactAdmin = new JButton("Kontakto Admin");
        JButton contactTL = new JButton("Kontakto TL");
        JButton contactProgrammer = new JButton("Kontakto Programmer");

        contactOptionsPanel.add(contactAdmin);
        contactOptionsPanel.add(contactTL);
        contactOptionsPanel.add(contactProgrammer);
        southPanel.add(contactOptionsPanel);

        kontaktoButton.addActionListener(e -> contactOptionsPanel.setVisible(true));

        contactAdmin.addActionListener(e -> showContactMessage("Admin"));
        contactTL.addActionListener(e -> showContactMessage("Team Leader"));
        contactProgrammer.addActionListener(e -> showContactMessage("Programmer"));

        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showContactMessage(String person) {
        int option = JOptionPane.showOptionDialog(this,
                "Po kontaktohet " + person + "...",
                "Kontaktim",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Përfundo procesin"},
                "Përfundo procesin");

        if (option == 0) {
            JOptionPane.showMessageDialog(this, "Procesi përfundoi.");
        }
    }

    private void openCreateUserDialog(String roleToCreate) {
        JDialog dialog = new JDialog(this, "Regjistrim i " + roleToCreate, true);
        dialog.setSize(300, 500);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField startDateField = new JTextField("yyyy-MM-dd");

        dialog.add(new JLabel("Emri:"));
        dialog.add(firstNameField);
        dialog.add(new JLabel("Mbiemri:"));
        dialog.add(lastNameField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Password:"));
        dialog.add(passwordField);
        dialog.add(new JLabel("Data e fillimit:"));
        dialog.add(startDateField);

        JButton submitButton = new JButton("Regjistro");
        dialog.add(new JLabel());
        dialog.add(submitButton);

        submitButton.addActionListener(e -> {
            try {
                boolean success = userController.registerUser(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        new String(passwordField.getPassword()),
                        roleToCreate,
                        LocalDate.parse(startDateField.getText()));

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "U regjistrua me sukses!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "User ekziston!", "Gabim", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Gabim gjatë regjistrimit: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void openEditPersonalDataDialog() {
        JDialog dialog = new JDialog(this, "Edito të dhënat personale", true);
        dialog.setSize(300, 400);
        dialog.setLayout(new GridLayout(6, 2));

        JTextField firstNameField = new JTextField(currentUser.getFirstName());
        JTextField lastNameField = new JTextField(currentUser.getLastName());
        JTextField emailField = new JTextField(currentUser.getEmail());
        emailField.setEditable(false);
        JPasswordField passwordField = new JPasswordField(currentUser.getPassword());
        JTextField startDateField = new JTextField(currentUser.getStartingDate().toString());

        dialog.add(new JLabel("Emri:"));
        dialog.add(firstNameField);
        dialog.add(new JLabel("Mbiemri:"));
        dialog.add(lastNameField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Password:"));
        dialog.add(passwordField);
        dialog.add(new JLabel("Data e fillimit:"));
        dialog.add(startDateField);

        JButton ruajButton = new JButton("Ruaj");
        dialog.add(new JLabel());
        dialog.add(ruajButton);

        ruajButton.addActionListener(e -> {
            try {
                currentUser.setFirstName(firstNameField.getText());
                currentUser.setLastName(lastNameField.getText());
                currentUser.setPassword(new String(passwordField.getPassword()));
                currentUser.setStartingDate(LocalDate.parse(startDateField.getText()));

                boolean success = userController.updateUser(currentUser);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Të dhënat u ruajtën me sukses.");
                    dialog.dispose();
                    this.dispose();
                    new DashboardView(currentUser); // Refresh
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gabim gjatë ruajtjes.", "Gabim", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Gabim në formatin e të dhënave: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}


