package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Bid;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BidDaoTest {
    private static EntityManagerFactory entityManagerFactory ;

    @BeforeClass
    public static void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    @Test
    public void getMinRateBidTest(){
        BidDao bidDao = new BidDao(entityManagerFactory);
        List<Bid> bids = bidDao.getMinRateBid(400);
        System.out.println(bids.size());
    }

}
