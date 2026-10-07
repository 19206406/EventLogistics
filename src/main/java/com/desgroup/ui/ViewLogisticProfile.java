/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.logicInterfaces.IStaffSalary;
import com.desgroup.models.Logistic;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author urreg
 */
public class ViewLogisticProfile extends javax.swing.JInternalFrame {

    private final Logistic logistic;
    private final ILogisticService logisticService;

    public ViewLogisticProfile(Logistic logistic, ILogisticService logisticService) {
        super("Datos del Logístico", true, true, true, true);
        this.logistic = logistic;
        this.logisticService = logisticService;
        initializeComponents();
    }

    private void initializeComponents() {
        setSize(300, 260);
        setLocation(130, 90);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        double salary = logisticService.calculateSalary(logistic.getIdStaff());

        JLabel lblName = new JLabel("Nombre: " + logistic.getName());
        JLabel lblEmail = new JLabel("Email: " + logistic.getEmail());
        JLabel lblPhone = new JLabel("Teléfono: " + logistic.getPhone());
        JLabel lblPosition = new JLabel("Posición: " + logistic.getPosition());
        JLabel lblZone = new JLabel("Zona: " + logistic.getZone());
        JLabel lblRole = new JLabel("Rol: " + logistic.getRole());
        JLabel lblSalary = new JLabel("Salario: " + salary);
        JLabel lblScore = new JLabel("Puntaje: " + logistic.getScore());

        panel.add(lblName);
        panel.add(lblEmail);
        panel.add(lblPhone);
        panel.add(lblPosition);
        panel.add(lblZone);
        panel.add(lblRole);
        panel.add(lblSalary);
        panel.add(lblScore);

        getContentPane().add(panel);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
