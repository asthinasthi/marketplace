package org.anirudh.marketplace.service;

import org.anirudh.marketplace.dao.GeneralDao;
import org.anirudh.marketplace.dao.ProjectDao;
import org.anirudh.marketplace.entity.Buyer;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.entity.Seller;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Date;

public class ProjectService {

    private EntityManagerFactory entityManagerFactory;

    public ProjectService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    public Project upsertProject(Project project) throws ResourceNotFoundException {
        GeneralDao dao = new GeneralDao(entityManagerFactory);
        if(project.getId()!=null){
            Project existingProject = (Project) dao.findById(Project.class, project.getId());
            if(existingProject == null) throw new ResourceNotFoundException("Project with this ID not found");
        }
        Project mergedEntity = (Project) dao.upsert(project);
        return mergedEntity;
    }

    /*
    * Get 100 most recent projects
    * Option to filter by status, bidder
    * */
    public List<Project> getProjects(Project project, Integer nextId) {
        ProjectDao projectDao = new ProjectDao(entityManagerFactory);
        return projectDao.getProjects(project, nextId);
    }

    public List<Project> getProjectsByDeadline(Date from, Date to) {
        ProjectDao projectDao = new ProjectDao(entityManagerFactory);
        return projectDao.getProjectsByDeadline(from, to);
    }

    public List<Project> getProjectsByBuyer(Buyer buyer, Integer nextId){
        ProjectDao projectDao = new ProjectDao(entityManagerFactory);
        return projectDao.getProjectsByBuyer(buyer.getId(), nextId);
    }


}
