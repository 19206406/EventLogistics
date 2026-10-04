package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageCoordinatorFrame extends JInternalFrame {
    private final ICoordinatorService coordinatorService;
    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;
    private final Staff currentUser;
    private final JDesktopPane desktopPane;

    public ManageCoordinatorFrame(ICoordinatorService coordinatorService, IEventService eventService,
            IEventAssignmentService assignmentService, Staff currentUser, JDesktopPane desktopPane) {
        super("Administrar coordinadores", true, true, true, true);
        this.coordinatorService = coordinatorService;
        this.eventService = eventService;
        this.assignmentService = assignmentService;
        this.currentUser = currentUser;
        this.desktopPane = desktopPane;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadCoordinator();
        setSize(860, 480);
        setLocation(30, 30);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createCoordinatorButton = new JButton("Crear coordinador");
        JButton updateCoordinatorButton = new JButton("Actualizar coordinador");
        JButton deleteCoordinatorButton = new JButton("Eliminar coordinador");
        JButton assignCooridnatorButton = new JButton("Asignar coordinador");

        createCoordinatorButton.addActionListener(e -> openCreateForm());
        updateCoordinatorButton.addActionListener(e -> openUpdateForm());
        deleteCoordinatorButton.addActionListener(e -> deleteSelectedCoordinator());
        assignCooridnatorButton.addActionListener(e -> openAssignForm());

        buttonPanel.add(createCoordinatorButton);
        buttonPanel.add(updateCoordinatorButton);
        buttonPanel.add(deleteCoordinatorButton);
        buttonPanel.add(assignCooridnatorButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = { "Id", "Nombre", "Email", "Telefono", "Cargo", "Compañia" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tableManageLogistic = new JTable(tableModel);
        coordinatorTable = tableManageLogistic;
        coordinatorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coordinatorTable.setRowHeight(24);
        coordinatorTable.getColumnModel().getColumn(0).setPreferredWidth(40);

        JScrollPane scrollPanelTable = new JScrollPane(coordinatorTable);
        mainPanel.add(scrollPanelTable, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void loadCoordinator() {
        tableModel.setRowCount(0);
        List<Coordinator> coordinators = coordinatorService.getAllCoordinators();
        for (Coordinator c : coordinators) {
            tableModel.addRow(new Object[] {
                    c.getIdStaff(), c.getName(), c.getEmail(), c.getPhone(),
                    c.getPosition(), c.getCompany()
            });
        }
    }

    private void openCreateForm() {
        CoordinatorFormFrame form = new CoordinatorFormFrame(coordinatorService, null, this);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void openUpdateForm() {
        int row = coordinatorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un coordinador para actualizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Coordinator coordinator = coordinatorService.getCoordinatorById(id);
        CoordinatorFormFrame form = new CoordinatorFormFrame(coordinatorService, coordinator, this);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void deleteSelectedCoordinator() {
        int row = coordinatorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un coordinador para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar al coordinador \"" + name + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            coordinatorService.deleteCoordinator(id);
            loadCoordinator();
        }
    }

    private void openAssignForm() {
        int row = coordinatorTable.getSelectedRow();
        Coordinator selectedCoordinator = null;
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            selectedCoordinator = coordinatorService.getCoordinatorById(id);
        }

        AssignCoordinatorToEvent form = new AssignCoordinatorToEvent(eventService, assignmentService,
                coordinatorService, currentUser, selectedCoordinator);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private JTable coordinatorTable;
    private DefaultTableModel tableModel;
}
