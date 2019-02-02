package org.anirudh.marketplace.controller;

import com.google.gson.Gson;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.entity.Seller;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.response.ProjectListResponse;
import org.anirudh.marketplace.response.ProjectPOJO;
import org.anirudh.marketplace.response.ProjectResponse;
import org.anirudh.marketplace.service.ProjectService;
import org.anirudh.marketplace.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.anirudh.marketplace.entity.User;

import java.security.Principal;
import java.util.*;

@RestController
public class ProjectController {

    ProjectService projectService = new ProjectService();

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('SELLER_USER')")
    public ResponseEntity createProject(@RequestBody String body, OAuth2Authentication oauth){
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " POST /project");
        String userName = oauth.getPrincipal().toString();
        SellerService sellerService = new SellerService();
        Seller seller = sellerService.getSellerByUserName(userName);
        ResponseEntity responseEntity = null;
        try {
            Gson gson = new Gson();
            Project project = gson.fromJson(body, Project.class);
            project.setCreatedBy("project-controller");
            project.setCreateDate(new Date());
            project.setUpdatedBy("project-controller");
            project.setUpdateDate(new Date());
            project.setStatus("CREATED");
            project.setSeller(seller);
            project =  projectService.createProject(project);
            ProjectResponse projectResponse = new ProjectResponse(new ProjectPOJO(project), "Success", requestId.toString());
            responseEntity = new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.OK);
        }  catch (Exception e){
            e.printStackTrace();
            ProjectResponse projectResponse = new ProjectResponse(null, "Internal Error", requestId.toString());
            responseEntity = new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity getProjects(@RequestParam(name = "nextId", defaultValue = "0") Integer nextId, @RequestParam(name = "name", defaultValue = "") String name,
                            @RequestParam(name = "description", defaultValue = "") String description, @RequestParam(name = "deadline", defaultValue = "0") Long deadline){
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " GET /project");
        try {
            Project project = new Project();
            if(StringUtils.isNotEmpty(name))
                project.setName(name);
            if(StringUtils.isNotEmpty(description))
                project.setDescription(description);
            if(deadline!=null){
                project.setDeadline(new Date(deadline));
            }else {
                Date lastMonth = DateUtils.addDays(new Date(), -30);
                project.setDeadline(lastMonth);
            }
            ProjectService projectService = new ProjectService();
            ProjectListResponse projectResource = new ProjectListResponse(projectService.getProjects(project, nextId), "Success", requestId.toString());
            return new ResponseEntity<ProjectListResponse>(projectResource, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            ProjectListResponse projectListResponse = new ProjectListResponse(new ArrayList<>(), "Internal Error", requestId.toString());
            return new ResponseEntity<ProjectListResponse>(projectListResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/projectAuth", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public String auth(){
        return "Authorized";
    }
}
