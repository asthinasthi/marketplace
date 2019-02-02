package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class SellerDao {
    private EntityManagerFactory emf;

    public SellerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Seller getSellerByUsername(String userName){
        EntityManager em = emf.createEntityManager();
        Seller seller = null;
        try{
            Query q = em.createNativeQuery("select * from seller where app_user_id in (select id from app_user where username =?1)", Seller.class);
            q.setParameter(1, userName);
            em.getTransaction().begin();
            List<Seller> sellerList = q.getResultList();
            em.getTransaction().commit();
            em.close();
            if(sellerList!=null && sellerList.size()>0){
                seller = sellerList.get(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return seller;
    }

}
