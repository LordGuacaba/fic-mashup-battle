package src.main.model.effects;

import src.main.model.character.Character;

/**
 * Represents a status effect on a {@link Character} during a battle.
 * 
 * @author Will Hoover
 */
public interface StatusEffect {
    
    /**
     * Notifies the status effect of a {@link Character} change in {@link TurnState}. Applies this status effect
     * and returns true if appropriate, returns false otherwise.
     * 
     * @param character The character to potentially apply status effects to.
     * @return true if the effect was applied, false otherwise.
     */
    boolean notify(Character character);
}
