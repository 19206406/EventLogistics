/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.models.Manager;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author urreg
 */
public class ViewManagerProfileFrame extends javax.swing.JInternalFrame {

    private final Manager manager;

    /**
     * Creates new form ViewManagerProfileFrame
     */
    public ViewManagerProfileFrame(Manager manager) {
        super("Datos del Gerente", true, true, true, true);
        this.manager = manager;
        initializeComponents();
    }

    private void initializeComponents() {
        setSize(300, 240);
        setLocation(130, 90);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblName = new JLabel("Nombre: " + manager.getName());
        JLabel lblEmail = new JLabel("Email: " + manager.getEmail());
        JLabel lblPhone = new JLabel("Teléfono: " + manager.getPhone());
        JLabel lblPosition = new JLabel("Posición: " + manager.getPosition());
        JLabel lblCompany = new JLabel("Empresa: " + manager.getCompany());

        panel.add(lblName);
        panel.add(lblEmail);
        panel.add(lblPhone);
        panel.add(lblPosition);
        panel.add(lblCompany);

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
