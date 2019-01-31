package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.entity.Buyer;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class BidDao {
    private EntityManagerFactory emf;

    public BidDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /*
    * Search for the lowest bid for a specific project
    * If two or more bids are found with lowest value. A bid is randomly selected
    * */
    public List<Bid> getMinRateBid(Integer projectId) {
        Date now = new Date();
        System.out.println("Lowest Bid search start for " + projectId + " at " + now.toString());
        EntityManager em = emf.createEntityManager();
        List<Bid> bids = null;
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("select * from bid where quote in (select min(quote) from bid where project_id=?1 and quote>0)", Bid.class);
            q.setParameter(1, projectId);
            bids = q.getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        Date after = new Date();
        Long timeDiff = new Date().getTime() - now.getTime();
        System.out.println("Lowest Bid search complete for " + projectId + " Took " + timeDiff + " ms");
        return bids;
    }

    public Bid createBid(Bid bid) throws ResourceNotFoundException {
        addQuote(bid);
        GeneralDao dao = new GeneralDao(emf);
        if (bid.getBuyer() == null) {
            Buyer buyer = (Buyer) dao.findById(Buyer.class, bid.getBuyerId());
            if (buyer == null) throw new ResourceNotFoundException("Invalid Buyer");
            bid.setBuyer(buyer);
        }
        if (bid.getProject() == null) {
            Project project = (Project) dao.findById(Project.class, bid.getProjectId());
            if (project == null) throw new ResourceNotFoundException("Invalid Project");
            bid.setProject(project);
        }
        return (Bid) dao.upsert(bid);
    }

    public void addQuote(Bid bid) {
        if (bid.getFixedPrice() != null) {
            bid.setQuote(bid.getFixedPrice());
        } else if (bid.getHours() != null && bid.getHourlyRate() != null) {
            bid.setQuote(bid.getHourlyRate() * bid.getHours());
        } else {
            bid.setQuote(0f);
        }
    }

}
