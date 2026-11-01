/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/MDIApplication.java to edit this template
 */
package com.desgroup.eventlogistics;

import com.desgroup.ui.LoginFrame;
import com.desgroup.ui.menus.CoordinatorMenu;
import com.desgroup.ui.menus.LogisticMenu;
import com.desgroup.ui.menus.ManagerMenu;
import com.desgroup.ui.menus.RoleMenu;
import com.desgroup.utils.DatabaseInitializer;
import com.desgroup.utils.HibernateUtil;
import com.desgroup.utils.StaffRole;
import java.awt.EventQueue;
import java.util.Map;
import javax.swing.UIManager;
import org.hibernate.SessionFactory;

/**
 *
 * @author urreg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws Exception {

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

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
}
