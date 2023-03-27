package system.model.character;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a team of 5 characters that may battle another team
 * 
 * @author Will Hoover
 */
public class Team {
    
    private List<Character> active;
    private final Queue<Character> knockedOut;

    public Team() {
        this.active = new ArrayList<>();
        this.knockedOut = new ArrayDeque<>();
    }

    public List<Character> getActive() {
        return this.active;
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
            if (charIndex < active.size()) {
                adjacent.add(active.get(charIndex+1));
            }
            return adjacent;
        }
    }

    public boolean addCharacter(Character character) {
        if (active.size() > 4) {
            return false;
        } else {
            active.add(character);
            return true;
        }
    } 

    public boolean addCharacter(Character character, int position) {
        if (active.size() > 4) {
            return false;
        } else {
            active.add(position-1, character);
            return true;
        }
    }

    public void knockOut(Character character) {
        if (active.contains(character) && character.turnState() == TurnState.KNOCKED_OUT) {
            active.remove(character);
            knockedOut.add(character);
        }
    }
}
