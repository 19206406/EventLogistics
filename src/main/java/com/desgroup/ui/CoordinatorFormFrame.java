package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.models.Coordinator;

import javax.swing.*;
import java.awt.*;

public class CoordinatorFormFrame extends JInternalFrame {
    private final ICoordinatorService coordinatorService;
    private final Coordinator coordinatorToEdit;
    private final ManageCoordinatorFrame parentFrame;

    public CoordinatorFormFrame(ICoordinatorService coordinatorService, Coordinator coordinatorToEdit, ManageCoordinatorFrame parentFrame) {
        super(getTitle(coordinatorToEdit), true, true, true, true);
        this.coordinatorService = coordinatorService;
        this.coordinatorToEdit = coordinatorToEdit;
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        if (coordinatorToEdit != null)
            fillFields();
        setSize(480, 460);
        setLocation(200, 60);
    }

    private static String getTitle(Coordinator coordinatorToEdit) {
        if (coordinatorToEdit == null) {
            return "Crear coordinador";
        } else {
            return "Actualizar coordinador";
        }
    }

    private void initComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        passwordField = new JPasswordField(20);
        positionField = new JTextField(20);
        positionField.setText("Coordinador");
        positionField.setEditable(false);
        companyField = new JTextField(20);

        String[] labels = { "Nombre:", "Correo:", "Telefono:", "Password:", "Posición:", "Compañia" };
        JComponent[] fields = { nameField, emailField, phoneField, passwordField,
                positionField, companyField };

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
        nameField.setText(coordinatorToEdit.getName());
        emailField.setText(coordinatorToEdit.getEmail());
        phoneField.setText(coordinatorToEdit.getPhone());
        passwordField.setText(coordinatorToEdit.getPassword());
        companyField.setText(coordinatorToEdit.getCompany());
    }

    private void save() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String position = "Coordinador";
        String company = companyField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()
                || position.isEmpty() || company.isEmpty()) {
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

        if (coordinatorToEdit == null) {
            coordinatorService.createCoordinator(name, email, phone, position, password, company);
        } else {
            coordinatorService.updatedCoordinator(coordinatorToEdit.getIdStaff(), name, email, phone, position, password, company);
        }

        parentFrame.loadCoordinator();
        dispose();
    }

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JTextField positionField;
    private JTextField companyField;
}
