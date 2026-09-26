package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.Logistic;

public interface ILogisticService {

        List<Logistic> getAllLogistics();

        Logistic getLogisticById(int id);

        void createLogistic(String name, String email, String phone, String position, String zone,
                        String role, String password, int score, int workingHours);

        void updatedLogistic(int id, String name, String email, String phone, String position, String zone,
                        String role, String password, int score, int workingHours);

        void deleteLogistic(int id);

        void recordHoursWorked(int id, int hours);
}
