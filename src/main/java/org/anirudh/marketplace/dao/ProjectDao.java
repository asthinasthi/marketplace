package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.entity.Seller;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectDao {
    private EntityManagerFactory emf;

    public ProjectDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /*
    * Pagination supported through nextId
    * If nextId = 0 --> First Page of 100 results
    * Subsequent requests can use the nextId in the response to query for next pages
    * */
    public List<Project> getProjects(Project project, Integer nextId) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        List<Project> projects = null;
        try {
            Root<Project> root = cq.from(Project.class);
            cq.select(root);
            List<Predicate> predicates = new ArrayList<>();
            if(project.getSeller() != null){
                Predicate sellerPredicate = cb.equal(root.get("seller").as(Seller.class), project.getSeller());
                predicates.add(sellerPredicate);
            }
            Predicate gtNextId = cb.greaterThan(root.get("id").as(Integer.class), nextId);
            predicates.add(gtNextId);
            Predicate gtDeadline = cb.greaterThanOrEqualTo(root.get("deadline").as(Date.class), project.getDeadline());
            predicates.add(gtDeadline);
            if (StringUtils.isNotEmpty(project.getName())) {
                Predicate namePredicate = cb.like(root.get("name").as(String.class), "%" + project.getName() + "%");
                predicates.add(namePredicate);
            }
            if (StringUtils.isNotEmpty(project.getDescription())) {
                Predicate descriptionPredicate = cb.like(root.get("description").as(String.class), "%" + project.getDescription() + "%");
                predicates.add(descriptionPredicate);
            }
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            cq.orderBy(cb.asc(root.get("id").as(Integer.class)));
            em.getTransaction().begin();
            projects = em.createQuery(cq).setMaxResults(100).getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            em.close();
        }
        return projects;
    }

    public List<Project> getProjectsByDeadline(Date from, Date to) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        List<Project> projects = null;
        try {
            Root<Project> root = cq.from(Project.class);
            cq.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate deadlinePredicate = cb.between(root.get("deadline").as(Date.class), from, to);
            predicates.add(deadlinePredicate);
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            em.getTransaction().begin();
            projects = em.createQuery(cq).getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            em.close();
        }
        return projects;
    }

    public List<Project> getProjectsByBuyer(Integer buyerId, Integer nextId){
        EntityManager em = emf.createEntityManager();
        List<Project> projects = null;
        try {
            Query q = em.createNativeQuery("select * from project p, buyer b, bid bd where p.id = bd.project_id " +
                    "AND bd.buyer_id = b.id AND b.id =?1 AND p.id > ?2", Project.class);
            q.setParameter(1, buyerId);
            q.setParameter(2, nextId);
            em.getTransaction().begin();
            projects = q.getResultList();
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            em.close();
        }
        return projects;
    }
}
