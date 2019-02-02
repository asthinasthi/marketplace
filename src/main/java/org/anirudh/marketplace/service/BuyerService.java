package org.anirudh.marketplace.service;

import org.anirudh.marketplace.dao.BuyerDao;
import org.anirudh.marketplace.entity.Buyer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BuyerService {
    private EntityManagerFactory entityManagerFactory;

    public BuyerService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    public Buyer getBuyerByUsername(String userName) {
        BuyerDao buyerDao = new BuyerDao(entityManagerFactory);
        return buyerDao.getBuyerByUsername(userName);
    }

}
