package org.anirudh.marketplace.controller;


import com.google.gson.Gson;
import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.entity.Buyer;
import org.anirudh.marketplace.entity.Seller;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.response.BidResponse;
import org.anirudh.marketplace.service.BiddingService;
import org.anirudh.marketplace.service.BuyerService;
import org.anirudh.marketplace.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BidController {

    BiddingService biddingService = new BiddingService();

    @RequestMapping(value = "/bid", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('BUYER_USER')")
    public ResponseEntity createBid(@RequestBody String body, OAuth2Authentication oauth) {
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " POST /bid");
        try {
            BidResponse bidResponse = null;
            if (StringUtils.isEmpty(body)) {
                bidResponse = new BidResponse(null, "Request Body Missing", requestId.toString());
                return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
            }
            Gson gson = new Gson();
            Bid bid = gson.fromJson(body, Bid.class);
            String userName = oauth.getPrincipal().toString();
            BuyerService buyerService = new BuyerService();
            Buyer buyer = buyerService.getBuyerByUsername(userName);
            bid.setBuyer(buyer);

            if (bid.getProjectId() == null) {
                bidResponse = new BidResponse(null, "Missing Project Id", requestId.toString());
                return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
            }
            try {//Prioritize Hourly Rate
                if (bid.getHourlyRate() != null) {
                    if (bid.getHours() != null) {
                        Bid updatedBid = biddingService.createBid(bid);
                        bidResponse = new BidResponse(updatedBid, "Created Bid Successfully!", requestId.toString());
                        return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.OK);
                    } else {
                        bidResponse = new BidResponse(null, "Missing hours", requestId.toString());
                        return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
                    }
                } else if (bid.getFixedPrice() != null) {
                    Bid updatedBid = biddingService.createBid(bid);
                    bidResponse = new BidResponse(updatedBid, "Created Bid Successfully!", requestId.toString());
                    return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.OK);
                } else {
                    bidResponse = new BidResponse(null, "Use either hourly rate + hours or fixedPrice", requestId.toString());
                    return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
                }
            } catch (ResourceNotFoundException e) {
                bidResponse = new BidResponse(null, e.getMessage(), requestId.toString());
                return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            e.printStackTrace();
            BidResponse bidResponse = new BidResponse(null, "Internal Error", requestId.toString());
            return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/bid", method = RequestMethod.GET)
    public ResponseEntity getBid(@RequestParam(value = "id") Integer id){
        UUID requestId = UUID.randomUUID();
        System.out.println(requestId.toString() + " GET /bid");
        try {
            Bid bid = biddingService.getBid(id);
            BidResponse bidResponse = new BidResponse(bid, "Success", requestId.toString());
            return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException rnfe){
            BidResponse bidResponse = new BidResponse(null, rnfe.getMessage(), requestId.toString());
            return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            BidResponse bidResponse = new BidResponse(null, "Internal Error", requestId.toString());
            return new ResponseEntity<BidResponse>(bidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
