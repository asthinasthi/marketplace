package org.anirudh.marketplace.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.anirudh.marketplace.entity.User;
import java.util.List;

public class UserDao {
    private EntityManagerFactory emf;

    public UserDao(EntityManagerFactory emf){
        this.emf = emf;
    }

    public User findByUsername(String userName){
        List<User> userList = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("select * from app_user where username = ?1", User.class);
            q.setParameter(1, userName);
            userList =  (List<User>)q.getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(userList!=null & userList.size()>0){
            return userList.get(0);
        } else {
            return null;
        }
    }

}
