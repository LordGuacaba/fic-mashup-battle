package system.model.attacks;

import system.model.character.Battle;
import system.model.character.Character;

/**
 * An abstract action that does damage to an opposing {@link Character}. The exact damage
 * is determined by the character using the attack.
 * 
 * @author Will Hoover
 */
public class DamageAction implements AttackAction {

    protected double damageModifier;
    protected int currentDamage;

    public DamageAction(double damage) {
        this.damageModifier = damage;
        this.currentDamage = 1;
    }
    
    public void modifyDamage(double modifier) {
        damageModifier *= modifier;
    }

    @Override
    public void actOn(Battle battle) {
        Character target = battle.getTarget();
        target.takeDamage(currentDamage);
    }

    
}
