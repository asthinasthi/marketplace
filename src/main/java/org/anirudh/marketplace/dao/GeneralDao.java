package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GeneralDao {

    private EntityManagerFactory emf;

    public GeneralDao(EntityManagerFactory emf){
        this.emf = emf;
    }

    public Object upsert(Object o){
        Object mergedObj = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            mergedObj = em.merge(o);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return mergedObj;
    }

    public Object findById(Class t, Integer id){
        Object obj = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            obj = em.find(t, id);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        } finally {
            em.close();
        }
        return obj;
    }
}
