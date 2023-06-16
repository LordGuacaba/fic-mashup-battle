package src.main.model.attacks;

import src.main.model.character.Battle;
import src.main.model.character.Character;

/**
 * An abstract action that does damage to an opposing {@link Character}. The exact damage
 * is determined by the character using the attack.
 * 
 * @author Will Hoover
 */
public class DamageAction implements AttackAction {

    private double damageModifier;
    private int currentDamage;

    public DamageAction(double damage) {
        this.damageModifier = damage;
        this.currentDamage = 1;
    }
    
    /**
     * Modifies the damage done by the attack.
     * 
     * @param modifier The damage multiplier - stacks onto previous effects.
     */
    public void modifyDamage(double modifier) {
        currentDamage *= modifier;
    }

    @Override
    public void actOn(Battle battle) {
        currentDamage *= damageModifier;
        Character target = battle.getTarget();
        target.takeDamage(currentDamage);
        currentDamage = 1;
    }

    
}
