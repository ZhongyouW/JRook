/*import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Round round;

    public static void main(String[] args) {
        RoundTest roundTest = new RoundTest();
        roundTest.initRound();
    }

    void initRound() {
        Player[] players = {new PwnPawn(), new PwnPawn(), new Human("Johnny"), new PwnPawn()};
        System.out.printf("Players initiated:\n%s\n%s\n%s\n%s\n\n", players[0].name,players[1].name,players[2].name,players[3].name);
        round = new Round(players);
        System.out.println("Round initiated");
        for(Player player : players) {
            System.out.println(player.hand);
        }
    }

    @Test
    void biddingTest() {
        initRound();
        assertEquals(1,1);
    }
}*/