/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Event;
import com.desgroup.models.Place;
import com.desgroup.utils.BusinessException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 *
 * @author urreg
 */
public class EventFormInternalFrame extends JInternalFrame {
    public static final String[] STATES = { "Programado", "En curso", "Finalizado", "Cancelado" };
    private final IEventService service;
    private final ManageEventFrame manageEventFrame;
    private final Event event;

    public EventFormInternalFrame(IEventService service, ManageEventFrame manageEventFrame, Event event) {
        super(getTitle(event), false, true, false, false);
        this.service = service;
        this.manageEventFrame = manageEventFrame;
        this.event = event;
        initComponents();
        if (event != null) {
            fillFields(event);
        }
        pack();
        setLocation(60, 40);
    }

    private static String getTitle(Event event) {
        if (event == null) {
            return "Crear evento";
        } else {
            return "Editar evento — ID: " + event.getId();
        }
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
        txtEndHour = new JTextField(22);
        txtMaxCoordinators = new JTextField(22);
        txtMaxLogistics = new JTextField(22);
        txtMaxLogisticsPerCoordinator = new JTextField(22);
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
        addRow(panel, gbc, row++, "Hora de fin (1-24):", txtEndHour);
        addRow(panel, gbc, row++, "Máx. coordinadores:", txtMaxCoordinators);
        addRow(panel, gbc, row++, "Máx. logísticos:", txtMaxLogistics);
        addRow(panel, gbc, row++, "Máx. logísticos por coordinador:", txtMaxLogisticsPerCoordinator);
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

    private void fillFields(Event event) {
        txtName.setText(event.getName());

        if (event.getState() != null && !Arrays.asList(STATES).contains(event.getState())) {
            cmbState.addItem(event.getState());
        }
        cmbState.setSelectedItem(event.getState());

        txtDate.setText(event.getDate() == null ? "" : event.getDate().toString());
        txtHour.setText(String.valueOf(event.getStartTime()));
        txtEndHour.setText(String.valueOf(event.getEndTime()));
        txtMaxCoordinators.setText(String.valueOf(event.getMaxCoordinators()));
        txtMaxLogistics.setText(String.valueOf(event.getMaxLogistics()));
        txtMaxLogisticsPerCoordinator.setText(String.valueOf(event.getMaxLogisticsPerCoordinator()));

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
        String endHourText = txtEndHour.getText().trim();
        String maxCoordinatorsText = txtMaxCoordinators.getText().trim();
        String maxLogisticsText = txtMaxLogistics.getText().trim();
        String maxLogisticsPerCoordinatorText = txtMaxLogisticsPerCoordinator.getText().trim();

        if (name.isEmpty() || dateText.isEmpty() || hourText.isEmpty() || country.isEmpty() || city.isEmpty()
                || placeName.isEmpty() || address.isEmpty() || capacityText.isEmpty() || endHourText.isEmpty()
                || maxCoordinatorsText.isEmpty() || maxLogisticsText.isEmpty()
                || maxLogisticsPerCoordinatorText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateText);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha no es válida. Use el formato AAAA-MM-DD (por ejemplo 2026-10-25).",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!hourText.matches("\\d{1,2}") || Integer.parseInt(hourText) > 23) {
            JOptionPane.showMessageDialog(this,
                    "La hora debe ser un número entero entre 0 y 23.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!capacityText.matches("\\d{1,9}") || Integer.parseInt(capacityText) == 0) {
            JOptionPane.showMessageDialog(this,
                    "La capacidad debe ser un número entero mayor que 0.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!endHourText.matches("\\d{1,2}") || Integer.parseInt(endHourText) > 24) {
            JOptionPane.showMessageDialog(this,
                    "La hora de fin debe ser un número entero entre 1 y 24.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!maxCoordinatorsText.matches("\\d{1,9}") || !maxLogisticsText.matches("\\d{1,9}")
                || !maxLogisticsPerCoordinatorText.matches("\\d{1,9}")) {
            JOptionPane.showMessageDialog(this,
                    "Los máximos de coordinadores y logísticos deben ser números enteros.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int startTime = Integer.parseInt(hourText);
        int capacity = Integer.parseInt(capacityText);
        int endTime = Integer.parseInt(endHourText);
        int maxCoordinators = Integer.parseInt(maxCoordinatorsText);
        int maxLogistics = Integer.parseInt(maxLogisticsText);
        int maxLogisticsPerCoordinator = Integer.parseInt(maxLogisticsPerCoordinatorText);

        try {
            if (event == null) {
                service.createEvent(name, state, date, startTime, country, city, placeName, address, capacity,
                        endTime, maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);
            } else {
                service.updateEvent(event.getId(), name, state, date, startTime, country, city, placeName,
                        address, capacity, endTime, maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);
            }
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        manageEventFrame.loadEvents();
        dispose();
    }

    private JTextField txtName;
    private JComboBox<String> cmbState;
    private JTextField txtDate;
    private JTextField txtHour;
    private JTextField txtEndHour;
    private JTextField txtMaxCoordinators;
    private JTextField txtMaxLogistics;
    private JTextField txtMaxLogisticsPerCoordinator;
    private JTextField txtCountry;
    private JTextField txtCity;
    private JTextField txtPlaceName;
    private JTextField txtAddress;
    private JTextField txtCapacity;
}
