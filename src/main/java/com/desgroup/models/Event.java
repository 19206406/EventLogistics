/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

import java.time.LocalDate;

/**
 *
 * @author urreg
 */
public class Event {
    private int idEvent;
    private String name;
    private Place place;
    private String state;
    private LocalDate date;
    private int startTime;

    private int endTime;
    private int maxCoordinators;
    private int maxLogistics;
    private int maxLogisticsPerCoordinator;

    public Event() {
    }

    public Event(int idEvent, String name, Place place, String state, LocalDate date, int startTime) {
        this.idEvent = idEvent;
        this.name = name;
        this.place = place;
        this.state = state;
        this.date = date;
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getMaxCoordinators() {
        return maxCoordinators;
    }

    public void setMaxCoordinators(int maxCoordinators) {
        this.maxCoordinators = maxCoordinators;
    }

    public int getMaxLogistics() {
        return maxLogistics;
    }

    public void setMaxLogistics(int maxLogistics) {
        this.maxLogistics = maxLogistics;
    }

    public int getMaxLogisticsPerCoordinator() {
        return maxLogisticsPerCoordinator;
    }

    public void setMaxLogisticsPerCoordinator(int maxLogisticsPerCoordinator) {
        this.maxLogisticsPerCoordinator = maxLogisticsPerCoordinator;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}
