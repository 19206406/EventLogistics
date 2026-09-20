/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.desgroup.ui;


import com.desgroup.logic.StaffService;
import com.desgroup.logic.CoordinatorService;
import com.desgroup.logic.LogisticService;
import com.desgroup.models.Staff;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;
import com.desgroup.models.Manager;
import java.awt.CardLayout;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author urreg
 */
public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());
    private StaffService controller;
    private CoordinatorService coordinatorService;
    private LogisticService logisticService;
    private Staff usuarioActual;

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    private static final String LOGIN = "LOGIN";
    private static final String REGISTRO_COORDINADOR = "REGISTRO_COORDINADOR";
    private static final String REGISTRO_LOGISTICO = "REGISTRO_LOGISTICO";
    private static final String LOGISTICO = "LOGISTICO";
    private static final String COODINADOR = "COORDINADOR";
    private static final String MANAGER = "MANAGER";

    private JLabel lblBienvenidaCoordinador;
    private JLabel lblBienvenidaManager;

    
    
    /**
     * Creates new form Login
     */
    public LoginFrame() {
        initComponents();
        setTitle("EventLogitics");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        controller = new StaffService();
        coordinatorService = new CoordinatorService();
        logisticService = new LogisticService();

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        panelContenedor.add(crearPanelLogin(), LOGIN);
        panelContenedor.add(PanelCoordinador(), COODINADOR);
        panelContenedor.add(PanelManager(), MANAGER);
        panelContenedor.add(crearPanelRegistroCoordinador(), REGISTRO_COORDINADOR);
        panelContenedor.add(crearPanelRegistroLogistico(), REGISTRO_LOGISTICO);

        add(panelContenedor);
        cardLayout.show(panelContenedor, LOGIN);
    }

     public void mostrarPantalla(String nombre) {
        cardLayout.show(panelContenedor, nombre);
    }
     
     private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField txtUsuario = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Login");
        JLabel lblMensaje = new JLabel("");
        JLabel lblBienvenida = new JLabel("WELCOME TO EventLogitics");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblBienvenida, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password"), gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        gbc.gridy = 4;
        panel.add(lblMensaje, gbc);

        btnLogin.addActionListener(e -> {
            String email = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            Staff staffLogueado = controller.login(email, password);

            if (staffLogueado == null) {
                lblMensaje.setText("Usuario o contraseña incorrectos");
                return;
            }

            usuarioActual = staffLogueado;
            lblMensaje.setText(" ");

            if (staffLogueado instanceof Coordinator) {
                lblBienvenidaCoordinador.setText("Bienvenido, " + usuarioActual.getName());
                mostrarPantalla(COODINADOR);
            } else if (staffLogueado instanceof Manager) {
                lblBienvenidaManager.setText("Bienvenido, " + usuarioActual.getName());
                mostrarPantalla(MANAGER);
            } else if (staffLogueado instanceof Logistic) {
                mostrarPantalla(LOGISTICO);
            } else {
                lblMensaje.setText("Rol desconocido");
            }
        });

        return panel;
    }
     
     private JPanel PanelCoordinador() {
        JPanel panel = new JPanel(new FlowLayout());

        lblBienvenidaCoordinador = new JLabel("Coordinador");
        JButton btnGestionarEventos = new JButton("Gestionar Eventos");
        JButton btnCerrarSesion = new JButton("Cerrar sesion");

        panel.add(lblBienvenidaCoordinador);
        panel.add(btnGestionarEventos);
        panel.add(btnCerrarSesion);

        // btnGestionarEventos.addActionListener(e -> mostrarPantalla(GESTION_EVENTOS));
        btnCerrarSesion.addActionListener(e -> mostrarPantalla(LOGIN));

        return panel;
    }

     
     
     private JPanel PanelManager() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblBienvenidaManager = new JLabel("Gerente");
        lblBienvenidaManager.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenidaManager.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnCreateCoordinator = new JButton("Crear Coordinador");
        JButton btnCreateLogistic = new JButton("Crear Logistico");
        JButton btnSeeStaff = new JButton("Ver Personal");
        JButton btnLogOut = new JButton("Cerrar Sesion");

        for (JButton btn : new JButton[]{btnCreateCoordinator, btnCreateLogistic, btnSeeStaff, btnLogOut}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 30));
        }

        panel.add(lblBienvenidaManager);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnCreateCoordinator);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnCreateLogistic);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnSeeStaff);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnLogOut);

        btnCreateCoordinator.addActionListener(e -> mostrarPantalla(REGISTRO_COORDINADOR));
        btnCreateLogistic.addActionListener(e -> mostrarPantalla(REGISTRO_LOGISTICO));
        btnSeeStaff.addActionListener(e -> mostrarListaPersonal());
        btnLogOut.addActionListener(e -> mostrarPantalla(LOGIN));

        return panel;
    }
     
     private void mostrarListaPersonal() {
        StringBuilder sb = new StringBuilder();
        coordinatorService.getAllCoordinators().forEach(c ->
            sb.append("Coordinador: ").append(c.getName()).append(" - ").append(c.getEmail()).append("\n")
        );
        logisticService.getAllLogistics().forEach(l ->
            sb.append("Logistico: ").append(l.getName()).append(" - ").append(l.getEmail()).append("\n")
        );
        JOptionPane.showMessageDialog(this, sb.toString(), "Personal registrado", JOptionPane.INFORMATION_MESSAGE);
    }
     
    private JPanel crearPanelRegistroCoordinador() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField txtNombre = new JTextField(15);
        JTextField txtEmail = new JTextField(15);
        JTextField txtTelefono = new JTextField(15);
        JTextField txtEmpresa = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnGuardar = new JButton("Crear Coordinador");
        JButton btnVolver = new JButton("Volver");
        JLabel lblMensaje = new JLabel(" ");

        int fila = 0;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panel.add(txtNombre, gbc);

        fila++;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);

        fila++;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Telefono:"), gbc);
        gbc.gridx = 1; panel.add(txtTelefono, gbc);

        fila++;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Empresa:"), gbc);
        gbc.gridx = 1; panel.add(txtEmpresa, gbc);

        fila++;
        gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Contrasena:"), gbc);
        gbc.gridx = 1; panel.add(txtPassword, gbc);

        fila++;
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
        panel.add(btnGuardar, gbc);

        fila++;
        gbc.gridy = fila;
        panel.add(btnVolver, gbc);

        fila++;
        gbc.gridy = fila;
        panel.add(lblMensaje, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                String email = txtEmail.getText();
                int telefono = Integer.parseInt(txtTelefono.getText());
                String empresa = txtEmpresa.getText();
                String password = new String(txtPassword.getPassword());

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    lblMensaje.setText("Completa todos los campos");
                    return;
                }

                coordinatorService.createCoordinator(nombre, email, telefono, "Coordinador", password, empresa);

                JOptionPane.showMessageDialog(this, "Coordinador creado exitosamente");

                txtNombre.setText("");
                txtEmail.setText("");
                txtTelefono.setText("");
                txtEmpresa.setText("");
                txtPassword.setText("");
                lblMensaje.setText(" ");

                mostrarPantalla(MANAGER);

            } catch (NumberFormatException ex) {
                lblMensaje.setText("El telefono debe ser numerico");
            }
        });

        btnVolver.addActionListener(e -> mostrarPantalla(MANAGER));

        return panel;
    }
 
    
    private JPanel crearPanelRegistroLogistico() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    JTextField txtNombre = new JTextField(15);
    JTextField txtEmail = new JTextField(15);
    JTextField txtTelefono = new JTextField(15);
    JTextField txtZona = new JTextField(15);
    JTextField txtRol = new JTextField(15);
    JPasswordField txtPassword = new JPasswordField(15);
    JButton btnGuardar = new JButton("Crear Logistico");
    JButton btnVolver = new JButton("Volver");
    JLabel lblMensaje = new JLabel(" ");

    int fila = 0;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Nombre:"), gbc);
    gbc.gridx = 1; panel.add(txtNombre, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1; panel.add(txtEmail, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Telefono:"), gbc);
    gbc.gridx = 1; panel.add(txtTelefono, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Zona:"), gbc);
    gbc.gridx = 1; panel.add(txtZona, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Rol:"), gbc);
    gbc.gridx = 1; panel.add(txtRol, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; panel.add(new JLabel("Contrasena:"), gbc);
    gbc.gridx = 1; panel.add(txtPassword, gbc);

    fila++;
    gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 2;
    panel.add(btnGuardar, gbc);

    fila++;
    gbc.gridy = fila;
    panel.add(btnVolver, gbc);

    fila++;
    gbc.gridy = fila;
    panel.add(lblMensaje, gbc);

    btnGuardar.addActionListener(e -> {
        try {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            int telefono = Integer.parseInt(txtTelefono.getText());
            String zona = txtZona.getText();
            String rol = txtRol.getText();
            String password = new String(txtPassword.getPassword());

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                lblMensaje.setText("Completa todos los campos");
                return;
            }

            logisticService.createLogistic(nombre, email, telefono, "Logistico", zona, rol, password);

            JOptionPane.showMessageDialog(this, "Logistico creado exitosamente");

            txtNombre.setText("");
            txtEmail.setText("");
            txtTelefono.setText("");
            txtZona.setText("");
            txtRol.setText("");
            txtPassword.setText("");
            lblMensaje.setText(" ");

            mostrarPantalla(MANAGER);

        } catch (NumberFormatException ex) {
            lblMensaje.setText("El telefono debe ser numerico");
        }
    });

    btnVolver.addActionListener(e -> mostrarPantalla(MANAGER));

    return panel;
}
    
    
    
    
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
