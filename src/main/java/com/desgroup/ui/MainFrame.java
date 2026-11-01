/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

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

    private final IStaffService staffService; // solo para volver al login
    private final Map<StaffRole, RoleMenu> roleMenus;

    private final Staff currentUser;

    public MainFrame(IStaffService staffService, Map<StaffRole, RoleMenu> roleMenus, Staff currentUser) {
        this.staffService = staffService;
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
        new LoginFrame(staffService, roleMenus).setVisible(true);
        this.dispose();
    }
}
