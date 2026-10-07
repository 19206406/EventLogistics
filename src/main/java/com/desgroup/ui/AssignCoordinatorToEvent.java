package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Staff;
import com.desgroup.utils.BusinessException;
import com.desgroup.utils.StaffRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AssignCoordinatorToEvent extends JInternalFrame {
    private static final String[] AVAILABLE_COLUMNS = { "Id", "Name", "Email", "Phone", "Company" };
    private static final String[] ASSIGNED_COLUMNS = { "Assignment Id", "Coordinator Id", "Name", "Email" };

    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;
    private final ICoordinatorService coordinatorService;
    private final Staff currentUser;
    private final Coordinator preselectedCoordinator;

    public AssignCoordinatorToEvent(IEventService eventService, IEventAssignmentService assignmentService,
            ICoordinatorService coordinatorService, Staff currentUser, Coordinator preselectedCoordinator) {
        super("Asignar coordinador a evento", true, true, true, true);
        this.eventService = eventService;
        this.assignmentService = assignmentService;
        this.coordinatorService = coordinatorService;
        this.currentUser = currentUser;
        this.preselectedCoordinator = preselectedCoordinator;
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
        eventLabel = new JLabel("Evento:");
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
        eventComboBox.addActionListener(e -> loadCoordinators());
        coordinatorSlotsLabel = new JLabel();

        eventPanel.add(eventLabel);
        eventPanel.add(eventComboBox);
        eventPanel.add(coordinatorSlotsLabel);
        mainPanel.add(eventPanel, BorderLayout.NORTH);

        availableTableModel = createReadOnlyTableModel(AVAILABLE_COLUMNS);
        availableCoordinatorsTable = createTable(availableTableModel);
        availableScrollPane = new JScrollPane(availableCoordinatorsTable);
        availableScrollPane.setBorder(BorderFactory.createTitledBorder("Coordinadores disponibles"));

        assignedTableModel = createReadOnlyTableModel(ASSIGNED_COLUMNS);
        assignedCoordinatorsTable = createTable(assignedTableModel);
        assignedScrollPane = new JScrollPane(assignedCoordinatorsTable);
        assignedScrollPane.setBorder(BorderFactory.createTitledBorder("Coordinadores asignados"));

        tablesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tablesPanel.add(availableScrollPane);
        tablesPanel.add(assignedScrollPane);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        assignButton = new JButton("Asignar");
        unassignButton = new JButton("Desasignar");
        closeButton = new JButton("Cerrar");
        assignButton.addActionListener(e -> assignSelectedCoordinator());
        unassignButton.addActionListener(e -> unassignSelectedCoordinator());
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
        loadCoordinators();
    }

    private void loadCoordinators() {
        availableTableModel.setRowCount(0);
        assignedTableModel.setRowCount(0);

        Event selectedEvent = getSelectedEvent();
        boolean hasEvent = selectedEvent != null;
        assignButton.setEnabled(hasEvent);
        unassignButton.setEnabled(hasEvent);
        if (!hasEvent) {
            coordinatorSlotsLabel.setText("No events available");
            return;
        }

        int eventId = selectedEvent.getIdEvent();
        for (Coordinator coordinator : assignmentService.getAvailableCoordinators(eventId)) {
            availableTableModel.addRow(new Object[] {
                    coordinator.getIdStaff(), coordinator.getName(), coordinator.getEmail(),
                    coordinator.getPhone(), coordinator.getCompany()
            });
        }

        List<EventAssignment> coordinatorAssignments = assignmentService.getAssignmentsByEvent(eventId).stream()
                .filter(assignment -> assignment.getStaffRole() == StaffRole.COORDINATOR)
                .toList();
        for (EventAssignment assignment : coordinatorAssignments) {
            Coordinator coordinator = coordinatorService.getCoordinatorById(assignment.getIdStaff());
            assignedTableModel.addRow(new Object[] {
                    assignment.getIdAssignment(), assignment.getIdStaff(),
                    coordinator != null ? coordinator.getName() : "Unknown",
                    coordinator != null ? coordinator.getEmail() : ""
            });
        }

        coordinatorSlotsLabel.setText("Coordinators: " + coordinatorAssignments.size()
                + " / " + selectedEvent.getMaxCoordinators());
        selectPreselectedCoordinator();
    }

    private void selectPreselectedCoordinator() {
        if (preselectedCoordinator == null)
            return;

        for (int row = 0; row < availableTableModel.getRowCount(); row++) {
            if ((int) availableTableModel.getValueAt(row, 0) == preselectedCoordinator.getIdStaff()) {
                availableCoordinatorsTable.setRowSelectionInterval(row, row);
                return;
            }
        }
    }

    private void assignSelectedCoordinator() {
        Event selectedEvent = getSelectedEvent();
        int row = availableCoordinatorsTable.getSelectedRow();
        if (selectedEvent == null || row == -1) {
            showWarning("Select an available coordinator to assign.");
            return;
        }

        int coordinatorId = (int) availableTableModel.getValueAt(row, 0);
        Coordinator coordinator = coordinatorService.getCoordinatorById(coordinatorId);
        if (coordinator == null) {
            showWarning("The selected coordinator no longer exists.");
            loadCoordinators();
            return;
        }

        try {
            assignmentService.assign(currentUser, coordinator, selectedEvent.getIdEvent());
            JOptionPane.showMessageDialog(this, "Coordinator assigned successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (BusinessException ex) {
            showError(ex.getMessage());
        }
        loadCoordinators();
    }

    private void unassignSelectedCoordinator() {
        int row = assignedCoordinatorsTable.getSelectedRow();
        if (row == -1) {
            showWarning("Select an assigned coordinator to unassign.");
            return;
        }

        int assignmentId = (int) assignedTableModel.getValueAt(row, 0);
        String coordinatorName = (String) assignedTableModel.getValueAt(row, 2);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Do you want to unassign \"" + coordinatorName + "\"?\n"
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
        loadCoordinators();
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
    private JLabel coordinatorSlotsLabel;
    private JPanel tablesPanel;
    private JTable availableCoordinatorsTable;
    private DefaultTableModel availableTableModel;
    private JScrollPane availableScrollPane;
    private JTable assignedCoordinatorsTable;
    private DefaultTableModel assignedTableModel;
    private JScrollPane assignedScrollPane;
    private JPanel buttonPanel;
    private JButton assignButton;
    private JButton unassignButton;
    private JButton closeButton;
}
