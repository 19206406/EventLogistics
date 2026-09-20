/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

/**
 *
 * @author urreg
 */
public class Logistic extends Staff {
    private String zone;
    private int workingHours;
    private String role;
    private int score;

    public Logistic(String zone, int workingHours, String role, int score, int id, String name, String email, int phone,
            String position, String password, double salary) {
        super(id, name, email, phone, position, password, salary);
        this.zone = zone;
        this.workingHours = workingHours;
        this.role = role;
        this.score = score;
    }

    public Logistic(int id, String name, String email, int phone, String position, String password, double salary) {
        super(id, name, email, phone, position, password, salary);
    }

    public Logistic(String name, String email, int phone, String position, String zone, String role, String password) {
        super(name, email, phone, position, password);
        this.zone = zone;
        this.role = role;
    }

    public Logistic() {
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
