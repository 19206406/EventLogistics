/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/MDIApplication.java to edit this template
 */
package com.desgroup.eventlogistics;

import com.desgroup.repositories.AppContext;
import com.desgroup.ui.LoginFrame;
import com.desgroup.utils.DatabaseInitializer;
import com.desgroup.utils.HibernateUtil;
import java.awt.EventQueue;
import javax.swing.UIManager;

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

        // 1. Crea la base de datos si no existe y aplica las migraciones de Flyway.
        DatabaseInitializer.initialize();

        // 2. Fuerza la validación de las entidades contra el esquema ya creado.
        HibernateUtil.getSessionFactory();
        Runtime.getRuntime().addShutdownHook(new Thread(HibernateUtil::shutdown, "hibernate-shutdown"));

        AppContext ctx = AppContext.get();

        EventQueue.invokeLater(() -> new LoginFrame(
                ctx.staffService,
                ctx.logisticService,
                ctx.eventService,
                ctx.assignmentService,
                ctx.coordinatorService).setVisible(true));
    }
}
