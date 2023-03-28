package system.model.attacks;

import java.util.List;

import system.model.character.Battle;
import system.model.character.Character;

/**
 * Represents a damaging effect of an attack that affects targets and their
 * adjacent characters.
 * 
 * @author Will Hoover
 */
public class SplashDamageAction extends DamageAction {

    private double splashModifier;
    private int currentSplash;

    public SplashDamageAction(double damage, double splashDamage) {
        super(damage);
        this.splashModifier = splashDamage;
        this.currentSplash = 1;
    }
    
    public SplashDamageAction(double damage) {
        super(damage);
        this.splashModifier = damage;
        this.currentSplash = 1;
    }

    @Override
    public void modifyDamage(double modifier) {
        super.modifyDamage(modifier);
        currentSplash *= modifier;

    }

    @Override
    public void actOn(Battle battle) {
        super.actOn(battle);
        currentSplash *= splashModifier;
        List<Character> secondaries = battle.getDefenders().getAdjacent(battle.getTarget());
        for (Character character : secondaries) {
            character.takeDamage(currentSplash);
        }
    }
    
}
