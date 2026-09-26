package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.Coordinator;

public interface ICoordinatorService {

        List<Coordinator> getAllCoordinators();

        Coordinator getCoordinatorById(int id);

        void createCoordinator(String name, String email, String phone, String position, String password,
                        String company);

        void updatedCoordinator(String name, String email, String phone, String position, String password,
                        String company);

        void deleteCoordinator(int id);
}
