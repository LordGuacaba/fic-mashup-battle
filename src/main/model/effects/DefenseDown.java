package src.main.model.effects;

import src.main.model.character.Character;
import src.main.model.character.TurnState;

public class DefenseDown extends StatusEffect {

    @Override
    public boolean notify(Character character) {
        if (character.turnState() == TurnState.DEFENDING) {
            character.modifyDefense(0.5);
        }
        return false;
    }

    @Override
    public boolean isPositive() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DefenseDown) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "DD";
    }
    
}

