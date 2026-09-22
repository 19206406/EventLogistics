/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.LogisticService;
import com.desgroup.models.Logistic;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author urreg
 */
public class ManageLogisticFrame extends JInternalFrame {
    private final LogisticService logisticService;
    private final JDesktopPane desktopPane;
    private JTable logisticTable;
    private DefaultTableModel tableModel;

    public ManageLogisticFrame(LogisticService logisticService, JDesktopPane desktopPane) {
        super("Administrar logisticos", true, true, true, true);
        this.logisticService = logisticService;
        this.desktopPane = desktopPane;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadLogistics();
        setSize(860, 480);
        setLocation(30, 30);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createButton = new JButton("Crear logistico");
        JButton updateButton = new JButton("Actualizar logistico");
        JButton deleteButton = new JButton("Eliminar logistico");

        createButton.addActionListener(e -> openCreateForm());
        updateButton.addActionListener(e -> openUpdateForm());
        deleteButton.addActionListener(e -> deleteSelectedLogistic());

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = {"Id", "Nombre", "Email", "Telefono", "Cargo", "Zona", "Rol", "Puntuación", "Horas"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        logisticTable = new JTable(tableModel);
        logisticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        logisticTable.setRowHeight(24);
        logisticTable.getColumnModel().getColumn(0).setPreferredWidth(40);

        mainPanel.add(new JScrollPane(logisticTable), BorderLayout.CENTER);
        add(mainPanel);
    }

    /** Recarga la tabla desde el servicio. Se pasa como callback al formulario. */
    public void loadLogistics() {
        tableModel.setRowCount(0);
        List<Logistic> logistics = logisticService.getAllLogistics();
        for (Logistic l : logistics) {
            // AJUSTAR los getters a los nombres reales de tu clase Logistic
            tableModel.addRow(new Object[]{
                l.getIdStaff(), l.getName(), l.getEmail(), l.getPhone(),
                l.getPosition(), l.getZone(), l.getRole(), l.getScore(), l.getWorkingHours()
            });
        }
    }

    private void openCreateForm() {
        LogisticFormFrame form = new LogisticFormFrame(logisticService, null, this);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void openUpdateForm() {
        int row = logisticTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un logistico para actualizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Logistic logistic = logisticService.getLogisticById(id);
        LogisticFormFrame form = new LogisticFormFrame(logisticService, logistic, this);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void deleteSelectedLogistic() {
        int row = logisticTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un logistico para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar al logistico \"" + name + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            logisticService.deleteLogistic(id);
            loadLogistics();
        }
    }
}
