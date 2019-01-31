package org.anirudh.marketplace.controller;


import com.google.gson.Gson;
import com.sun.tools.javah.Gen;
import org.anirudh.marketplace.dao.BidDao;
import org.anirudh.marketplace.dao.GeneralDao;
import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.service.BiddingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BidController {

    BiddingService biddingService = new BiddingService();

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    public ResponseEntity createBid(@RequestBody String body) {
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " POST /bid");
        try {
            BidResource bidResource = null;
            if (StringUtils.isEmpty(body)) {
                bidResource = new BidResource(null, "Request Body Missing", requestId.toString());
                return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
            }
            Gson gson = new Gson();
            Bid bid = gson.fromJson(body, Bid.class);

            if (bid.getBuyerId() == null) {
                bidResource = new BidResource(null, "Missing Buyer Id", requestId.toString());
                return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
            }

            if (bid.getProjectId() == null) {
                bidResource = new BidResource(null, "Missing Project Id", requestId.toString());
                return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
            }
            try {//Prioritize Hourly Rate
                if (bid.getHourlyRate() != null) {
                    if (bid.getHours() != null) {
                        Bid updatedBid = biddingService.createBid(bid);
                        bidResource = new BidResource(updatedBid, "Created Bid Successfully!", requestId.toString());
                        return new ResponseEntity<BidResource>(bidResource, HttpStatus.OK);
                    } else {
                        bidResource = new BidResource(null, "Missing hours", requestId.toString());
                        return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
                    }
                } else if (bid.getFixedPrice() != null) {
                    Bid updatedBid = biddingService.createBid(bid);
                    bidResource = new BidResource(updatedBid, "Created Bid Successfully!", requestId.toString());
                    return new ResponseEntity<BidResource>(bidResource, HttpStatus.OK);
                } else {
                    bidResource = new BidResource(null, "Use either hourly rate + hours or fixedPrice", requestId.toString());
                    return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
                }
            } catch (ResourceNotFoundException e) {
                bidResource = new BidResource(null, e.getMessage(), requestId.toString());
                return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
            BidResource bidResource = new BidResource(null, "Internal Error", requestId.toString());
            return new ResponseEntity<BidResource>(bidResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/bid", method = RequestMethod.GET)
    public ResponseEntity getBid(@RequestParam(value = "id") Integer id){
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " GET /bid");
        try {
            Bid bid = biddingService.getBid(id);
            BidResource bidResource = new BidResource(bid, "Success", requestId.toString());
            return new ResponseEntity<BidResource>(bidResource, HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            BidResource bidResource = new BidResource(null, rnfe.getMessage(), requestId.toString());
            return new ResponseEntity<BidResource>(bidResource, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            BidResource bidResource = new BidResource(null, "Internal Error", requestId.toString());
            return new ResponseEntity<BidResource>(bidResource, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
