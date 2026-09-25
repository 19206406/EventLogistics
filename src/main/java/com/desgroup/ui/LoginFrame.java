/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.desgroup.ui;

import javax.swing.JOptionPane;

import com.desgroup.logic.CoordinatorService;
import com.desgroup.logic.EventAssignmentService;
import com.desgroup.logic.EventService;
import com.desgroup.logic.LogisticService;
import com.desgroup.logic.StaffService;
import com.desgroup.models.Staff;

/**
 *
 * @author urreg
 */
public class LoginFrame extends javax.swing.JFrame {
    private final StaffService service;

    public LoginFrame() {
        service = new StaffService();
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEmail = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        lblTitleLogin = new javax.swing.JLabel();
        pwdPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblEmail.setText("Email:");

        lblPassword.setText("Contraseña:");

        btnSubmit.setText("Ingresar");
        btnSubmit.addActionListener(this::btnSubmitActionPerformed);

        lblTitleLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleLogin.setText("Bienvenidos Ingresa tu email y contraseña para ingresar.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTitleLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(pwdPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblTitleLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwdPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnSubmit)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSubmitActionPerformed

        String email = txtEmail.getText();
        String password = new String(pwdPassword.getPassword());

        Staff isSuccess = service.login(email, password);

        if (isSuccess != null) {
            MainFrame mainFrame = new MainFrame(new LogisticService(), new EventService(), new EventAssignmentService(),
                    new CoordinatorService(), isSuccess);
            mainFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "A ocurrido un error al iniciar sesión intenta de nuevo.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);

        }
    }// GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTitleLogin;
    private javax.swing.JPasswordField pwdPassword;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
