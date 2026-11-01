package com.desgroup.ui.menus;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.models.Manager;
import com.desgroup.models.Staff;
import com.desgroup.ui.ManageCoordinatorFrame;
import com.desgroup.ui.ManageEventFrame;
import com.desgroup.ui.ViewManagerProfileFrame;

public class ManagerMenu implements RoleMenu {

    private final IEventService eventService;
    private final ICoordinatorService coordinatorService;
    private final IEventAssignmentService assignmentService;

    public ManagerMenu(IEventService eventService, ICoordinatorService coordinatorService,
            IEventAssignmentService assignmentService) {
        this.eventService = eventService;
        this.coordinatorService = coordinatorService;
        this.assignmentService = assignmentService;
    }

    @Override
    public JMenu build(JDesktopPane desktopPane, Staff currentUser) {
        JMenu menu = new JMenu("Manager");

        JMenuItem itemManageEvents = new JMenuItem("Administrar eventos");
        itemManageEvents.addActionListener(e -> show(desktopPane, new ManageEventFrame(eventService, desktopPane)));
        menu.add(itemManageEvents);

        JMenuItem itemManageCoordinators = new JMenuItem("Administrar coordinadores");
        itemManageCoordinators.addActionListener(e -> show(desktopPane,
                new ManageCoordinatorFrame(coordinatorService, eventService, assignmentService, currentUser,
                        desktopPane)));
        menu.add(itemManageCoordinators);

        JMenuItem itemProfile = new JMenuItem("Ver perfil");
        // El cast es seguro: este menú solo se registra para StaffRole.MANAGER.
        itemProfile.addActionListener(e -> show(desktopPane, new ViewManagerProfileFrame((Manager) currentUser)));
        menu.add(itemProfile);

        return menu;
    }
}
