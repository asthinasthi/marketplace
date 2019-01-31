package org.anirudh.marketplace.controller;

public class ProjectResponse  {
    String requestId;
    String message;
    ProjectPOJO projectPOJO;
    public ProjectResponse(ProjectPOJO projectPOJO, String message, String requestId){
        this.requestId = requestId;
        this.message = message;
        this.projectPOJO = projectPOJO;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectPOJO getProjectPOJO() {
        return projectPOJO;
    }

    public void setProjectPOJO(ProjectPOJO projectPOJO) {
        this.projectPOJO = projectPOJO;
    }
}
