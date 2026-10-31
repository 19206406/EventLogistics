package com.desgroup.logic;

import com.desgroup.interfaces.rulesInterfaces.ISalaryPolicy;
import com.desgroup.models.Logistic;

public class LogisticSalaryPolicy implements ISalaryPolicy<Logistic> {

    private final double hourlyRate;

    public LogisticSalaryPolicy(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculate(Logistic staff) {
        return staff.getWorkingHours() * hourlyRate;
    }

}
