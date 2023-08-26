package src.main.model.effects;

import src.main.model.character.Character;
import src.main.model.character.TurnState;

public class DefenseUp extends StatusEffect {

    @Override
    public boolean notify(Character character) {
        if (character.turnState() == TurnState.ATTACKING) {
            character.modifyDefense(1.5);
        }
        return false;
    }

    @Override
    public boolean isPositive() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DefenseUp) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "DU";
    }
    
}
