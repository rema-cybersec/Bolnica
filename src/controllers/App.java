package controllers;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.SwingUtilities;

import gui.MainWindow;

public class App {

    // App by rema-cybersec 102.23@dmi.pmf.uns.ac.rs
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Could not set LookAndFeel: " + e.getMessage());
            }

            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
                EntityManager em = emf.createEntityManager();
                Queries q = new Queries();
                q.printDeleteRules("MR10223_PREGLED");
            } catch (Exception e) {
                e.printStackTrace();
            }
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}