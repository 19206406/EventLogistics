package com.desgroup.interfaces.rulesInterfaces;

import com.desgroup.models.Staff;

public interface ISalaryPolicy<T extends Staff> {
    double calculate(T staff);
}
