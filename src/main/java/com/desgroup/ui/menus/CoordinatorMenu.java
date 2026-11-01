package com.desgroup.ui.menus;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.models.Staff;
import com.desgroup.ui.AssignedEventsInternalFrame;
import com.desgroup.ui.ManageLogisticFrame;
import com.desgroup.ui.ViewCoordinatorProfile;

public class CoordinatorMenu implements RoleMenu {

    private final ILogisticService logisticService;
    private final ICoordinatorService coordinatorService;
    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;

    public CoordinatorMenu(ILogisticService logisticService, ICoordinatorService coordinatorService,
            IEventService eventService, IEventAssignmentService assignmentService) {
        this.logisticService = logisticService;
        this.coordinatorService = coordinatorService;
        this.eventService = eventService;
        this.assignmentService = assignmentService;
    }

    @Override
    public JMenu build(JDesktopPane desktopPane, Staff currentUser) {
        JMenu menu = new JMenu("Coordinador");
        SingleFrameOpener assignedEvents = new SingleFrameOpener();

        JMenuItem itemManageLogistics = new JMenuItem("Administrar logisticos");
        itemManageLogistics.addActionListener(e -> show(desktopPane,
                new ManageLogisticFrame(logisticService, desktopPane, eventService, assignmentService, currentUser)));
        menu.add(itemManageLogistics);

        JMenuItem itemEvents = new JMenuItem("Ver eventos asignados");
        itemEvents.addActionListener(e -> assignedEvents.open(desktopPane,
                () -> new AssignedEventsInternalFrame(assignmentService, eventService, currentUser)));
        menu.add(itemEvents);

        JMenuItem itemProfile = new JMenuItem("Ver perfil");
        itemProfile.addActionListener(e -> show(desktopPane,
                new ViewCoordinatorProfile(coordinatorService.getCoordinatorById(currentUser.getId()),
                        coordinatorService)));
        menu.add(itemProfile);

        return menu;
    }
}
