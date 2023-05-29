package system.main.model.character;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

import system.main.model.attacks.Attack;
import system.main.model.attacks.AttackAction;
import system.main.model.exceptions.FMBException;

public class Battle implements CharacterObserver {
    
    private Team attackers;
    private Team defenders;
    private Queue<Character> turnOrder;
    private Character active;
    private Character target;
    private boolean isOver;

    public Battle(Team team1, Team team2) {
        attackers = team1;
        defenders = team2;
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
        isOver = false;
        try {startTurn();} catch (FMBException e) {}
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

    public boolean isOver() {
        return isOver;
    }

    public void setTarget(Character character) {
        if (defenders.isOnTeam(character)) {
            this.target = character;
        }
    }

    public void startTurn() throws FMBException {
        if (isOver) {
            throw new FMBException("Battle is over!");
        }
        turnOrder.add(active);
        active = turnOrder.poll();
        if (defenders.isOnTeam(active)) {
            Team temp = defenders;
            defenders = attackers;
            attackers = temp;
            target = attackers.getActive().get(0);
        }
        active.startTurn();
    }

    public void takeTurn(AttackType type) throws FMBException {
        Attack attack = active.attack(type);
        for (AttackAction action : attack.getActions()) {
            action.actOn(this);
        }
        active.endTurn();
        if (defenders.size() == 0) {
            isOver = true;
        }
    }

    @Override
    public void notifyObserver(Character character) {
        if (character.turnState() == TurnState.KNOCKED_OUT) {
            turnOrder.remove(character);
        }
    }
}
