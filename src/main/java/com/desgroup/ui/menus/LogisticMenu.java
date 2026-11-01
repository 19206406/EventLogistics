package com.desgroup.ui.menus;

import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.desgroup.interfaces.logicInterfaces.IAssignmentQueryService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.models.Staff;
import com.desgroup.ui.AssignedEventsInternalFrame;
import com.desgroup.ui.ViewLogisticProfile;

public class LogisticMenu implements RoleMenu {

    private final IAssignmentQueryService assignmentQueryService;
    private final IEventService eventService;
    private final ILogisticService logisticService;

    public LogisticMenu(IAssignmentQueryService assignmentQueryService, IEventService eventService,
            ILogisticService logisticService) {
        this.assignmentQueryService = assignmentQueryService;
        this.eventService = eventService;
        this.logisticService = logisticService;
    }

    @Override
    public JMenu build(JDesktopPane desktopPane, Staff currentUser) {
        JMenu menu = new JMenu("Logistico");
        SingleFrameOpener assignedEvents = new SingleFrameOpener();

        JMenuItem itemEvents = new JMenuItem("Ver eventos asignados");
        itemEvents.addActionListener(e -> assignedEvents.open(desktopPane,
                () -> new AssignedEventsInternalFrame(assignmentQueryService, eventService, currentUser)));
        menu.add(itemEvents);

        JMenuItem itemProfile = new JMenuItem("Ver perfil");
        itemProfile.addActionListener(e -> show(desktopPane,
                new ViewLogisticProfile(logisticService.getLogisticById(currentUser.getId()), logisticService)));
        menu.add(itemProfile);

        return menu;
    }
}
