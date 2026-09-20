/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

/**
 *
 * @author urreg
 */
public class Manager extends Staff {

    public String company; 

    public Manager(String company, int id, String name, String email, int phone, String position, String password, double salary) {
        super(id, name, email, phone, position, password, salary);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
