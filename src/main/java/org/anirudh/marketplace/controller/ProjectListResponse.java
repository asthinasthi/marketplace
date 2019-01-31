package org.anirudh.marketplace.controller;

import org.anirudh.marketplace.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectListResponse {
    private String requestId;
    private String message;
    private Integer total=0;
    private Integer nextId=0;
    private List<ProjectPOJO> data;

    public ProjectListResponse(List<Project> projects, String message, String requestId){
        this.total = projects.size();
        if(this.total>0)
            this.nextId = projects.get(this.total-1).getId();
        this.data = new ArrayList<>();
        for (Project p: projects) {
            ProjectPOJO projectPOJO = new ProjectPOJO(p);
            this.data.add(projectPOJO);
        }
        this.message = message;
        this.requestId = requestId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public List<ProjectPOJO> getData() {
        return data;
    }

    public void setData(List<ProjectPOJO> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
 }
