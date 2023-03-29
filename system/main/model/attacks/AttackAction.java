package system.main.model.attacks;

import system.main.model.character.Battle;

public interface AttackAction {
    
    /**
     * Performs this action on the necessary characters in the battle.
     * 
     * @param battle The battle during which the attack occurs.
     */
    void actOn(Battle battle);
}
