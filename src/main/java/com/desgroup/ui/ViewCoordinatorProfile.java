/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.models.Coordinator;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author urreg
 */
public class ViewCoordinatorProfile extends javax.swing.JInternalFrame {

    private final Coordinator coordinator;
    private final ICoordinatorService coordinatorService;

    /**
     * Creates new form ViewCoordinatorProfile
     */
    public ViewCoordinatorProfile(Coordinator coordinator, ICoordinatorService coordinatorService) {
        super("Datos del logistico", true, true, true, true);
        this.coordinator = coordinator;
        this.coordinatorService = coordinatorService;
        initializeComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 394, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 274, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initializeComponents() {
        setSize(300, 260);
        setLocation(130, 90);

        JPanel profilePanel = new JPanel(new GridLayout(0, 1, 5, 5));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        double salary = coordinatorService.calculateSalary(coordinator.getId());

        JLabel lblName = new JLabel("Nombre: " + coordinator.getName());
        JLabel lblEmail = new JLabel("Email: " + coordinator.getEmail());
        JLabel lblPhone = new JLabel("Teléfono: " + coordinator.getPhone());
        JLabel lblPosition = new JLabel("Posicion: " + coordinator.getPosition());
        JLabel lblCompany = new JLabel("Empresa: " + coordinator.getCompany());
        JLabel lblSalary = new JLabel("Salario: " + salary);

        profilePanel.add(lblName);
        profilePanel.add(lblEmail);
        profilePanel.add(lblPhone);
        profilePanel.add(lblPosition);
        profilePanel.add(lblCompany);
        profilePanel.add(lblSalary);
        add(profilePanel);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
