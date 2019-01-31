package org.anirudh.marketplace.dao;

import org.anirudh.marketplace.entity.Project;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProjectDaoTest {

    private static EntityManagerFactory entityManagerFactory ;

    @BeforeClass
    public static void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    @Test
    public void getProjectsTest(){
        ProjectDao projectDao = new ProjectDao(entityManagerFactory);
    }

}
