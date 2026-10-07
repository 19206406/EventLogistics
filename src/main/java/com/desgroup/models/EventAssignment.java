/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

import com.desgroup.utils.StaffRole;

/**
 *
 * @author urreg
 */
public class EventAssignment {
    private int idAssignment;
    private int idStaff;
    private int idEvent;

    private int idAssignedBy;
    private StaffRole staffRole;

    public EventAssignment() {
    }

    public EventAssignment(int idAssignment, int idStaff, int idEvent) {
        this.idAssignment = idAssignment;
        this.idStaff = idStaff;
        this.idEvent = idEvent;
    }

    public EventAssignment(int idAssignment, int idStaff, int idEvent, int idAssignedBy, StaffRole staffRole) {
        this.idAssignment = idAssignment;
        this.idStaff = idStaff;
        this.idEvent = idEvent;
        this.idAssignedBy = idAssignedBy;
        this.staffRole = staffRole;
    }

    public int getIdAssignment() {
        return idAssignment;
    }

    public void setIdAssignment(int idAssignment) {
        this.idAssignment = idAssignment;
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdAssignedBy() {
        return idAssignedBy;
    }

    public void setIdAssignedBy(int idAssignedBy) {
        this.idAssignedBy = idAssignedBy;
    }

    public StaffRole getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(StaffRole staffRole) {
        this.staffRole = staffRole;
    }

    

}
