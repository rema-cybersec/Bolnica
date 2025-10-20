package controllers;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.SwingUtilities;

import gui.MainWindow;

public class App {

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
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Initialize database (optional for now)
            // Database.initConnection();

            // Load and show main window
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}