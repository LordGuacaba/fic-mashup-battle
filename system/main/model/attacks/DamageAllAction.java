package system.main.model.attacks;

import java.util.List;

import system.main.model.character.Battle;
import system.main.model.character.Character;

public class DamageAllAction extends DamageAction {

    private double allDamage;
    private int currentAllDamage;

    public DamageAllAction(double damage) {
        super(damage);
        allDamage = damage;
        currentAllDamage = 1;
    }

    @Override
    public void modifyDamage(double modifier) {
        super.modifyDamage(modifier);
        allDamage *= modifier;

    }

    @Override
    public void actOn(Battle battle) {
        super.actOn(battle);
        currentAllDamage *= allDamage;
        List<Character> secondaries = List.copyOf(battle.getDefenders().getActive());
        for (Character character : secondaries) {
            if (character != battle.getTarget()) character.takeDamage(currentAllDamage);
        }
        currentAllDamage = 1;
    }
    
}
