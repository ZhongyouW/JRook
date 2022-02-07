import java.util.ArrayList;

public class Team {
    ArrayList<Player> team;
    public int points;
    public int wins;
    public Team() {
        this.team = new ArrayList<Player>();
    }

    public Team(ArrayList<Player> team) {
        this.team = team;
    }

    public void add(Player player) {
        team.add(player);
    }
}
