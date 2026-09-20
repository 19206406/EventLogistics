/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

/**
 *
 * @author urreg
 */
public class EventAssignment {
    private int idAssignment;
    private int idStaff;
    private int idEvent;

    public EventAssignment() {
    }

    public EventAssignment(int idStaff, int idEvent) {
        this.idStaff = idStaff;
        this.idEvent = idEvent;
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

}
