package org.anirudh.marketplace.dao;

import com.sun.tools.javah.Gen;
import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.entity.Buyer;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.entity.Seller;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.function.Predicate;

public class GenerateData {

    private static EntityManagerFactory entityManagerFactory ;
    private static List<String> sellerNames;
    private static List<String> buyerNames;
    private static List<Date> dates;

    @BeforeClass
    public static void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
        sellerNames = new ArrayList<String>();
        sellerNames.add("John Smith");
        sellerNames.add("Jane Doe");
        sellerNames.add("Bill Gates");
        sellerNames.add("Jim Gordon");
        sellerNames.add("Janice Multisanti");

        buyerNames = new ArrayList<String>();
        buyerNames.add("Kimmy Smith");
        buyerNames.add("Jean Dove");
        buyerNames.add("Will Nates");
        buyerNames.add("Kim Gordon");
        buyerNames.add("Juanice LaCerva");

        dates = new ArrayList<>();
        dates.add(new Date(1548832510747l));
        dates.add(new Date(1546154100000l));
        dates.add(new Date(1544944500000l));
        dates.add(new Date(1516086900000l));
    }


    @Test
    public void test(){
        //Create Sellers
        GeneralDao dao = new GeneralDao(entityManagerFactory);
//        createSellers(dao);
//        createBuyers(dao);
        createProjects(dao);
    }

    private void createSellers(GeneralDao dao){
        for (int i = 0; i < 4; i++) {
            Seller seller = new Seller();
            seller.setName(sellerNames.get(i));
            seller.setUpdatedBy("data_generator_app");
            seller.setCreatedBy("data_generator_app");
            seller.setCreateDate(new Date());
            seller.setUpdateDate(new Date());
            dao.upsert(seller);
        }
    }

    private void createBuyers(GeneralDao dao){
        for (int i = 0; i < 4; i++) {
            Buyer buyer = new Buyer();
            buyer.setName(buyerNames.get(i));
            buyer.setUpdatedBy("data_generator_app");
            buyer.setCreatedBy("data_generator_app");
            buyer.setCreateDate(new Date());
            buyer.setUpdateDate(new Date());
            dao.upsert(buyer);
        }
    }

    private void createProjects(GeneralDao dao){
        for (int i = 0; i < 200 ; i++) {
            Project project = new Project();
            project.setName("Name-" + i);
            project.setDescription("Description-" + i);
            project.setDeadline(DateUtils.addMinutes(new Date(), 5));
            project.setSeller((Seller) dao.findById(Seller.class, i%10+1));
            project.setUpdatedBy("data_generator_app");
            project.setCreatedBy("data_generator_app");
            project.setCreateDate(new Date());
            project.setUpdateDate(new Date());
            Project updatedProject = (Project) dao.upsert(project);
            createBids(updatedProject, dao);
        }
    }

    private void createBids(Project project, GeneralDao dao){

        Buyer buyer = (Buyer)dao.findById(Buyer.class, 4);
        Bid bid = new Bid();
        bid.setProject(project);
        bid.setBuyer(buyer);
        bid.setHourlyRate(4f);
        dao.upsert(bid);

        buyer = (Buyer)dao.findById(Buyer.class, 3);
        bid = new Bid();
        bid.setProject(project);
        bid.setBuyer(buyer);
        bid.setHourlyRate(3f);
        dao.upsert(bid);

    }
}
