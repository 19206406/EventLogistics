/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.EventService;
import com.desgroup.models.Event;
import com.desgroup.models.Place;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Olger Mercado
 */
public class ManagerEvent extends javax.swing.JInternalFrame {
    private final EventService service;
    private DefaultTableModel tableModel;

    public ManagerEvent(EventService service) {
        super("Administrar eventos", true, true, true, true);
        this.service = service;
        initComponents();
        setupTable();
        loadEvents();
        setLocation(30, 30);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCrearEvento = new javax.swing.JButton();
        btnEditarEvento = new javax.swing.JButton();
        btnEliminarEvento = new javax.swing.JButton();
        btnConsultarLugar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblManageEvent = new javax.swing.JTable();

        btnCrearEvento.setText("Crear Evento");
        btnCrearEvento.addActionListener(this::btnCrearEventoActionPerformed);

        btnEditarEvento.setText("Editar Evento");
        btnEditarEvento.addActionListener(this::btnEditarEventoActionPerformed);

        btnEliminarEvento.setText("Eliminar evento");
        btnEliminarEvento.addActionListener(this::btnEliminarEventoActionPerformed);

        btnConsultarLugar.setText("Consultar  Lugar");
        btnConsultarLugar.addActionListener(this::btnConsultarLugarActionPerformed);

        tblManageEvent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre ", "Estado", "Fecha", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblManageEvent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCrearEvento)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarEvento)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarEvento)
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultarLugar)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearEvento)
                    .addComponent(btnEditarEvento)
                    .addComponent(btnEliminarEvento)
                    .addComponent(btnConsultarLugar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearEventoActionPerformed
        openForm(null);
    }//GEN-LAST:event_btnCrearEventoActionPerformed

    private void btnEditarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEventoActionPerformed
        Event selected = getSelectedEvent("editar");
        if (selected != null) {
            openForm(selected);
        }
    }//GEN-LAST:event_btnEditarEventoActionPerformed

    private void btnEliminarEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEventoActionPerformed
        Event selected = getSelectedEvent("eliminar");
        if (selected == null) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "¿Desea eliminar el evento \"" + selected.getName() + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            service.deleteEventById(selected.getIdEvent());
            loadEvents();
        }
    }//GEN-LAST:event_btnEliminarEventoActionPerformed

    private void btnConsultarLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarLugarActionPerformed
        Event selected = getSelectedEvent("consultar su lugar");
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
    }//GEN-LAST:event_btnConsultarLugarActionPerformed

    private void setupTable() {
        String[] columns = {"Id", "Nombre", "Estado", "Fecha", "Hora"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblManageEvent.setModel(tableModel);
        tblManageEvent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblManageEvent.setRowHeight(24);
        tblManageEvent.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblManageEvent.getColumnModel().getColumn(1).setPreferredWidth(170);
        tblManageEvent.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblManageEvent.getColumnModel().getColumn(3).setPreferredWidth(90);
        tblManageEvent.getColumnModel().getColumn(4).setPreferredWidth(60);
    }

    public void loadEvents() {
        tableModel.setRowCount(0);
        List<Event> events = service.getAllEvents();
        for (Event event : events) {
            tableModel.addRow(new Object[]{
                event.getIdEvent(),
                event.getName(),
                event.getState(),
                event.getDate(),
                String.format("%02d:00", event.getStartTime())
            });
        }
    }

    private Event getSelectedEvent(String action) {
        int row = tblManageEvent.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento para " + action + ".",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        return service.getEventById(id);
    }

    private void openForm(Event event) {
        EventFormInternalFrame form = new EventFormInternalFrame(service, this, event);
        getDesktopPane().add(form);
        form.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultarLugar;
    private javax.swing.JButton btnCrearEvento;
    private javax.swing.JButton btnEditarEvento;
    private javax.swing.JButton btnEliminarEvento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblManageEvent;
    // End of variables declaration//GEN-END:variables
}
