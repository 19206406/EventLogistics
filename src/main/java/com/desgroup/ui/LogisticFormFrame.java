/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.LogisticService;
import com.desgroup.models.Logistic;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author urreg
 */
public class LogisticFormFrame extends JInternalFrame {
    private final LogisticService logisticService;
    private final Logistic logisticToEdit;
    private final ManageLogisticFrame parentFrame;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JTextField positionField;
    private JTextField zoneField;
    private JTextField roleField;
    private JTextField scoreField;
    private JTextField workingHoursField;

    public LogisticFormFrame(LogisticService logisticService, Logistic logisticToEdit,
            ManageLogisticFrame parentFrame) {
        super(getTitle(logisticToEdit), true, true, true, true);
        this.logisticService = logisticService;
        this.logisticToEdit = logisticToEdit;
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        if (logisticToEdit != null) {
            fillFields();
        }
        setSize(480, 460);
        setLocation(200, 60);
    }

    private static String getTitle(Logistic logisticToEdit) {
        if (logisticToEdit == null) {
            return "Crear logistico";
        } else {
            return "Actualizar logistico";
        }
    }

    private void initComponents() {
        nameField = new JTextField(20);

        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        passwordField = new JPasswordField(20);
        positionField = new JTextField(20);
        zoneField = new JTextField(20);
        roleField = new JTextField(20);
        workingHoursField = new JTextField(20);
        scoreField = new JTextField(20);

        String[] labels = { "Nombre:", "Correo:", "Telefono:", "Password:", "Posición:", "Zona:", "Rol:", "Puntuación:",
                "Horas:" };
        JComponent[] fields = { nameField, emailField, phoneField, passwordField,
                positionField, zoneField, roleField, scoreField, workingHoursField };

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            contentPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            contentPanel.add(fields[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Guardar");
        JButton closeButton = new JButton("Cerrar");
        saveButton.addActionListener(e -> save());
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        contentPanel.add(buttonPanel, gbc);

        add(contentPanel);
    }

    private void fillFields() {
        nameField.setText(logisticToEdit.getName());
        emailField.setText(logisticToEdit.getEmail());
        phoneField.setText(logisticToEdit.getPhone());
        passwordField.setText(logisticToEdit.getPassword());
        zoneField.setText(logisticToEdit.getZone());
        roleField.setText(logisticToEdit.getRole());
        scoreField.setText(Integer.toString(logisticToEdit.getScore()));
        workingHoursField.setText(Integer.toString(logisticToEdit.getWorkingHours()));
    }

    private void save() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String position = "Logistico";
        String zone = zoneField.getText().trim();

        String role = roleField.getText().trim();
        int score = Integer.parseInt(scoreField.getText().trim());
        int workingHours = Integer.parseInt(workingHoursField.getText().trim());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()
                || position.isEmpty() || zone.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "El correo no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El telefono debe contener solo números.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (logisticToEdit == null) {
            logisticService.createLogistic(name, email, phone, position, zone, role, password, score, workingHours);
        } else {
            logisticService.updatedLogistic(logisticToEdit.getIdStaff(), name, email, phone, position, zone, role,
                    password, score,
                    workingHours);
        }

        parentFrame.loadLogistics();
        dispose();
    }
}
