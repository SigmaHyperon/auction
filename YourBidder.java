package auction.bidders.external;

import auction.Bidder;


/**
 * @author ???          -------Bitte hier ausfüllen--------
 */
public class YourBidder implements Bidder {

    /** The next bid. */
    private int avgBid;
    
    private int initCash;
    private int cash;
    private int opponentCash;
    private double opponentAvg;
    
    private int totalRounds;
    private int round;

    @Override
    public void init(final int quantity, final int cash) {
    	this.initCash = cash;
    	this.cash = cash;
    	this.opponentCash = cash;
    	this.avgBid = cash/quantity;
    	this.round = 0;
    	this.totalRounds = quantity;
    }

    @Override
    public int placeBid() {
    	
        /** Calculate your next bid here. */
    	if(round > (this.totalRounds/35)){
	    	
	    	if(this.opponentCash > 0)
	    	{
	    		double avg = (this.initCash-this.opponentCash)/this.round;
	    		if(avg*2 < this.cash){
	    			//return (int)((int)(avg*2)+(avg*0.2*((Math.random()-0.5)*2)));
	    			return (int)(avg*2);
	    		} else {
	    			return this.cash;
	    		}
	    	} else {
	    		if(this.cash > 0){
	    			return 1;
	    		} else {
	    			return 0;
	    		}
	    	}
    	} else {
    		return 0;
    	}
    	
    }

    @Override
    public void bids(final int own, final int other) {
    	this.cash -= own;
    	this.opponentCash -= other;
    	this.round++;
    }

}