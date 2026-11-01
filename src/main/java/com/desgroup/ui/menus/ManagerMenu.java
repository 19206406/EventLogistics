package com.desgroup.ui.menus;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.desgroup.interfaces.logicInterfaces.IAssignmentCommandService;
import com.desgroup.interfaces.logicInterfaces.IAssignmentQueryService;
import com.desgroup.interfaces.logicInterfaces.IAvailabilityService;
import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.IManagerService;
import com.desgroup.models.Staff;
import com.desgroup.ui.ManageCoordinatorFrame;
import com.desgroup.ui.ManageEventFrame;
import com.desgroup.ui.ViewManagerProfileFrame;

public class ManagerMenu implements RoleMenu {

    private final IEventService eventService;
    private final ICoordinatorService coordinatorService;
    private final IAssignmentCommandService assignmentCommandService;
    private final IAssignmentQueryService assignmentQueryService;
    private final IAvailabilityService availabilityService;
    private final IManagerService managerService;

    public ManagerMenu(IEventService eventService, ICoordinatorService coordinatorService,
            IAssignmentCommandService assignmentCommandService,
            IAssignmentQueryService assignmentQueryService,
            IAvailabilityService availabilityService, IManagerService managerService) {
        this.eventService = eventService;
        this.coordinatorService = coordinatorService;
        this.assignmentCommandService = assignmentCommandService;
        this.assignmentQueryService = assignmentQueryService;
        this.availabilityService = availabilityService;
        this.managerService = managerService;
    }

    @Override
    public JMenu build(JDesktopPane desktopPane, Staff currentUser) {
        JMenu menu = new JMenu("Manager");

        JMenuItem itemManageEvents = new JMenuItem("Administrar eventos");
        itemManageEvents.addActionListener(e -> show(desktopPane, new ManageEventFrame(eventService, desktopPane)));
        menu.add(itemManageEvents);

        JMenuItem itemManageCoordinators = new JMenuItem("Administrar coordinadores");
        itemManageCoordinators.addActionListener(e -> show(desktopPane,
                new ManageCoordinatorFrame(coordinatorService, eventService, assignmentCommandService,
                        assignmentQueryService, availabilityService, currentUser, desktopPane)));
        menu.add(itemManageCoordinators);

        JMenuItem itemProfile = new JMenuItem("Ver perfil");
        itemProfile.addActionListener(e -> show(desktopPane,
                new ViewManagerProfileFrame(managerService.getManagerById(currentUser.getId()))));
        menu.add(itemProfile);

        return menu;
    }
}
