package auction.bidders.external;

import auction.Bidder;


/**
 * @author ???        
 */
public class AdvancedBidder implements Bidder {

    /** The next bid. */
    private int totalQuantity;
    private int totalRounds;
    private int totalCash;
    
    private int cash;
    private int wonQuantity;
    
    private int opponentCash;
    
    private int round;
    private int avg;

    @Override
    public void init(final int quantity, final int cash) {
        this.totalQuantity = quantity;
        this.totalRounds = (int)(quantity/2);
        this.totalCash = cash;
        
    	this.cash = cash;
        this.wonQuantity = 0;
        
        this.opponentCash = cash;
        
        this.round = 0;
        this.avg = (int)(this.totalCash/this.totalRounds);
    }

    @Override
    public int placeBid() {
    	if (this.round > this.totalQuantity / 35) {
            if(this.totalRounds - this.round >= this.cash/2){
                return 2;
            }
            int avg = (this.totalCash - this.opponentCash) / this.round;
            return (int)(avg*(
                        1.75
                        +0.15*(this.totalRounds-this.round)/this.totalRounds)
                        //+1*(0.6-((this.round*2-this.wonQuantity)/(this.round*2)))
                    )
                    //+((Math.random() > 0.5) ? 1 : 0)
                    ;
    	} else {
            return 0;
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
    }

}
