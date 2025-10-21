package controllers;

import entities.*;
import views.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Queries {
    private final PersistenceEntityManager pem = PersistenceEntityManager.getInstance();

    // sati provedeno ovde: 4
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

    public <EntityType> void insert(EntityType entity) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Nije uspelo dodati u bazu" + ex.getMessage(), ex);
        } finally {
            em.close();
        }
    }



    public List<Mr10223Lekar> getAllLekari() {
        EntityManager em = pem.getEntityManager();
        return em.createQuery("SELECT l FROM Mr10223Lekar l", Mr10223Lekar.class).getResultList();
    }

    public List<Mr10223Pacijent> getAllPacijenti() {
        EntityManager em = pem.getEntityManager();
        return em.createQuery("SELECT p FROM Mr10223Pacijent p", Mr10223Pacijent.class).getResultList();
    }

    public List<Mr10223Dijagnoza> getAllDijagnoze() {
        // ovo se moze apstrakovati -> TODO: apstrakuj ove metode da preko java generics
        EntityManager em = pem.getEntityManager();
        return em.createQuery("SELECT d FROM Mr10223Dijagnoza d", Mr10223Dijagnoza.class).getResultList();
    }

    public List<Mr10223Pregled> getAllPregledi() {
        EntityManager em = pem.getEntityManager();
        return em.createQuery("SELECT p FROM Mr10223Pregled p", Mr10223Pregled.class).getResultList();
    }

    public void deletePacijent(Mr10223Pacijent pacijent) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Mr10223PacijentKey k = pacijent.getId();
            // rucno ih brisem zato sto nemam trenutno pristup bazi da promenim polja na cascade constraints... -> line 190
            em.createQuery(
                            "DELETE FROM Mr10223Pregled p WHERE " +
                                    "p.id.pacijentId.ime = :ime AND " +
                                    "p.id.pacijentId.prz = :prz AND " +
                                    "p.id.pacijentId.datrodj = :datrodj")
                    .setParameter("ime", k.getIme())
                    .setParameter("prz", k.getPrz())
                    .setParameter("datrodj", k.getDatrodj())
                    .executeUpdate();

            Mr10223Pacijent managed = em.merge(pacijent);
            em.remove(managed);
            // mogao sam mozda apstrakovati ove silne delete metode -> TODO: apstrakuj ove metode u jednu koja prima objekat, klasu entiteta i pravi odgovarajuc query

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database delete failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void deleteLekar(Mr10223Lekar lekar) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery(
                            "DELETE FROM Mr10223Pregled p WHERE " +
                                    "p.id.lekarId.ime = :ime AND " +
                                    "p.id.lekarId.prz = :prz AND " +
                                    "p.id.lekarId.spec = :spec")
                    .setParameter("ime", lekar.getId().getIme())
                    .setParameter("prz", lekar.getId().getPrz())
                    .setParameter("spec", lekar.getId().getSpec())
                    .executeUpdate();

            Mr10223Lekar managed = em.merge(lekar);
            em.remove(managed);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database delete failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void deleteDijagnoza(Mr10223Dijagnoza dijagnoza) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Mr10223DijagnozaKey dijagnozaKey = dijagnoza.getId();
            em.createQuery("DELETE FROM Mr10223Pregled p WHERE p.dijagnozaId = :dijagnozaKey")
                    .setParameter("dijagnozaKey", dijagnozaKey)
                    .executeUpdate();

            Mr10223Dijagnoza managed = em.merge(dijagnoza);
            em.remove(managed);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database delete failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


    public void deletePregled(Mr10223Pregled pregled) {
        EntityManager em = pem.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Mr10223Pregled managed = em.merge(pregled);
            em.remove(managed);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database delete failed: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


    public List<Mr10223Pregled> getPreglediByLekar(Mr10223Lekar lekar) {
        EntityManager em = pem.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Mr10223Pregled p WHERE p.id.lekarId = :lekarId",
                            Mr10223Pregled.class
                    ).setParameter("lekarId", lekar.getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }


    // OVO JE ZA DEBUG:
    // ne znam da li mi baza ima cascade constraints, pa moram proveriti preko jave
    // ovaj kod nije originalan, ali je tu samo za debug.
    public void printDeleteRules(String tableName) {
        // Posto nisam podesio bazu sa cascade constraints i vpn mi ne radi zbog nekog razloga
        // kanda cu morati rucno da brisem sve... ovo ce biti zabavno...
        EntityManager em = pem.getEntityManager();
        try {
            String sql = "SELECT constraint_name, delete_rule " +
                    "FROM user_constraints " +
                    "WHERE table_name = '" + tableName.toUpperCase() + "' " +
                    "AND constraint_type = 'R'";

            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            System.out.println("Foreign keys for table: " + tableName.toUpperCase());
            for (Object[] row : results) {
                System.out.printf("  %-40s  ->  %s%n", row[0], row[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to query constraints for " + tableName);
        } finally {
            em.close();
        }
    }

}