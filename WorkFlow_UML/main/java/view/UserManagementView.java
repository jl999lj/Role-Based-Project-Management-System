/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.UserController;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.User;

/**
 *
 * @author user
 */
public class UserManagementView extends JDialog {
    private final UserController userController;

    public UserManagementView(JFrame parent) {
        super(parent, "Menaxho Anëtarët", true);
        this.userController = new UserController();
        
        String[] opsionet = {"Shto Anëtar", "Edito Password", "Fshi Anëtar", "Shfaq Listën", "Dil"};
        int zgjedhja;

        do {
            zgjedhja = JOptionPane.showOptionDialog(this, "Zgjidh një veprim:", "Menaxhimi i Anëtarëve",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opsionet, opsionet[0]);

            switch (zgjedhja) {
                case 0 -> shtoAnetar();
                case 1 -> editoPassword();
                case 2 -> fshiAnetar();
                case 3 -> shfaqTeGjithe();
                case 4 -> JOptionPane.showMessageDialog(this, "Procesi u mbyll.");
            }
        } while (zgjedhja != 4);

        dispose();
    }

    private void shtoAnetar() {
        JTextField emri = new JTextField();
        JTextField mbiemri = new JTextField();
        JTextField email = new JTextField();
        JTextField password = new JTextField();
        String[] roles = {"Programmer", "Team Leader", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JTextField data = new JTextField("yyyy-MM-dd");

        Object[] fields = {
            "Emri:", emri,
            "Mbiemri:", mbiemri,
            "Email:", email,
            "Password:", password,
            "Roli:", roleBox,
            "Data e fillimit:", data
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Shto Anëtar", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean success = userController.registerUser(
                    emri.getText(),
                    mbiemri.getText(),
                    email.getText(),
                    password.getText(),
                    (String) roleBox.getSelectedItem(),
                    LocalDate.parse(data.getText())
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Anëtari u shtua me sukses.");
                } else {
                    JOptionPane.showMessageDialog(this, "Anëtar me këtë email ekziston tashmë.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim gjatë regjistrimit: " + ex.getMessage());
            }
        }
    }

    private void editoPassword() {
        String email = JOptionPane.showInputDialog(this, "Shkruaj email-in e anëtarit:");
        if (email == null || email.isEmpty()) return;

        String newPass = JOptionPane.showInputDialog(this, "Shkruaj passwordin e ri:");
        if (newPass != null && !newPass.isEmpty()) {
            if (userController.editPassword(email, newPass)) {
                JOptionPane.showMessageDialog(this, "Passwordi u ndryshua me sukses.");
            } else {
                JOptionPane.showMessageDialog(this, "Nuk u gjet përdorues me këtë email.");
            }
        }
    }

    private void fshiAnetar() {
        String email = JOptionPane.showInputDialog(this, "Shkruaj email-in e anëtarit për fshirje:");
        if (email == null || email.isEmpty()) return;

        if (userController.deleteUser(email)) {
            JOptionPane.showMessageDialog(this, "Përdoruesi u fshi me sukses.");
        } else {
            JOptionPane.showMessageDialog(this, "Nuk u gjet përdorues me këtë email.");
        }
    }

    private void shfaqTeGjithe() {
        List<User> users = userController.getAllUsers();
        if (users == null || users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nuk ka anëtarë për të shfaqur.", "Lista e Anëtarëve", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            if (u != null) {
                sb.append(u.getEmail()).append(" | ").append(u.getRole()).append("\n");
            }
        }

        if (sb.length() == 0) {
            sb.append("Asnjë anëtar i vlefshëm për t'u shfaqur.");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Lista e Anëtarëve", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
