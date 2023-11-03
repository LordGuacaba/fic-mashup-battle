package src.main.model.effects;

import src.main.model.character.Character;
import src.main.model.character.TurnState;

public class Bleed extends StatusEffect {

    public Bleed() {
        super(false);
    }

    public Bleed(int stack) {
        super(stack, false);
    }

    @Override
    public boolean notify(Character character) {
        if (character.turnState() == TurnState.READY) {
            character.takeDamage(50);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Bleed) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BL";
    }
    
}
