package org.anirudh.marketplace.service;

import org.anirudh.marketplace.dao.SellerDao;
import org.anirudh.marketplace.entity.Seller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerService {
    private EntityManagerFactory entityManagerFactory;

    public SellerService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    public Seller getSellerByUserName(String userName){
        SellerDao sellerDao = new SellerDao(entityManagerFactory);
        return sellerDao.getSellerByUsername(userName);
    }

}
