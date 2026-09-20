/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

/**
 *
 * @author urreg
 */
public class Staff {
    private int idStaff;
    private String name;
    private String email;
    private int phone;
    private String position;
    private String password;
    private double salary;

    public Staff(int id, String name, String email, int phone, String position, String password, double salary) {
        this.idStaff = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.password = password;
        this.salary = salary;
    }

    public Staff(String name, String email, int phone, String position, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.password = password;
    }

    public Staff() {
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
