package system.main.model.character;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import system.main.controller.MessageLogger;
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
    private MessageLogger logger;

    public Battle(Team team1, Team team2) {
        attackers = team1;
        defenders = team2;
        List<Character> initialList = new LinkedList<>();
        for (Character character : team1.getActive()) {
            initialList.add(character);
        }
        for (Character character : team2.getActive()) {
            initialList.add(character);
        }
        initialList.sort(new Comparator<Character>() {

            @Override
            public int compare(Character o1, Character o2) {
                return o2.getSpeed() - o1.getSpeed();
            }
            
        });
        turnOrder = new ArrayDeque<>();
        for (Character character : initialList) {
            character.register(this);
            turnOrder.add(character);
        }
        target = defenders.getActive().get(0);
        isOver = false;
        logger = MessageLogger.getInstance();
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

    public boolean setTarget(Character character) {
        if (defenders.isOnTeam(character)) {
            this.target = character;
            return true;
        } else {
            return false;
        }
    }

    public void startTurn() throws FMBException {
        if (isOver) {
            throw new FMBException("Battle is over!");
        }
        if (active != null) {
            turnOrder.add(active);
        }
        active = turnOrder.poll();
        if (defenders.isOnTeam(active)) {
            Team temp = defenders;
            defenders = attackers;
            attackers = temp;
            target = defenders.getActive().get(0);
        }
        logger.logMessage("It's " + active.getName() + "'s turn!");
        active.startTurn();
    }

    public void takeTurn(AttackType type) throws FMBException {
        Attack attack = active.attack(type);
        if (attack == null) {
            return;
        }
        for (AttackAction action : attack.getActions()) {
            action.actOn(this);
        }
        logger.logMessage(active.getName() + " used " + attack.getName() + " on " + target.getName());
        attack.deplete();
        active.endTurn();
        if (defenders.size() == 0) {
            isOver = true;
        }
    }

    public void end() {
        attackers.clear();
        defenders.clear();
    }

    @Override
    public void notifyObserver(Character character) {
        switch (character.turnState()) {
            case KNOCKED_OUT:
                turnOrder.remove(character);
                if (character == target) {
                    target = defenders.getActive().get(0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Attacking: " + attackers.getTeamName() + "\n");
        for (Character attacker : attackers.getActive()) {
            builder.append(attacker.shortString());
            if (active == attacker) {
                builder.append(" (active)");
            }
            builder.append(" | ");
        }
        builder.append("\n\n");
        builder.append("Defending: " + defenders.getTeamName() + "\n");
        for (Character defender : defenders.getActive()) {
            builder.append(defender.shortString());
            if (target == defender) {
                builder.append(" (target)");
            }
            builder.append(" | ");
        }
        builder.append("\n");
        return builder.toString();
    }
}
