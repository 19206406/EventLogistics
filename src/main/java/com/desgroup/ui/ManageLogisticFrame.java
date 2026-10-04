/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;
import com.desgroup.models.Staff;

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
    private final ILogisticService logisticService;
    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;
    private final Staff currentUser;
    private final JDesktopPane desktopPane;

    public ManageLogisticFrame(ILogisticService logisticService, JDesktopPane desktopPane, IEventService eventService, IEventAssignmentService assignmentService,
                               Staff currentUser) {
        super("Administrar logisticos", true, true, true, true);
        this.logisticService = logisticService;
        this.eventService = eventService;
        this.assignmentService = assignmentService;
        this.currentUser = currentUser;
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
        JButton createLoggisticButton = new JButton("Crear logistico");
        JButton updateLogisticButton = new JButton("Actualizar logistico");
        JButton deleteLogisticButton = new JButton("Eliminar logistico");
        JButton assignLogisticButton = new JButton("Asignar logisticos");

        createLoggisticButton.addActionListener(e -> openCreateForm());
        updateLogisticButton.addActionListener(e -> openUpdateForm());
        deleteLogisticButton.addActionListener(e -> deleteSelectedLogistic());
        assignLogisticButton.addActionListener(e -> openAssignForm());

        buttonPanel.add(createLoggisticButton);
        buttonPanel.add(updateLogisticButton);
        buttonPanel.add(deleteLogisticButton);
        buttonPanel.add(assignLogisticButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = { "Id", "Nombre", "Email", "Telefono", "Cargo", "Zona", "Rol", "Puntuación", "Horas" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tableManageLogistic = new JTable(tableModel);
        logisticTable = tableManageLogistic;
        logisticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        logisticTable.setRowHeight(24);
        logisticTable.getColumnModel().getColumn(0).setPreferredWidth(40);

        JScrollPane scrollPanelTable = new JScrollPane(logisticTable);
        mainPanel.add(scrollPanelTable, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void loadLogistics() {
        tableModel.setRowCount(0);
        List<Logistic> logistics = logisticService.getAllLogistics();
        for (Logistic l : logistics) {
            tableModel.addRow(new Object[] {
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

    private void openAssignForm() {
        int row = logisticTable.getSelectedRow();
        Logistic selectedLogistic = null;
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            selectedLogistic = logisticService.getLogisticById(id);
        }

        AssignLogisticToEvent form = new AssignLogisticToEvent(eventService, assignmentService, logisticService, currentUser, selectedLogistic);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private JTable logisticTable;
    private DefaultTableModel tableModel;
}
