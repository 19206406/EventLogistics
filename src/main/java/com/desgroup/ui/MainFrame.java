/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.IAssignmentCommandService;
import com.desgroup.interfaces.logicInterfaces.IAssignmentQueryService;
import com.desgroup.interfaces.logicInterfaces.IAvailabilityService;
import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.logicInterfaces.IStaffService;
import com.desgroup.models.Staff;
import com.desgroup.ui.menus.RoleMenu;
import com.desgroup.utils.StaffRole;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author urreg
 */
public class MainFrame extends JFrame {
    private JDesktopPane desktopPane;

    private final IStaffService staffService;
    private final ILogisticService logisticService;
    private final IEventService eventService;
    private final IAssignmentCommandService assignmentCommandService;
    private final IAssignmentQueryService assignmentQueryService;
    private final IAvailabilityService availabilityService;
    private final ICoordinatorService coordinatorService;
    private final Map<StaffRole, RoleMenu> roleMenus;

    private final Staff currentUser;

    private LoginFrame Back;

    public MainFrame(IStaffService staffService, ILogisticService logisticService, IEventService eventService,
            IAssignmentCommandService assignmentCommandService, IAssignmentQueryService assignmentQueryService,
            IAvailabilityService availabilityService, ICoordinatorService coordinatorService,
            Map<StaffRole, RoleMenu> roleMenus, Staff currentUser) {
        this.staffService = staffService;
        this.logisticService = logisticService;
        this.eventService = eventService;
        this.assignmentCommandService = assignmentCommandService;
        this.assignmentQueryService = assignmentQueryService;
        this.availabilityService = availabilityService;
        this.coordinatorService = coordinatorService;
        this.roleMenus = roleMenus;
        this.currentUser = currentUser;
        initComponents();
    }

    private void initComponents() {
        setTitle("Event Logistics - Panel Principal");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
        setJMenuBar(buildMenuBar());
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú del rol del usuario actual
        RoleMenu roleMenu = roleMenus.get(currentUser.getStaffRole());
        if (roleMenu != null) {
            menuBar.add(roleMenu.build(desktopPane, currentUser));
        }

        // Menú Opciones
        JMenu menuOptions = new JMenu("Opciones");
        JMenuItem itemExit = new JMenuItem("Salir");
        itemExit.addActionListener(e -> returnBack());
        menuOptions.add(itemExit);

        menuBar.add(menuOptions);

        return menuBar;
    }

    public void returnBack() {
        Back = new LoginFrame(staffService, logisticService, eventService, assignmentCommandService,
                assignmentQueryService, availabilityService,
                coordinatorService, roleMenus);
        Back.setVisible(true);
        this.dispose();
    }
}
