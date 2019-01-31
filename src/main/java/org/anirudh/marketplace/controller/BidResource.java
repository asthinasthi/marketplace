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

        public BidResource(Bid bid, String message, String requestId){
            if(bid!=null)
                this.id = bid.getId();
            this.message = message;
            this.requestId = requestId;
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
    }
