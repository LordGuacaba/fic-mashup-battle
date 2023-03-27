package system.model.attacks;
/**
 * An abstract action that does damage to an opposing {@link Character}. The exact damage
 * is determined by the character using the attack.
 * 
 * @author Will Hoover
 */
public abstract class DamageAction implements AttackAction {

    protected double damageModifier;

    public DamageAction(double damage) {
        this.damageModifier = damage;
    }
    
    public void modifyDamage(double modifier) {
        damageModifier *= modifier;
    }
}
