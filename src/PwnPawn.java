/** A basic agent
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class PwnPawn extends Agent{
    double riskTolerance = 0;
    static int population = 0;

    public PwnPawn() {
        super("PwnPawn "+population);
        riskTolerance = Math.random()*0.5 + 0.25;
        population++;
    }

    public PwnPawn(Hand hand) {
        super("PwnPawn "+population, hand);
        riskTolerance = Math.random();
        population++;
    }

    @Override
    public int getBid(int currentBid) {
        int value = hand.getValue();
        int preferredBid = ((int)Math.round((value * 4.5 * riskTolerance)/5))*5;
        //Clamp preferredBid between 200 and 70
        preferredBid = Math.max(70, Math.min(200, preferredBid));
        if(currentBid < preferredBid) {
            return currentBid += 5;
        }
        return -1;
    }
}
