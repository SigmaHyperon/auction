/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.bidders.external;

import auction.Bidder;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author ???        
 */
public class ResponsiveBidder implements Bidder {

    /** The next bid. */
    private static Map<Integer, List<Integer>> stats;
    private int totalQuantity;
    private int totalRounds;
    private int totalCash;
    
    private int cash;
    private int wonQuantity;
    
    private int opponentCash;
    
    private int round;
    private int previous;
    private int opponentPrevious;
    static {
        stats = new HashMap<Integer, List<Integer>>();
    }
    @Override
    public void init(final int quantity, final int cash) {
        
        this.totalQuantity = quantity;
        this.totalRounds = (int)(quantity/2);
        this.totalCash = cash;
        
    	this.cash = cash;
        this.wonQuantity = 0;
        
        this.opponentCash = cash;
        
        this.round = 0;
        this.previous = 0;
        this.opponentPrevious = 0;
    }

    @Override
    public int placeBid() {
        if(stats.get(this.previous) == null){
            return this.totalCash / this.totalRounds;
        }
        return (int)(stats.get(this.previous).stream().collect(Collectors.summingInt(Integer::intValue)) / stats.get(this.previous).size());
        
    }

    @Override
    public void bids(final int own, final int other) {
        if(this.round > 0){
            if(stats.get(this.previous) == null)
            {
                stats.put(this.previous, new ArrayList<Integer>());
            }
            stats.get(this.previous).add(other);
        }
        this.previous = own;
        this.opponentPrevious = other;
    	this.cash -= own;
        this.opponentCash -= other;
        if(own > other){
            this.wonQuantity += 2;
        } else if(own == other){
            this.wonQuantity++;
        }
        this.round++;
    }

}

