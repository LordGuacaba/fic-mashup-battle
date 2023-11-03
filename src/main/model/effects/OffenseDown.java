package src.main.model.effects;

import src.main.model.character.Character;
import src.main.model.character.TurnState;

/**
 * {@link StatusEffect} that increases the target character's attack power.
 * 
 * @author Will Hoover
 */
public class OffenseDown extends StatusEffect {

    public OffenseDown() {
        super(false);
    }

    public OffenseDown(int stack) {
        super(stack, false);
    }

    @Override
    public boolean notify(Character character) {
        if (character.turnState() == TurnState.ATTACKING) {
            character.modifyAttack(0.5);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OffenseDown) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "OD";
    }
    
}
