package auction.bidders.examples;

import auction.Bidder;


/**
 * @author ???        
 */
public class PyramidBidder implements Bidder {

    /** The next bid. */
    private int totalQuantity;
    private int totalRounds;
    private int totalCash;
    
    private int cash;
    private int wonQuantity;
    
    private int opponentCash;
    
    private int round;

    @Override
    public void init(final int quantity, final int cash) {
        this.totalQuantity = quantity;
        this.totalRounds = (int)(quantity/2);
        this.totalCash = cash;
        
    	this.cash = cash;
        this.wonQuantity = 0;
        
        this.opponentCash = cash;
        
        this.round = 0;
    }

    @Override
    public int placeBid() {
        double avg = ((double)this.totalCash/(double)this.totalRounds);
        return (int)(((double)this.round/(double)this.totalRounds)*2*avg);
        
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
        this.round++;
    }

}
