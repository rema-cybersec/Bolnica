package controllers;

import controllers.PersistenceEntityManager;
import views.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Queries {
    private final PersistenceEntityManager pem = PersistenceEntityManager.getInstance();

    public <EntityType, ViewType extends View<EntityType>> List<Object> getData(
            Class<EntityType> entityClass,
            Supplier<ViewType> viewSupplier
    ) {
        EntityManager em = pem.getEntityManager();

        String className = entityClass.getSimpleName();
        TypedQuery<EntityType> query = em.createQuery("SELECT e FROM " + className + " e", entityClass);

        List<EntityType> resultList = query.getResultList();
        List<Object> out = new ArrayList<>();

        for (EntityType entity : resultList) {
            ViewType view = viewSupplier.get();
            view.load(entity);
            out.add(view);
        }

        em.close();
        return out;
    }

    // --- New insert method ---
    public <EntityType> void insert(EntityType entity) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database insert failed: " + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }
}