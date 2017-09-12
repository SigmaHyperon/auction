package auction.bidders.external;

import auction.Bidder;
import static java.lang.Math.min;


/**
 * @author ???        
 */
public class AdaptiveBidder implements Bidder {

    /** The next bid. */
    private int totalQuantity;
    private int totalRounds;
    private int totalCash;
    
    private int cash;
    private int wonQuantity;
    private double wonPercentage;
    
    private int opponentCash;
    
    private int round;
    private int previous;
    private int previousOpponent;
    
    private double progress;
    
    private boolean beatDown;
    
    private int threshold = 0;

    @Override
    public void init(final int quantity, final int cash) {
        this.totalQuantity = quantity;
        this.totalRounds = (int)(quantity/2);
        this.totalCash = cash;
        
    	this.cash = cash;
        this.wonQuantity = 0;
        this.wonPercentage = 0;
        
        this.opponentCash = cash;
        
        this.round = 0;
        //this.previous = (int)(this.totalCash/(this.totalRounds*2));
        this.previous = (int)((this.totalCash/(this.totalRounds*2)));
        this.previousOpponent = 0;
        this.progress = 0;
        
        this.beatDown = false;
        
        this.threshold = (int)((this.totalCash/this.totalRounds)*2.15);
    }

    @Override
    public int placeBid() {
    	if((this.cash/(this.totalRounds-this.round)) <= (double)5 || this.beatDown){
    		this.beatDown = true;
    		if(this.previousOpponent <= 10){
    			//return (this.previousOpponent+1 > this.cash/((this.totalQuantity/2+1)-this.wonQuantity)) ? this.previousOpponent+1 : this.cash/((this.totalQuantity/2+1)-this.wonQuantity) ;
    			return this.previousOpponent+1;
    		}
    		return 2;
    	}
        if(this.previous > this.previousOpponent) {
            return (int)((this.previousOpponent+this.previous)/2)+1;
//        	return this.previousOpponent + 1;
        } else if(this.previous == this.previousOpponent) {
            return this.previous + 1; 
        } else {
        	if(this.progress < 0.75){
        		if(this.previous == this.threshold){
        			return 1;
        		}
        		return min(this.previous + (int)((this.totalRounds-this.round*0.7)*0.9) + 1,this.threshold);
        	} else {
        		return 1;
        	}
            
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
        this.progress = this.round / this.totalRounds;
        this.wonPercentage = this.wonQuantity/this.totalQuantity;
    }

}
