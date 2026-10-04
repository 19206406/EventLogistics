package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.repositoriesInterfaces.IEventAssignmentRepository;
import com.desgroup.models.*;
import com.desgroup.models.Event;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AssignLogisticToEvent extends JInternalFrame {

    private static final String[] AVAILABLE_COLUMNS = {"Id", "Nombre", "Correo", "Telefono", "Compañia"};
    private static final String[] ASSIGNED_COLUMNS = { "Id Asignación", "Id Logistico", "Nombre", "Correo"};

    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;
    private final ILogisticService logisticService;
    private final Staff currentUser;
    private final Logistic preselectedLogistic;


    public AssignLogisticToEvent(IEventService eventService, IEventAssignmentService assignmentService,
            ILogisticService logisticService, Staff currentUser, Logistic preselectedLogistic) {
        super("Asignar logistico a evento", true, true, true, true);
        this.eventService = eventService;
        this.assignmentService = assignmentService;
        this.logisticService = logisticService;
        this.currentUser = currentUser;
        this.preselectedLogistic = preselectedLogistic;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadEvents();
        setSize(900, 480);
        setLocation(60, 50);
    }


    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        eventPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        eventLabel = new JLabel("Evento");
        eventComboBox = new JComboBox<>();
        eventComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Event event)
                    setText(event.getName() + " - " + event.getDate() + " (" + event.getState() + ")");
                return this;
            }
        });

        eventComboBox.addActionListener(e -> loadLogistics());
        logisticSlotsLabel = new JLabel();

        eventPanel.add(eventLabel);
        eventPanel.add(eventComboBox);
        eventPanel.add(logisticSlotsLabel);
        mainPanel.add(eventPanel, BorderLayout.NORTH);

        availableTableModel = createReadOnlyTableModel(AVAILABLE_COLUMNS);
        availableLogisticsTable = createTable(availableTableModel);
        availableScrollPane = new JScrollPane(availableLogisticsTable);
        availableScrollPane.setBorder(BorderFactory.createTitledBorder("Logisticos disponibles"));

        assignedTableModel = createReadOnlyTableModel(ASSIGNED_COLUMNS);
        assignedLogisticsTable = createTable(assignedTableModel);
        assignedScrollPane = new JScrollPane(assignedLogisticsTable);
        assignedScrollPane.setBorder(BorderFactory.createTitledBorder("Logisticos asignados"));

        tablesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tablesPanel.add(availableScrollPane);
        tablesPanel.add(assignedScrollPane);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        assignButton = new JButton("Asignar");
        unassignButton = new JButton("Desasignar");
        closeButton = new JButton("Cerrar");
        assignButton.addActionListener(e -> assignSelectedLogistic());
        unassignButton.addActionListener(e -> unassignSelectedLogistic());
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(assignButton);
        buttonPanel.add(unassignButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private DefaultTableModel createReadOnlyTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        return table;
    }

    private void loadEvents() {
        eventComboBox.removeAllItems();
        for (Event event : eventService.getAllEvents()) {
            eventComboBox.addItem(event);
        }
        loadLogistics();
    }

    private void loadLogistics() {
        availableTableModel.setRowCount(0);
        assignedTableModel.setRowCount(0);

        Event selectedEvent = getSelectedEvent();
        boolean hasEvent = selectedEvent != null;
        assignButton.setEnabled(hasEvent);
        unassignButton.setEnabled(hasEvent);
        if (!hasEvent) {
            logisticSlotsLabel.setText("Eventos no disponibles");
            return;
        }

        int eventId = selectedEvent.getIdEvent();
        for (Logistic logistic : assignmentService.getAvailableLogistics(eventId)) {
            availableTableModel.addRow(new Object[] {
                    logistic.getIdStaff(), logistic.getName(), logistic.getEmail(),
                    logistic.getPhone()
            });
        }

        List<EventAssignment> logisticsAssignments = assignmentService.getAssignmentsByEvent(eventId).stream()
                .filter(assignment -> assignment.getStaffRole() == StaffRole.LOGISTIC)
                .toList();
        for (EventAssignment assignment : logisticsAssignments) {
            Logistic logistic = logisticService.getLogisticById(assignment.getIdStaff());
            assignedTableModel.addRow(new Object[] {
                    assignment.getIdAssignment(), assignment.getIdStaff(),
                    logistic != null ? logistic.getName() : "Desconocido",
                    logistic != null ? logistic.getEmail() : ""
            });
        }

        logisticSlotsLabel.setText("Logisticos: " + logisticsAssignments.size()
                + " / " + selectedEvent.getMaxCoordinators());
        selectPreselectedLogistic();
    }

    private void selectPreselectedLogistic() {
        if (preselectedLogistic == null)
            return;

        for (int row = 0; row < availableTableModel.getRowCount(); row++) {
            if ((int) availableTableModel.getValueAt(row, 0) == preselectedLogistic.getIdStaff()) {
                availableLogisticsTable.setRowSelectionInterval(row, row);
                return;
            }
        }
    }

    private void assignSelectedLogistic() {
        Event selectedEvent = getSelectedEvent();
        int row = availableLogisticsTable.getSelectedRow();
        if (selectedEvent == null || row == -1) {
            showWarning("Selecciona un logistico disponible para asignar");
            return;
        }

        int logisticId = (int) availableTableModel.getValueAt(row, 0);
        Logistic logistic = logisticService.getLogisticById(logisticId);
        if (logistic == null) {
            showWarning("El logistico seleccionado no existe");
            loadLogistics();
            return;
        }

        try {
            assignmentService.assign(currentUser, logistic, selectedEvent.getIdEvent());
            JOptionPane.showMessageDialog(this, "Logistico asignado correctamente", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }
        loadLogistics();
    }

    private void unassignSelectedLogistic() {
        int row = assignedLogisticsTable.getSelectedRow();
        if (row == -1) {
            showWarning("Select an assigned logistic to unassign.");
            return;
        }

        int assignmentId = (int) assignedTableModel.getValueAt(row, 0);
        String logisticName = (String) assignedTableModel.getValueAt(row, 2);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Do you want to unassign \"" + logisticName + "\"?\n"
                        + "The logistics assigned by this coordinator to the event will also be removed.",
                "Confirm unassignment",
                JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION)
            return;

        try {
            assignmentService.unassign(currentUser, assignmentId);
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }
        loadLogistics();
    }

    private Event getSelectedEvent() {
        return (Event) eventComboBox.getSelectedItem();
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel mainPanel;
    private JPanel eventPanel;
    private JLabel eventLabel;
    private JComboBox<Event> eventComboBox;
    private JLabel logisticSlotsLabel;
    private JPanel tablesPanel;
    private JTable availableLogisticsTable;
    private DefaultTableModel availableTableModel;
    private JScrollPane availableScrollPane;
    private JTable assignedLogisticsTable;
    private DefaultTableModel assignedTableModel;
    private JScrollPane assignedScrollPane;
    private JPanel buttonPanel;
    private JButton assignButton;
    private JButton unassignButton;
    private JButton closeButton;
}
