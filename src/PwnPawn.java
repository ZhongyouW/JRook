/** A basic agent
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class PwnPawn extends Agent{
    double riskTolerance = 0;
    public PwnPawn(Hand hand) {
        super("PwnPawn", hand);
        riskTolerance = Math.random();
    }

    @Override
    public int getBid(int currentBid) {
        int value = hand.getValue();
        int preferredBid = ((int)Math.round((value * 6 * riskTolerance)/5))*5;
        if(currentBid >= preferredBid) {
            return preferredBid;
        }
        return -1;
    }
}
