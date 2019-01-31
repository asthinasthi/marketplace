package org.anirudh.marketplace.controller;

import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.entity.Seller;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.service.ProjectService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Test
    public void testCreateProject() throws ResourceNotFoundException, JSONException{
        ProjectController projectController = new ProjectController();
        projectController.projectService = mock(ProjectService.class);
        Project project = new Project();
        project.setStatus("CREATED");
        Seller seller = new Seller();
        seller.setId(2);
        project.setSeller(seller);
        when(projectController.projectService.createProject(any())).thenReturn(project);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "CREATED");
        jsonObject.put("sellerId", 2);
        ResponseEntity responseEntity = projectController.createProject(jsonObject.toString());
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        ProjectResponse projectResponse = (ProjectResponse) responseEntity.getBody();
        Assert.assertEquals((int)projectResponse.getProjectPOJO().getSellerId(),2);
    }

    @Test
    public void testGetProjects() throws ResourceNotFoundException, JSONException{
        ProjectController projectController = new ProjectController();
        projectController.projectService = mock(ProjectService.class);
        List<Project> projectList = new ArrayList<>();
        Project project = new Project();
        Seller seller = new Seller();
        seller.setId(3);
        project.setSeller(seller);
        when(projectController.projectService.getProjects(any(), any())).thenReturn(projectList);

        ResponseEntity responseEntity = projectController.getProjects(0, "", "", 0l);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
