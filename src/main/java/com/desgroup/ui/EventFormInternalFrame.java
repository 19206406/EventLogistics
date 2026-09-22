/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.EventService;
import com.desgroup.models.Event;
import com.desgroup.models.Place;
import com.desgroup.utils.MessagesUi;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author urreg
 */
public class EventFormInternalFrame extends JInternalFrame {
    // Estados que se ofrecen en el combo. Si se necesitan otros, se cambian aquí.
    public static final String[] STATES = {"Programado", "En curso", "Finalizado", "Cancelado"};

    private final EventService service;
    private final ManagerEvent managerEvent;
    private final Event event; // null = se está creando un evento nuevo

    private JTextField txtName;
    private JComboBox<String> cmbState;
    private JTextField txtDate;
    private JTextField txtHour;
    private JTextField txtCountry;
    private JTextField txtCity;
    private JTextField txtPlaceName;
    private JTextField txtAddress;
    private JTextField txtCapacity;

    public EventFormInternalFrame(EventService service, ManagerEvent managerEvent, Event event) {
        super(event == null ? "Crear evento" : "Editar evento — ID: " + event.getIdEvent(),
                false, true, false, false);
        this.service = service;
        this.managerEvent = managerEvent;
        this.event = event;
        initComponents();
        if (event != null) {
            fillFields(event);
        }
        pack();
        setLocation(60, 40);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtName = new JTextField(22);
        cmbState = new JComboBox<>(STATES);
        txtDate = new JTextField(22);
        txtHour = new JTextField(22);
        txtCountry = new JTextField(22);
        txtCity = new JTextField(22);
        txtPlaceName = new JTextField(22);
        txtAddress = new JTextField(22);
        txtCapacity = new JTextField(22);

        int row = 0;
        addRow(panel, gbc, row++, "Nombre del evento:", txtName);
        addRow(panel, gbc, row++, "Estado:", cmbState);
        addRow(panel, gbc, row++, "Fecha (AAAA-MM-DD):", txtDate);
        addRow(panel, gbc, row++, "Hora de inicio (0-23):", txtHour);
        addRow(panel, gbc, row++, "País:", txtCountry);
        addRow(panel, gbc, row++, "Ciudad:", txtCity);
        addRow(panel, gbc, row++, "Nombre del lugar:", txtPlaceName);
        addRow(panel, gbc, row++, "Dirección:", txtAddress);
        addRow(panel, gbc, row++, "Capacidad:", txtCapacity);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSave = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        buttonsPanel.add(btnSave);
        buttonsPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panel.add(buttonsPanel, gbc);

        add(panel);
        getRootPane().setDefaultButton(btnSave);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    // Carga en el formulario los datos actuales del evento que se va a editar.
    private void fillFields(Event event) {
        txtName.setText(event.getName());

        // si el estado no está en la lista (por ejemplo lo creó otro módulo) se agrega
        // para no perderlo al guardar
        if (event.getState() != null && !Arrays.asList(STATES).contains(event.getState())) {
            cmbState.addItem(event.getState());
        }
        cmbState.setSelectedItem(event.getState());

        txtDate.setText(event.getDate() == null ? "" : event.getDate().toString());
        txtHour.setText(String.valueOf(event.getStartTime()));

        Place place = event.getPlace();
        if (place != null) {
            txtCountry.setText(place.getCountry());
            txtCity.setText(place.getCity());
            txtPlaceName.setText(place.getPlaceName());
            txtAddress.setText(place.getAddress());
            txtCapacity.setText(String.valueOf(place.getCapacity()));
        }
    }

    private void save() {
        String name = txtName.getText().trim();
        String state = (String) cmbState.getSelectedItem();
        String dateText = txtDate.getText().trim();
        String hourText = txtHour.getText().trim();
        String country = txtCountry.getText().trim();
        String city = txtCity.getText().trim();
        String placeName = txtPlaceName.getText().trim();
        String address = txtAddress.getText().trim();
        String capacityText = txtCapacity.getText().trim();

        if (name.isEmpty() || dateText.isEmpty() || hourText.isEmpty() || country.isEmpty() || city.isEmpty()
                || placeName.isEmpty() || address.isEmpty() || capacityText.isEmpty()) {
            MessagesUi.showError(this, "Todos los campos son obligatorios.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateText); // formato ISO: AAAA-MM-DD
        } catch (DateTimeParseException ex) {
            MessagesUi.showError(this, "La fecha no es válida. Use el formato AAAA-MM-DD (por ejemplo 2026-10-25).");
            return;
        }

        // \\d{1,2} y \\d{1,9} limitan la cantidad de dígitos, así parseInt nunca se desborda
        if (!hourText.matches("\\d{1,2}") || Integer.parseInt(hourText) > 23) {
            MessagesUi.showError(this, "La hora debe ser un número entero entre 0 y 23.");
            return;
        }

        if (!capacityText.matches("\\d{1,9}") || Integer.parseInt(capacityText) == 0) {
            MessagesUi.showError(this, "La capacidad debe ser un número entero mayor que 0.");
            return;
        }

        int startTime = Integer.parseInt(hourText);
        int capacity = Integer.parseInt(capacityText);

        if (event == null) {
            service.createEvent(name, state, date, startTime, country, city, placeName, address, capacity);
        } else {            
            service.updatedEvent(event.getIdEvent(), name, state, date, startTime, country, city, placeName, address, capacity);
        }

        managerEvent.loadEvents();
        dispose();
    }
}
