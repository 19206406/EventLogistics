package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Logistic;
import com.desgroup.models.Place;
import com.desgroup.models.Staff;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Internal frame that shows the events assigned to the current user.
 * Shared by coordinators and logistics: the content depends only on the
 * logged staff member, so the same component serves both menus.
 *
 * @author urreg
 */
public class AssignedEventsInternalFrame extends JInternalFrame {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String EMPTY_VALUE = "-";

    private final IEventAssignmentService assignmentService;
    private final IEventService eventService;
    private final Staff currentUser;

    private final AssignedEventsTableModel tableModel = new AssignedEventsTableModel();
    private JTable eventsTable;
    private JLabel summaryLabel;
    private JLabel placeNameValue;
    private JLabel addressValue;
    private JLabel cityValue;
    private JLabel countryValue;
    private JLabel capacityValue;

    public AssignedEventsInternalFrame(IEventAssignmentService assignmentService, IEventService eventService,
            Staff currentUser) {
        super("Eventos asignados", true, true, true, true);
        this.assignmentService = Objects.requireNonNull(assignmentService);
        this.eventService = Objects.requireNonNull(eventService);
        this.currentUser = Objects.requireNonNull(currentUser);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadAssignedEvents();
        setSize(760, 500);
        setLocation(40, 40);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(buildHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(buildTablePanel(), BorderLayout.CENTER);
        mainPanel.add(buildPlaceDetailPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel buildHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));

        JLabel titleLabel = new JLabel("Eventos asignados a " + currentUser.getName()
                + " (" + getRoleLabel(currentUser) + ")");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 15f));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Actualizar");
        refreshButton.setToolTipText("Volver a cargar los eventos asignados");
        refreshButton.addActionListener(e -> loadAssignedEvents());
        headerPanel.add(refreshButton, BorderLayout.EAST);

        summaryLabel = new JLabel();
        headerPanel.add(summaryLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JScrollPane buildTablePanel() {
        eventsTable = new JTable(tableModel);
        eventsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventsTable.setAutoCreateRowSorter(true);
        eventsTable.setFillsViewportHeight(true);
        eventsTable.setRowHeight(24);
        eventsTable.getTableHeader().setReorderingAllowed(false);
        eventsTable.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        eventsTable.getColumnModel().getColumn(AssignedEventsTableModel.COLUMN_START_TIME)
                .setCellRenderer(centerRenderer);
        eventsTable.getColumnModel().getColumn(AssignedEventsTableModel.COLUMN_END_TIME)
                .setCellRenderer(centerRenderer);

        eventsTable.getColumnModel().getColumn(AssignedEventsTableModel.COLUMN_ID).setPreferredWidth(40);
        eventsTable.getColumnModel().getColumn(AssignedEventsTableModel.COLUMN_NAME).setPreferredWidth(200);

        eventsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showPlaceOf(getSelectedEvent());
            }
        });

        return new JScrollPane(eventsTable);
    }

    private JPanel buildPlaceDetailPanel() {
        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Lugar del evento seleccionado"));

        placeNameValue = new JLabel();
        addressValue = new JLabel();
        cityValue = new JLabel();
        countryValue = new JLabel();
        capacityValue = new JLabel();

        addDetailRow(detailPanel, 0, 0, "Lugar:", placeNameValue);
        addDetailRow(detailPanel, 0, 1, "Dirección:", addressValue);
        addDetailRow(detailPanel, 0, 2, "Capacidad:", capacityValue);
        addDetailRow(detailPanel, 2, 0, "Ciudad:", cityValue);
        addDetailRow(detailPanel, 2, 1, "País:", countryValue);

        showPlaceOf(null);
        return detailPanel;
    }

    private void addDetailRow(JPanel panel, int column, int row, String labelText, JLabel valueLabel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.insets = new Insets(2, 6, 2, 6);
        constraints.anchor = GridBagConstraints.LINE_START;

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        constraints.gridx = column;
        panel.add(label, constraints);

        constraints.gridx = column + 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(valueLabel, constraints);
    }

    private void loadAssignedEvents() {
        List<Event> events = new ArrayList<>();
        for (EventAssignment assignment : assignmentService.getAssignmentsByStaffId(currentUser.getIdStaff())) {
            Event event = eventService.getEventById(assignment.getIdEvent());
            if (event != null) {
                events.add(event);
            }
        }
        tableModel.setEvents(events);
        updateSummary(events.size());
        showPlaceOf(null);
    }

    private void updateSummary(int eventCount) {
        if (eventCount == 0) {
            summaryLabel.setText("No tiene eventos asignados por el momento.");
        } else if (eventCount == 1) {
            summaryLabel.setText("Tiene 1 evento asignado. Seleccione uno para ver su lugar.");
        } else {
            summaryLabel.setText("Tiene " + eventCount + " eventos asignados. Seleccione uno para ver su lugar.");
        }
    }

    private Event getSelectedEvent() {
        int viewRow = eventsTable.getSelectedRow();
        if (viewRow == -1) {
            return null;
        }
        return tableModel.getEventAt(eventsTable.convertRowIndexToModel(viewRow));
    }

    private void showPlaceOf(Event event) {
        Place place = event == null ? null : event.getPlace();
        if (place == null) {
            placeNameValue.setText(EMPTY_VALUE);
            addressValue.setText(EMPTY_VALUE);
            cityValue.setText(EMPTY_VALUE);
            countryValue.setText(EMPTY_VALUE);
            capacityValue.setText(EMPTY_VALUE);
            return;
        }
        placeNameValue.setText(place.getPlaceName());
        addressValue.setText(place.getAddress());
        cityValue.setText(place.getCity());
        countryValue.setText(place.getCountry());
        capacityValue.setText(place.getCapacity() + " personas");
    }

    private static String getRoleLabel(Staff staff) {
        if (staff instanceof Coordinator) {
            return "Coordinador";
        }
        if (staff instanceof Logistic) {
            return "Logístico";
        }
        return staff.getPosition();
    }

    private static String formatHour(int hour) {
        return String.format("%02d:00", hour);
    }

    /**
     * Read-only table model backed by the list of assigned events.
     */
    private static final class AssignedEventsTableModel extends AbstractTableModel {

        static final int COLUMN_ID = 0;
        static final int COLUMN_NAME = 1;
        static final int COLUMN_STATE = 2;
        static final int COLUMN_DATE = 3;
        static final int COLUMN_START_TIME = 4;
        static final int COLUMN_END_TIME = 5;

        private static final String[] COLUMN_NAMES = {
                "Id", "Nombre", "Estado", "Fecha", "Hora inicio", "Hora fin"
        };
        private static final Class<?>[] COLUMN_TYPES = {
                Integer.class, String.class, String.class, LocalDate.class, String.class, String.class
        };

        private List<Event> events = Collections.emptyList();

        void setEvents(List<Event> events) {
            this.events = List.copyOf(events);
            fireTableDataChanged();
        }

        Event getEventAt(int row) {
            return events.get(row);
        }

        @Override
        public int getRowCount() {
            return events.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Class<?> getColumnClass(int column) {
            return COLUMN_TYPES[column];
        }

        @Override
        public Object getValueAt(int row, int column) {
            Event event = events.get(row);
            return switch (column) {
                case COLUMN_ID -> event.getIdEvent();
                case COLUMN_NAME -> event.getName();
                case COLUMN_STATE -> event.getState();
                case COLUMN_DATE -> event.getDate();
                case COLUMN_START_TIME -> formatHour(event.getStartTime());
                case COLUMN_END_TIME -> event.getEndTime() > 0 ? formatHour(event.getEndTime()) : "Sin definir";
                default -> throw new IllegalArgumentException("Unknown column: " + column);
            };
        }
    }

    /**
     * Renders {@link LocalDate} cells with the local date format.
     */
    private static final class LocalDateRenderer extends DefaultTableCellRenderer {

        LocalDateRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        protected void setValue(Object value) {
            setText(value instanceof LocalDate date ? DATE_FORMAT.format(date) : EMPTY_VALUE);
        }
    }
}
