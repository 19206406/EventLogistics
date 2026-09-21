/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.desgroup.ui;

import com.desgroup.logic.EventService;
import com.desgroup.logic.LogisticService;
import com.desgroup.models.Logistic;
import com.desgroup.models.Staff;
import java.beans.PropertyVetoException;
import java.util.function.Supplier;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
    private final LogisticService logisticService;
    private final EventService eventService; 
    private Staff currentUser; 

    public MainFrame(LogisticService logisticService, EventService eventService, Staff currentUser) {
        this.logisticService = logisticService;
        this.currentUser = currentUser; 
        this.eventService = eventService; 
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

        // ---------- Menú Logistico ----------
        JMenu menuLogistic = new JMenu("Logistico");
        // AQUÍ van los ítems del rol Logistico. Patrón para cada uno:
        //   JMenuItem itemX = new JMenuItem("Texto");
        //   itemX.addActionListener(e -> openX());
        //   menuLogistic.add(itemX);
        
        JMenuItem itemLogisticOption = new JMenuItem("Ver perfil");
        itemLogisticOption.addActionListener(e -> openLogisticOption());
        menuLogistic.add(itemLogisticOption);
        
        menuBar.add(menuLogistic);

        // ---------- Menú Coordinador ----------
       
        JMenu menuCoordinator = new JMenu("Coordinador");
        
        JMenuItem itemManageLogistics = new JMenuItem("Administrar logisticos");
        itemManageLogistics.addActionListener(e -> openManageLogistics());
        menuCoordinator.add(itemManageLogistics);

        JMenuItem itemAssignedEvents = new JMenuItem("Ver eventos asignados");
        // AQUÍ: cuando exista la sesión, cambia el listener por openAssignedEvents()
        itemAssignedEvents.addActionListener(e -> showNotAvailableYet());
        menuCoordinator.add(itemAssignedEvents);

        menuBar.add(menuCoordinator);

        // ---------- Menú Manager ----------
        JMenu menuManager = new JMenu("Manager");
        
        JMenuItem itemManageEvents = new JMenuItem("Administrar eventos");
        itemManageEvents.addActionListener(e -> openManageEvents());
        menuManager.add(itemManageEvents);
        
        // AQUÍ van los ítems del rol Manager (mismo patrón que en Logistico).
        menuBar.add(menuManager);

        // ---------- Menú Opciones ----------
        JMenu menuOptions = new JMenu("Opciones");
        JMenuItem itemExit = new JMenuItem("Salir");
        itemExit.addActionListener(e -> System.exit(0));
        menuOptions.add(itemExit);
        menuBar.add(menuOptions);

        return menuBar;
    }

    // ---------- Apertura de sesiones (un método open por cada sesión) ----------

    private void openManageLogistics() {
        if(!(currentUser instanceof Manager)){
            javax.swing.JOptionPane.showMessageDialog(this, "Esta opción es solo para Coordinadores");
            return;
        }
        openSingleFrame(ManageLogisticFrame2.class,
                () -> new ManageLogisticFrame2(logisticService, desktopPane));
    }
    
    private void openLogisticOption() {
        if (!(currentUser instanceof Logistic)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Esta opción es solo para logísticos");
            return;
        }
    
        Logistic logistic = (Logistic) currentUser;
        LogisticOpcion frame = new LogisticOpcion(logistic);
        desktopPane.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    private void openManageEvents() {
        if(!(currentUser instanceof Manager)){
            javax.swing.JOptionPane.showMessageDialog(this, "Esta opción es solo para Gerentes");
            return;
        }
        openSingleFrame(ManagerEvent.class,
                () -> new ManagerEvent(eventService));
    }

    /**
     * Abre la sesión, o trae al frente la que ya está abierta para no duplicarla.
     */
    private <T extends JInternalFrame> void openSingleFrame(Class<T> type, Supplier<T> factory) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (type.isInstance(frame)) {
                try {
                    frame.setIcon(false);
                    frame.setSelected(true);
                } catch (PropertyVetoException ignored) {
                }
                frame.toFront();
                return;
            }
        }
        T newFrame = factory.get();
        desktopPane.add(newFrame);
        newFrame.setVisible(true);
    }

    private void showNotAvailableYet() {
        JOptionPane.showMessageDialog(this, "Esta sesión aún no está disponible.",
                "En construcción", JOptionPane.INFORMATION_MESSAGE);
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
