/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.interfaces.logicInterfaces.ICoordinatorService;
import com.desgroup.interfaces.logicInterfaces.IEventAssignmentService;
import com.desgroup.interfaces.logicInterfaces.IEventService;
import com.desgroup.interfaces.logicInterfaces.ILogisticService;
import com.desgroup.interfaces.logicInterfaces.IStaffSalary;
import com.desgroup.interfaces.logicInterfaces.IStaffService;
import com.desgroup.logic.CoordinatorService;
import com.desgroup.logic.EventAssignmentService;
import com.desgroup.logic.EventService;
import com.desgroup.logic.LogisticService;
import com.desgroup.models.Logistic;
import com.desgroup.models.Staff;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import com.desgroup.models.Coordinator;
import com.desgroup.models.Manager;

/**
 *
 * @author urreg
 */
public class MainFrame extends JFrame {
    private JDesktopPane desktopPane;

    private final IStaffService staffService;
    private final ILogisticService logisticService;
    private final IEventService eventService;
    private final IEventAssignmentService assignmentService;
    private final ICoordinatorService coordinatorService;
    private final IStaffSalary salaryService;

    private final Staff currentUser;

    private LoginFrame Back;

    public MainFrame(IStaffService staffService, ILogisticService logisticService, IEventService eventService,
            IEventAssignmentService assignmentService, ICoordinatorService coordinatorService,
            IStaffSalary salaryService, Staff currentUser) {
        this.staffService = staffService;
        this.logisticService = logisticService;
        this.eventService = eventService;
        this.assignmentService = assignmentService;
        this.coordinatorService = coordinatorService;
        this.salaryService = salaryService;
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

        // Menú logistico
        JMenu menuLogistic = new JMenu("Logistico");

        JMenuItem itemEventsAssignedLogistic = new JMenuItem("Ver eventos asignados");
        itemEventsAssignedLogistic.addActionListener(e -> openViewEventsAssignment());
        menuLogistic.add(itemEventsAssignedLogistic);

        JMenuItem itemViewCooridnatorProfile = new JMenuItem("Ver perfil");
        itemViewCooridnatorProfile.addActionListener(e -> openLogisticProfile());
        menuLogistic.add(itemViewCooridnatorProfile);

        menuBar.add(menuLogistic);

        // Menú Coordinador
        JMenu menuCoordinator = new JMenu("Coordinador");

        JMenuItem itemManageLogistics = new JMenuItem("Administrar logisticos");
        itemManageLogistics.addActionListener(e -> openManageLogistics());
        menuCoordinator.add(itemManageLogistics);

        JMenuItem itemEventsAssignedCoordinator = new JMenuItem("Ver eventos asignados");
        itemEventsAssignedCoordinator.addActionListener(e -> openViewEventsAssignment());
        menuCoordinator.add(itemEventsAssignedCoordinator);

        JMenuItem itemViewCoordinatorProfile = new JMenuItem("Ver perfil");
        itemViewCoordinatorProfile.addActionListener(e -> openCooridinatorProfile());
        menuCoordinator.add(itemViewCoordinatorProfile);

        menuBar.add(menuCoordinator);

        // Menú Manager
        JMenu menuManager = new JMenu("Manager");

        JMenuItem itemManageEvents = new JMenuItem("Administrar eventos");
        itemManageEvents.addActionListener(e -> openManageEvents());
        menuManager.add(itemManageEvents);

        menuBar.add(menuManager);

        // Menú Opciones
        JMenu menuOptions = new JMenu("Opciones");
        JMenuItem itemExit = new JMenuItem("Salir");
        itemExit.addActionListener(e -> returnBack());
        menuOptions.add(itemExit);

        menuBar.add(menuOptions);

        return menuBar;
    }

    // Coordinators
    private void openViewEventsAssignment() {
        if (!(currentUser instanceof Coordinator) && !(currentUser instanceof Logistic)) {
            JOptionPane.showMessageDialog(this, "Esta opción es solo para Coordinadores y Logisticos");
            return;
        }
        ViewEventsAssignmentCoordinator frame = new ViewEventsAssignmentCoordinator(assignmentService, eventService,
                currentUser);
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void openManageLogistics() {
        if (!(currentUser instanceof Coordinator)) {
            JOptionPane.showMessageDialog(this, "Esta opción es solo para Coordinadores");
            return;
        }
        ManageLogisticFrame frame = new ManageLogisticFrame(logisticService, desktopPane);
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void openCooridinatorProfile() {
        if (!(currentUser instanceof Coordinator)) {
            JOptionPane.showMessageDialog(this, "Esta opción es solo para Coordinadores");
            return;
        }

        Coordinator coordinator = (Coordinator) currentUser;
        ViewCoordinatorProfile frame = new ViewCoordinatorProfile(coordinator, coordinatorService, salaryService);
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    // Logistics
    private void openLogisticProfile() {
        if (!(currentUser instanceof Logistic)) {
            JOptionPane.showMessageDialog(this, "Esta opción es solo para Logisticos");
            return;
        }

        Logistic logistic = (Logistic) currentUser;
        ViewLogisticProfile frame = new ViewLogisticProfile(logistic, salaryService);
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    // Managers
    private void openManageEvents() {
        if (!(currentUser instanceof Manager)) {
            JOptionPane.showMessageDialog(this, "Esta opción es solo para Gerentes");
            return;
        }
        ManagerEvent frame = new ManagerEvent(eventService);
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    public void returnBack() {
        Back = new LoginFrame(staffService, logisticService, eventService, assignmentService,
                coordinatorService, salaryService);
        Back.setVisible(true);
        this.dispose();
    }
}
