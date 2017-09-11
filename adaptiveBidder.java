package auction.bidders.examples;

import auction.Bidder;


/**
 * @author ???        
 */
public class adaptiveBidder implements Bidder {

    /** The next bid. */
    private int totalQuantity;
    private int totalRounds;
    private int totalCash;
    
    private int cash;
    private int wonQuantity;
    
    private int opponentCash;
    
    private int round;
    private int previous;
    private int previousOpponent;

    @Override
    public void init(final int quantity, final int cash) {
        this.totalQuantity = quantity;
        this.totalRounds = (int)(quantity/2);
        this.totalCash = cash;
        
    	this.cash = cash;
        this.wonQuantity = 0;
        
        this.opponentCash = cash;
        
        this.round = 0;
        this.previous = (int)(this.totalCash/(this.totalRounds*2));
        this.previousOpponent = 0;
    }

    @Override
    public int placeBid() {
        if(this.previous > this.previousOpponent) {
            return (int)((this.previousOpponent+this.previous)/2)+1;
        } else if(this.previous == this.previousOpponent) {
            return this.previous + 1; 
        } else {
            return this.previous + (int)((this.totalRounds-this.round)*0.8) + 1;
        }
        
    }

    @Override
    public void bids(final int own, final int other) {
    	this.cash -= own;
        this.opponentCash -= other;
        if(own > other){
            this.wonQuantity += 2;
        } else if(own == other){
            this.wonQuantity++;
        }
        this.round ++;
        this.previous = own;
        this.previousOpponent = other;
    }

}
