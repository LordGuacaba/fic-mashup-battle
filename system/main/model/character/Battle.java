package system.main.model.character;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Battle {
    
    private Team attackers;
    private Team defenders;
    private Queue<Character> turnOrder;
    private Character active;
    private Character target;

    public Battle(Team team1, Team team2) {
        List<Character> initialList = team1.getActive();
        for (Character character : team2.getActive()) {
            initialList.add(character);
        }
        initialList.sort(new Comparator<Character>() {

            @Override
            public int compare(Character o1, Character o2) {
                return o1.getSpeed() - o2.getSpeed();
            }
            
        });
        turnOrder = new ArrayDeque<>();
        for (Character character : initialList) {
            turnOrder.add(character);
        }
        startTurn();
    }

    public Team getAttackers() {
        return this.attackers;
    }

    public Team getDefenders() {
        return this.defenders;
    }

    public Character getTarget() {
        return this.target;
    }

    public Character getActive() {
        return this.active;
    }

    public void startTurn() {
        turnOrder.add(active);
        active = turnOrder.poll();
        if (defenders.isOnTeam(active)) {
            Team temp = defenders;
            defenders = attackers;
            attackers = temp;
        }
        active.startTurn();
    }
}
