package org.anirudh.marketplace.controller;

import org.anirudh.marketplace.entity.Bid;
import org.anirudh.marketplace.exceptions.ResourceNotFoundException;
import org.anirudh.marketplace.response.BidResponse;
import org.anirudh.marketplace.service.BiddingService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class BidControllerTest {

    @Test
    public void testCreateBid() throws JSONException, ResourceNotFoundException{
        BidController bidController = new BidController();
        bidController.biddingService = mock(BiddingService.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Bid-1");
        jsonObject.put("projectId", "1");
        jsonObject.put("fixedPrice", 350f);
        jsonObject.put("buyerId", 2);
        Bid bid = new Bid();
        bid.setId(123);
        bid.setName("Bid-1");
        bid.setProjectId(1);
        bid.setFixedPrice(350f);
        bid.setBuyerId(2);
        when(bidController.biddingService.createBid(any())).thenReturn(bid);
        ResponseEntity responseEntity = bidController.createBid(jsonObject.toString(), null);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        BidResponse responseBid = (BidResponse)responseEntity.getBody();
        Assert.assertEquals((Integer)responseBid.getId(), (Integer)123);
    }

    @Test
    public void testCreateBidNoBuyer() throws JSONException{
        BidController bidController = new BidController();
        bidController.biddingService = mock(BiddingService.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Bid-1");
        jsonObject.put("projectId", "1");
        jsonObject.put("fixedPrice", 350f);

        ResponseEntity responseEntity = bidController.createBid(jsonObject.toString(), null );
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateBidNoProjectId() throws JSONException{
        BidController bidController = new BidController();
        bidController.biddingService = mock(BiddingService.class);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Bid-1");
        jsonObject.put("fixedPrice", 350f);
        jsonObject.put("buyerId", 3);

        ResponseEntity responseEntity = bidController.createBid(jsonObject.toString(), null);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
