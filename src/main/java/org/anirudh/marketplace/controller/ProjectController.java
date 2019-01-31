package org.anirudh.marketplace.controller;

import com.google.gson.Gson;
import org.anirudh.marketplace.entity.Project;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.dc.pr.PRError;

import java.util.*;

@RestController
public class ProjectController {

    ProjectService projectService = new ProjectService();

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody String body){
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " POST /project");
        try {
            Gson gson = new Gson();
            Project project = gson.fromJson(body, Project.class);
            project.setCreatedBy("project-controller");
            project.setCreateDate(new Date());
            project.setUpdatedBy("project-controller");
            project.setUpdateDate(new Date());
            project.setStatus("CREATED");
            project =  projectService.createProject(project);
            ProjectResponse projectResponse = new ProjectResponse(new ProjectPOJO(project), "Success", requestId.toString());
            ResponseEntity responseEntity = new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.OK);
            return responseEntity;
        } catch (ResourceNotFoundException rnfe){
            rnfe.printStackTrace();
            ProjectResponse projectResponse = new ProjectResponse(null, rnfe.getMessage(), requestId.toString());
            return new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            ProjectResponse projectResponse = new ProjectResponse(null, "Internal Error", requestId.toString());
            return new ResponseEntity<ProjectResponse>(projectResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}
