/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.models;

/**
 *
 * @author urreg
 */
public class Coordinator extends Staff {
    private String company;

    public Coordinator(String name, String email, int phone, String position, String password, String company) {
        super(name, email, phone, position, password);
        this.company = company;
    }

    public Coordinator() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
