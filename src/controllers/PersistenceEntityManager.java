package controllers;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceEntityManager {
    private static PersistenceEntityManager instance = null;
    private final EntityManagerFactory emf;

    private PersistenceEntityManager() {
        emf = Persistence.createEntityManagerFactory("default");
    }

    public static synchronized PersistenceEntityManager getInstance() {
        if (instance == null) {
            instance = new PersistenceEntityManager();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
