package src.main.model.effects;

import src.main.model.character.Character;

/**
 * Represents a status effect on a {@link Character} during a battle.
 * 
 * @author Will Hoover
 */
public abstract class StatusEffect {

    private boolean isPositive;
    private int stack;
    
    /**
     * Initializes the Status Effect with a stack of 1.
     * @param isPositive
     */
    public StatusEffect(boolean isPositive) {
        this.stack = 1;
        this.isPositive = isPositive;
    }

    /**
     * Initializes the Status Effect.
     * 
     * @param stack
     * @param isPositive
     */
    public StatusEffect(int stack, boolean isPositive) {
        this.stack = stack;
        this.isPositive = isPositive;
    }

    /**
     * Notifies the status effect of a {@link Character} change in {@link TurnState}. Applies this status effect
     * and returns true if appropriate, returns false otherwise.
     * 
     * @param character The character to potentially apply status effects to.
     * @return true if the effect was applied and should be removed, false otherwise.
     */
    public abstract boolean notify(Character character);

    /**
     * Returns true if the effect is positive, false if it is negative.
     * @return True if the effect is positive, false if negative.
     */
    public boolean isPositive() {
        return isPositive;
    }

    /**
     * Returns the current stack of this effect.
     * 
     * @return The current stack of this effect.
     */
    public int getStack() {
        return this.stack;
    }

    /**
     * Increments the stack of this {@link StatusEffect}.
     */
    public void addToStack() {
        stack++;
    }

    /**
     * Adds the given amount to the stack.
     * 
     * @param amount The amount of this effect to add to the stack.
     */
    public void addToStack(int amount) {
        stack += amount;
    }

    /**
     * Removes one of this effect from the stack.
     * 
     * @return True if there are now none of this effect stacked, false otherwise.
     */
    public boolean remove() {
        if(--stack == 0) {
            return true;
        }
        return false;
    }
}
