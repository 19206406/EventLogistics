package com.desgroup.interfaces.logicInterfaces;

import java.util.List;

import com.desgroup.models.Coordinator;
import com.desgroup.models.Logistic;

public interface IAvailabilityService {

    List<Logistic> getAvailableLogistics(int eventId);

    List<Coordinator> getAvailableCoordinators(int eventId);

    int getRemainingLogisticSlots(int eventId, int coordinatorId);

}
