/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.EventAssignmentService;
import com.desgroup.logic.EventService;
import com.desgroup.models.Event;
import com.desgroup.models.EventAssignment;
import com.desgroup.models.Place;
import com.desgroup.models.Staff;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author urreg
 */
public class ViewEventsAssignmentCoordinator extends javax.swing.JInternalFrame {

    private final EventAssignmentService assignmentService; 
    private final EventService eventService; 
    private Staff currentUser; 
    private DefaultTableModel tableModel; 
    
    public ViewEventsAssignmentCoordinator(EventAssignmentService assignmentService, EventService eventService, Staff currentUser) {
        super("Asignaciones de eventos", true, true, true, true);
        this.assignmentService = assignmentService;
        this.eventService = eventService;
        this.currentUser = currentUser;
        initComponents();
        setupTable();
        loadAssignedEvents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        scrViewAssignments = new javax.swing.JScrollPane();
        tblViewAssignment = new javax.swing.JTable();
        btnConsultPlace = new javax.swing.JButton();

        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Asignaciones de eventos que usted tiene:");
        lblTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tblViewAssignment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Estado", "Fecha", "Hora"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrViewAssignments.setViewportView(tblViewAssignment);

        btnConsultPlace.setText("Consultar lugar");
        btnConsultPlace.addActionListener(this::btnConsultPlaceActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrViewAssignments, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConsultPlace, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(scrViewAssignments, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnConsultPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultPlaceActionPerformed
        // TODO add your handling code here:
        Event selected = getSelectedEvent();
        if (selected == null) {
            return;
        }
 
        Place place = selected.getPlace();
        String detail = "Lugar: " + place.getPlaceName()
                + "\nDirección: " + place.getAddress()
                + "\nCiudad: " + place.getCity()
                + "\nPaís: " + place.getCountry()
                + "\nCapacidad: " + place.getCapacity() + " personas";
 
        JOptionPane.showMessageDialog(this, detail,
                "Lugar del evento \"" + selected.getName() + "\"", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnConsultPlaceActionPerformed

    
    private void setupTable() {
        String[] columns = {"Id", "Nombre", "Estado", "Fecha", "Hora"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblViewAssignment.setModel(tableModel);
        tblViewAssignment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblViewAssignment.setRowHeight(24);
        tblViewAssignment.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblViewAssignment.getColumnModel().getColumn(1).setPreferredWidth(170);
        tblViewAssignment.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblViewAssignment.getColumnModel().getColumn(3).setPreferredWidth(90);
        tblViewAssignment.getColumnModel().getColumn(4).setPreferredWidth(60);
    }
    
    public void loadAssignedEvents() {
        tableModel.setRowCount(0);
        System.out.println(currentUser.getIdStaff());
        List<EventAssignment> eventIds = assignmentService.getAssignmentsByStaffId(currentUser.getIdStaff());
        for (EventAssignment assignment : eventIds) {
            Event event = eventService.getEventById(assignment.getIdEvent());
            System.out.println(event);
            if (event == null) {
                continue;
            }
            tableModel.addRow(new Object[]{
                event.getIdEvent(),
                event.getName(),
                event.getState(),
                event.getDate(),
                String.format("%02d:00", event.getStartTime())
            });
        }
    }
    
    private Event getSelectedEvent() {
        int row = tblViewAssignment.getSelectedRow(); 
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento para consultar su lugar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null; 
        }
        
        int id = (int) tableModel.getValueAt(row, 0); 
        return eventService.getEventById(id); 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultPlace;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrViewAssignments;
    private javax.swing.JTable tblViewAssignment;
    // End of variables declaration//GEN-END:variables
}
