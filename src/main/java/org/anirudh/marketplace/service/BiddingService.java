package org.anirudh.marketplace.service;

import org.anirudh.marketplace.dao.BidDao;
import org.anirudh.marketplace.dao.GeneralDao;
import org.anirudh.marketplace.dao.ProjectDao;
import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.plugin.perf.PluginRollup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BiddingService {
    private EntityManagerFactory entityManagerFactory;

    public BiddingService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    /*
    * Background job runs every min to check if any project is due
    * Calculates Lowest Bid & assigns it to the project
    * */
    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void bidTriager() {
        //get projects expired a min ago
        Date now = new Date();
        Date aMinAgo = DateUtils.addMinutes(now, -1);
        System.out.println("Checking for projects that were due at: " + aMinAgo.toString());
        ProjectService projectService = new ProjectService();
        List<Project> dueProjects = projectService.getProjectsByDeadline(aMinAgo, now);
        if (dueProjects.size() == 0) {
            System.out.println("No projects were due between: " + aMinAgo.toString() + " and " + now);
        } else {
            System.out.println("Found " + dueProjects.size() + " due at: " + aMinAgo.toString());
            Queue<Project> projectQueue = new LinkedList<>();
            projectQueue.addAll(dueProjects);
            while (!projectQueue.isEmpty()) { // If connections exceed.
                System.out.println("Queue size: " + projectQueue.size());
                Project top = projectQueue.poll();
                try {
                    Bid lowestBid = getLowestBid(top.getId()); //TODO push down compute to get bids along with projects in a single query
                    if (lowestBid != null) {
                        assignBid(top, getLowestBid(top.getId()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bid getLowestBid(Integer projectId) {
        BidDao bidDao = new BidDao(entityManagerFactory);
        List<Bid> lowestBids = bidDao.getMinRateBid(projectId);
        if (lowestBids == null || lowestBids.size() == 0) return null;
        Integer randIdx = ThreadLocalRandom.current().nextInt(0, lowestBids.size());
        return lowestBids.get(randIdx);
    }

    public void assignBid(Project project, Bid bid) {
        System.out.println("Assigning Bid: " + bid.getId() + " to " + project.getId());
        project.setBid(bid);
        project.setUpdatedBy("bidding-service");
        project.setUpdateDate(new Date());
        project.setStatus("ASSIGNED");
        GeneralDao dao = new GeneralDao(entityManagerFactory);
        dao.upsert(project);
    }

    public Bid createBid(Bid bid) throws ResourceNotFoundException {
        BidDao bidDao = new BidDao(entityManagerFactory);
        return bidDao.createBid(bid);
    }

}
