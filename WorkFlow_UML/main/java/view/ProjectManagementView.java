/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ProjectController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
/**
 *
 * @author user
 */
public class ProjectManagementView extends JDialog {
    private final JTextArea infoArea;
    private final ProjectController projectController;
    
    public ProjectManagementView(JFrame parent) {
        super(parent, "Menaxho Projektet", true);
        this.projectController = new ProjectController();
        
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel projectListPanel = new JPanel();
        projectListPanel.setLayout(new BoxLayout(projectListPanel, BoxLayout.Y_AXIS));
        projectListPanel.setBorder(BorderFactory.createTitledBorder("Projektet"));
        
        Map<String, String> projectData = projectController.getProjectsAsMap();
        
        for (String emri : projectData.keySet()) {
            JLabel label = new JLabel("<html><a href='#'>" + emri + "</a></html>");
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    infoArea.setText(projectData.get(emri));
                }
            });
            projectListPanel.add(label);
        }
        

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton shtoBtn = new JButton("Shto Projekt");
        JButton modifikoBtn = new JButton("Modifiko Projekt");
        JButton fshiBtn = new JButton("Fshi Projekt");
        
        bottomPanel.add(shtoBtn);
        bottomPanel.add(modifikoBtn);
        bottomPanel.add(fshiBtn);
        

        shtoBtn.addActionListener(e -> shtoProject());
        modifikoBtn.addActionListener(e -> modifikoProject());
        fshiBtn.addActionListener(e -> fshiProject());

        add(projectListPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void shtoProject() {
        JTextField emriField = new JTextField();
        JTextField pershkrimiField = new JTextField();
        JTextField progresiField = new JTextField();
        JTextArea anetaretArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(anetaretArea);
         Object[] fields = {
            "Emri:", emriField,
            "Përshkrimi:", pershkrimiField,
            "Progresi (%):", progresiField,
            "Anëtarët (një për rresht):", scrollPane
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Shto Projekt", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String emri = emriField.getText().trim();
                String pershkrimi = pershkrimiField.getText().trim();
                int progresi = Integer.parseInt(progresiField.getText().trim());
                String anetaret = anetaretArea.getText().trim();
                
                if (emri.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Emri nuk mund të jetë bosh.", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (progresi < 0 || progresi > 100) {
                    JOptionPane.showMessageDialog(this, "Progresi duhet të jetë midis 0 dhe 100.", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                boolean success = projectController.addProject(emri, pershkrimi, progresi, anetaret);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Projekti u shtua me sukses.");
                    dispose();
                    new ProjectManagementView((JFrame) getParent()); 
                } else {
                    JOptionPane.showMessageDialog(this, "Projekt me këtë emër ekziston tashmë.", "Gabim", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ju lutem shkruani një numër të vlefshëm për progresin.", "Gabim", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim gjatë shtimit të projektit: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void modifikoProject() {
 
        String[] projektet = projectController.getProjectsAsMap().keySet().toArray(new String[0]);
        if (projektet.length == 0) {
            JOptionPane.showMessageDialog(this, "Nuk ka projekte për të modifikuar.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String emriProjektit = (String) JOptionPane.showInputDialog(this, 
            "Zgjidhni projektin për ta modifikuar:",
            "Modifiko Projekt",
            JOptionPane.QUESTION_MESSAGE,
            null,
            projektet,
            projektet[0]);
        
        if (emriProjektit == null) return;
 
        model.Project projekt = projectController.getProjectByName(emriProjektit);
        if (projekt == null) {
            JOptionPane.showMessageDialog(this, "Projekti nuk u gjet.", "Gabim", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField emriField = new JTextField(projekt.getName());
        JTextField pershkrimiField = new JTextField(projekt.getDescription());
        JTextField progresiField = new JTextField(String.valueOf(projekt.getProgress()));
        JTextArea anetaretArea = new JTextArea(5, 20);
        anetaretArea.setText(projekt.getMembers());
        JScrollPane scrollPane = new JScrollPane(anetaretArea);
        
        Object[] fields = {
            "Emri:", emriField,
            "Përshkrimi:", pershkrimiField,
            "Progresi (%):", progresiField,
            "Anëtarët (një për rresht):", scrollPane
        };
        
        int result = JOptionPane.showConfirmDialog(this, fields, "Modifiko Projekt", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String emriRi = emriField.getText().trim();
                String pershkrimi = pershkrimiField.getText().trim();
                int progresi = Integer.parseInt(progresiField.getText().trim());
                String anetaret = anetaretArea.getText().trim();
                
                if (emriRi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Emri nuk mund të jetë bosh.", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (progresi < 0 || progresi > 100) {
                    JOptionPane.showMessageDialog(this, "Progresi duhet të jetë midis 0 dhe 100.", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                

                if (!emriRi.equals(emriProjektit) && projectController.getProjectByName(emriRi) != null) {
                    JOptionPane.showMessageDialog(this, "Projekt me emrin e ri ekziston tashmë.", "Gabim", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!emriRi.equals(emriProjektit)) {
                    projectController.deleteProject(emriProjektit);
                }
                
                boolean success = projectController.updateProject(emriRi, pershkrimi, progresi, anetaret);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Projekti u modifikua me sukses.");
                    dispose();
                    new ProjectManagementView((JFrame) getParent()); // Refresh
                } else {
                    JOptionPane.showMessageDialog(this, "Gabim gjatë modifikimit të projektit.", "Gabim", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ju lutem shkruani një numër të vlefshëm për progresin.", "Gabim", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gabim gjatë modifikimit të projektit: " + ex.getMessage(), "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void fshiProject() {
        // Get the project name to delete
        String[] projektet = projectController.getProjectsAsMap().keySet().toArray(new String[0]);
        if (projektet.length == 0) {
            JOptionPane.showMessageDialog(this, "Nuk ka projekte për të fshirë.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String emriProjektit = (String) JOptionPane.showInputDialog(this, 
            "Zgjidhni projektin për ta fshirë:",
            "Fshi Projekt",
            JOptionPane.QUESTION_MESSAGE,
            null,
            projektet,
            projektet[0]);
        
        if (emriProjektit == null) return;
        
        int confirmation = JOptionPane.showConfirmDialog(this,
            "A jeni të sigurt që dëshironi të fshini projektin '" + emriProjektit + "'?",
            "Konfirmo Fshirjen",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = projectController.deleteProject(emriProjektit);
            if (success) {
                JOptionPane.showMessageDialog(this, "Projekti u fshi me sukses.");
                dispose();
                new ProjectManagementView((JFrame) getParent()); // Refresh
            } else {
                JOptionPane.showMessageDialog(this, "Gabim gjatë fshirjes së projektit.", "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
