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
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Event;
import com.desgroup.models.EventState;
import com.desgroup.models.Place;
import java.time.LocalDate;

/**
 *
 * @author urreg
 */
public class EventFormInternalFrame extends JInternalFrame {
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
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtName = new JTextField(22);
        cmbState = new JComboBox<>(EventState.values());
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
        addRow(formPanel, gbc, row++, "Nombre del evento:", txtName);
        addRow(formPanel, gbc, row++, "Estado:", cmbState);
        addRow(formPanel, gbc, row++, "Fecha (AAAA-MM-DD):", txtDate);
        addRow(formPanel, gbc, row++, "Hora de inicio (0-23):", txtHour);
        addRow(formPanel, gbc, row++, "Hora de fin (1-24):", txtEndHour);
        addRow(formPanel, gbc, row++, "Máx. coordinadores:", txtMaxCoordinators);
        addRow(formPanel, gbc, row++, "Máx. logísticos:", txtMaxLogistics);
        addRow(formPanel, gbc, row++, "Máx. logísticos por coordinador:", txtMaxLogisticsPerCoordinator);
        addRow(formPanel, gbc, row++, "País:", txtCountry);
        addRow(formPanel, gbc, row++, "Ciudad:", txtCity);
        addRow(formPanel, gbc, row++, "Nombre del lugar:", txtPlaceName);
        addRow(formPanel, gbc, row++, "Dirección:", txtAddress);
        addRow(formPanel, gbc, row++, "Capacidad:", txtCapacity);

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
        formPanel.add(buttonsPanel, gbc);

        add(formPanel);
        getRootPane().setDefaultButton(btnSave);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel fieldLabel = new JLabel(labelText);
        panel.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void fillFields(Event event) {
        txtName.setText(event.getName());

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
        if (UiActions.run(this, this::saveEvent)) {
            UiActions.run(this, manageEventFrame::loadEvents);
            dispose();
        }
    }

    private void saveEvent() {
        String name = FormInput.text(txtName);
        EventState state = (EventState) cmbState.getSelectedItem();
        LocalDate date = FormInput.parseDate(txtDate, "fecha");
        int startTime = FormInput.parseInt(txtHour, "hora de inicio");
        int endTime = FormInput.parseInt(txtEndHour, "hora de fin");
        String country = FormInput.text(txtCountry);
        String city = FormInput.text(txtCity);
        String placeName = FormInput.text(txtPlaceName);
        String address = FormInput.text(txtAddress);
        int capacity = FormInput.parseInt(txtCapacity, "capacidad");
        int maxCoordinators = FormInput.parseInt(txtMaxCoordinators, "máximo de coordinadores");
        int maxLogistics = FormInput.parseInt(txtMaxLogistics, "máximo de logísticos");
        int maxLogisticsPerCoordinator = FormInput.parseInt(txtMaxLogisticsPerCoordinator,
                "máximo de logísticos por coordinador");

        if (event == null) {
            service.createEvent(name, state, date, startTime, country, city, placeName, address, capacity,
                    endTime, maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);
        } else {
            service.updateEvent(event.getId(), name, state, date, startTime, country, city, placeName,
                    address, capacity, endTime, maxCoordinators, maxLogistics, maxLogisticsPerCoordinator);
        }
    }

    private JTextField txtName;
    private JComboBox<EventState> cmbState;
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
