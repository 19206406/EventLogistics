package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Event;
import com.desgroup.models.Place;

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
 * @author Olger Mercado
 */
public class ManageEventFrame extends JInternalFrame {
    private final IEventService eventService;
    private final JDesktopPane desktopPane;

    public ManageEventFrame(IEventService eventService, JDesktopPane desktopPane) {
        super("Administrar eventos", true, true, true, true);
        this.eventService = eventService;
        this.desktopPane = desktopPane;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadEvents();
        setSize(860, 500);
        setLocation(30, 30);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createEventButton = new JButton("Crear evento");
        JButton updateEventButton = new JButton("Editar evento");
        JButton deleteEventButton = new JButton("Eliminar evento");
        JButton consultPlaceButton = new JButton("Consultar lugar");

        createEventButton.addActionListener(e -> openCreateForm());
        updateEventButton.addActionListener(e -> openUpdateForm());
        deleteEventButton.addActionListener(e -> deleteSelectedEvent());
        consultPlaceButton.addActionListener(e -> showSelectedEventPlace());

        buttonPanel.add(createEventButton);
        buttonPanel.add(updateEventButton);
        buttonPanel.add(deleteEventButton);
        buttonPanel.add(consultPlaceButton);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columns = { "Id", "Nombre", "Estado", "Fecha", "Hora inicio", "Hora fin", "Lugar", "Ciudad",
                "Máx. coord.", "Máx. logíst.", "Logíst./coord." };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        eventTable = new JTable(tableModel);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventTable.setRowHeight(24);
        eventTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        eventTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        eventTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        eventTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        eventTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        eventTable.getColumnModel().getColumn(5).setPreferredWidth(70);
        eventTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        eventTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        eventTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        eventTable.getColumnModel().getColumn(9).setPreferredWidth(80);
        eventTable.getColumnModel().getColumn(10).setPreferredWidth(90);

        JScrollPane scrollPanelTable = new JScrollPane(eventTable);
        mainPanel.add(scrollPanelTable, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void loadEvents() {
        tableModel.setRowCount(0);
        List<Event> events = eventService.getAllEvents();
        for (Event event : events) {
            Place place = event.getPlace();
            tableModel.addRow(new Object[] {
                    event.getId(),
                    event.getName(),
                    event.getState(),
                    event.getDate(),
                    String.format("%02d:00", event.getStartTime()),
                    String.format("%02d:00", event.getEndTime()),
                    place == null ? "" : place.getPlaceName(),
                    place == null ? "" : place.getCity(),
                    event.getMaxCoordinators(),
                    event.getMaxLogistics(),
                    event.getMaxLogisticsPerCoordinator()
            });
        }
    }

    private void openCreateForm() {
        EventFormInternalFrame form = new EventFormInternalFrame(eventService, this, null);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void openUpdateForm() {
        Event event = getSelectedEvent("editar");
        if (event == null) {
            return;
        }
        EventFormInternalFrame form = new EventFormInternalFrame(eventService, this, event);
        desktopPane.add(form);
        form.setVisible(true);
    }

    private void deleteSelectedEvent() {
        Event event = getSelectedEvent("eliminar");
        if (event == null) {
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el evento \"" + event.getName() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            eventService.deleteEventById(event.getId());
            loadEvents();
        }
    }

    private void showSelectedEventPlace() {
        Event event = getSelectedEvent("consultar su lugar");
        if (event == null) {
            return;
        }
        Place place = event.getPlace();
        String detail = "Lugar: " + place.getPlaceName()
                + "\nDirección: " + place.getAddress()
                + "\nCiudad: " + place.getCity()
                + "\nPaís: " + place.getCountry()
                + "\nCapacidad: " + place.getCapacity() + " personas";
        JOptionPane.showMessageDialog(this, detail,
                "Lugar del evento \"" + event.getName() + "\"", JOptionPane.INFORMATION_MESSAGE);
    }

    private Event getSelectedEvent(String action) {
        int row = eventTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento para " + action + ".",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        return eventService.getEventById(id);
    }

    private JTable eventTable;
    private DefaultTableModel tableModel;
}
