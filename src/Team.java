import java.util.ArrayList;

public class Team {
    ArrayList<Player> team;
    public int points;
    public int wins;
    public Hand taken;
    public Team() {
        this.team = new ArrayList<Player>();
        taken = new Hand();
    }

    public void add(Player player) {
        team.add(player);
        player.team = this;
    }

    public int getPoints() {
        return taken.getValue();
    }
    
    public String toString() {
        String name = team.get(0).name + " and " + team.get(1).name;
        return name;
    }
}
