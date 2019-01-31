package org.anirudh.marketplace.controller;

import com.sun.deploy.net.HttpResponse;
import org.anirudh.marketplace.entity.Bid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/*
* Bid Response Object
* */

    public class BidResource{
        String requestId;
        Integer id;
        String message;
        String name;
        Integer projectId;

        public BidResource(Bid bid, String message, String requestId){
            if(bid!=null)
                this.id = bid.getId();
            this.message = message;
            this.requestId = requestId;
            this.name = bid.getName();
            this.projectId = bid.getProject().getId();
        }
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }
    }
