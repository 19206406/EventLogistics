/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/MDIApplication.java to edit this template
 */
package com.desgroup.eventlogistics;

import com.desgroup.ui.LoginFrame;
import com.desgroup.ui.UiActions;
import com.desgroup.ui.menus.CoordinatorMenu;
import com.desgroup.ui.menus.LogisticMenu;
import com.desgroup.ui.menus.ManagerMenu;
import com.desgroup.ui.menus.RoleMenu;
import com.desgroup.utils.DatabaseInitializer;
import com.desgroup.utils.HibernateUtil;
import com.desgroup.utils.StaffRole;
import java.awt.EventQueue;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.hibernate.SessionFactory;

/**
 *
 * @author urreg
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        UiActions.installGlobalHandler();
        applyLookAndFeel();

        try {
            start();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "La aplicación no pudo iniciar", ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "No se pudo iniciar EventLogistics",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void applyLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.WARNING, "No se pudo aplicar Nimbus; se usa la apariencia por defecto", ex);
        }
    }

    private static void start() {
        DatabaseInitializer.initialize();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Runtime.getRuntime().addShutdownHook(new Thread(HibernateUtil::shutdown, "hibernate-shutdown"));

        AppContext ctx = new AppContext(sessionFactory);

        Map<StaffRole, RoleMenu> roleMenus = Map.of(
                StaffRole.LOGISTIC, new LogisticMenu(ctx.assignmentQueryService, ctx.eventService, ctx.logisticService),
                StaffRole.COORDINATOR,
                new CoordinatorMenu(ctx.logisticService, ctx.coordinatorService, ctx.eventService,
                        ctx.assignmentCommandService, ctx.assignmentQueryService, ctx.availabilityService),
                StaffRole.MANAGER,
                new ManagerMenu(ctx.eventService, ctx.coordinatorService, ctx.assignmentCommandService,
                        ctx.assignmentQueryService, ctx.availabilityService, ctx.managerService));

        EventQueue.invokeLater(() -> new LoginFrame(ctx.staffService, roleMenus).setVisible(true));
    }

    /*
     * WITH s AS (
     * INSERT INTO staff (name, email, phone, staff_position, password, staff_type)
     * VALUES ('Admin', 'admin@gmail.com', '3000000000', 'Gerente', 'admin123',
     * 'MANAGER')
     * RETURNING id
     * )
     * INSERT INTO manager (id, company) SELECT id, 'DesGroup' FROM s;
     */

    // Sebastian Urrego Graciano
    // Juan David Ortiz Diaz
    // Olger Rafael Mercado Alvarez
    // Juan Esteban Ballesteros Sanchez

}
