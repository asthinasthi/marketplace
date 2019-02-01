package org.anirudh.marketplace.response;

import org.anirudh.marketplace.entity.Project;

import java.util.Date;

public class ProjectPOJO {
    private Integer id;
    private String name;
    private Integer sellerId;
    private String description;
    private Date deadline;

    public ProjectPOJO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.sellerId = project.getSeller().getId();
        this.description = project.getDescription();
        this.deadline = project.getDeadline();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
