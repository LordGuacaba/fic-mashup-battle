package src.main.model.effects;

import src.main.model.character.Character;
import src.main.model.character.TurnState;

/**
 * {@link StatusEffect} that increases the target character's attack power.
 * 
 * @author Will Hoover
 */
public class OffenseUp extends StatusEffect {

    @Override
    public boolean notify(Character character) {
        if (character.turnState() == TurnState.ATTACKING) {
            character.modifyAttack(1.5);
        }
        return false;
    }

    @Override
    public boolean isPositive() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OffenseUp) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "OU";
    }
    
}
