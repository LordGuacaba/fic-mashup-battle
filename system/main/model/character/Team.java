package system.main.model.character;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a team of 5 characters that may battle another team.
 * 
 * @author Will Hoover
 */
public class Team implements CharacterObserver {
    
    private String teamName;
    private List<Character> active;
    private final Queue<Character> knockedOut;

    public Team(String name) {
        this.teamName = name;
        this.active = new ArrayList<>();
        this.knockedOut = new ArrayDeque<>();
    }

    public List<Character> getActive() {
        return this.active;
    }

    public int size() {
        return this.active.size();
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String name) {
        this.teamName = name;
    }

    public List<Character> getAdjacent(Character character) {
        if (!active.contains(character)) {
            return null;
        } else {
            List<Character> adjacent = new LinkedList<>();
            int charIndex = active.indexOf(character);
            if (charIndex > 0) {
                adjacent.add(active.get(charIndex-1));
            }
            if (charIndex < active.size()-1) {
                adjacent.add(active.get(charIndex+1));
            }
            return adjacent;
        }
    }

    public boolean isOnTeam(Character character) {
        return this.active.contains(character);
    }

    public boolean addCharacter(Character character) {
        if (active.size() > 4) {
            return false;
        } else {
            active.add(character);
            character.joinTeam();
            character.register(this);
            return true;
        }
    } 

    public boolean addCharacter(Character character, int position) {
        if (active.size() > 4 || active.contains(character)) {
            return false;
        } else {
            active.add(position-1, character);
            character.joinTeam();
            character.register(this);
            return true;
        }
    }

    public boolean removeCharacter(Character character) {
        if (active.size() == 0) {
            return false;
        } else {
            active.remove(character);
            character.leaveTeam();
            return true;
        }
    }

    private void knockOut(Character character) {
        if (active.contains(character) && character.turnState() == TurnState.KNOCKED_OUT) {
            active.remove(character);
            knockedOut.add(character);
        }
    }

    public boolean defeated() {
        return this.knockedOut.size() == 5;
    }

    public void clear() {
        for (Character character : List.copyOf(active)) {
            character.leaveTeam();
        }
        for (Character character : List.copyOf(knockedOut)) {
            character.leaveTeam();
        }
    }

    @Override
    public void notifyObserver(Character character) {
        switch (character.turnState()) {
            case KNOCKED_OUT:
                knockOut(character);
                break;
            case NOT_BATTLING:
                active.remove(character);
                knockedOut.remove(character);
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(teamName + "\n");
        for (Character character : this.active) {
            if (character == null) {
                builder.append("      ");
            } else {
                builder.append(character.shortString());
            }
            builder.append(" | ");
        }
        String out = builder.toString();
        out += "\b\b\b";
        return out;
    }

}
