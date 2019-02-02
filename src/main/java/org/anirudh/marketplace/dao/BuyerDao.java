package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Buyer;
import org.anirudh.marketplace.entity.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class BuyerDao {
    private EntityManagerFactory emf;

    public BuyerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Buyer getBuyerByUsername(String userName){
        EntityManager em = emf.createEntityManager();
        Buyer buyer = null;
        try{
            Query q = em.createNativeQuery("select * from buyer where app_user_id in (select id from app_user where username =?1)", Buyer.class);
            q.setParameter(1, userName);
            em.getTransaction().begin();
            List<Buyer> buyerList = q.getResultList();
            em.getTransaction().commit();
            em.close();
            if(buyerList!=null && buyerList.size()>0){
                buyer = buyerList.get(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return buyer;
    }
}
