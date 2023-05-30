package system.main.model.attacks;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import system.main.model.character.Battle;
import system.main.model.character.Character;

public class HealAction implements AttackAction {

    private int health;
    private int allies;
    private boolean self;

    public HealAction(int health, int numberToHeal, boolean self) {
        this.health = health;
        this.allies = numberToHeal;
        this.self = self;
    }
    
    @Override
    public void actOn(Battle battle) {
        List<Character> toHeal = List.copyOf(battle.getAttackers().getActive());
        Collections.shuffle(toHeal);
        toHeal.remove(battle.getActive());
        for (int i=0; i<allies; i++) {
            if (i == toHeal.size()) {
                break;
            }
            toHeal.get(i).heal(health);
        }
        if (self) {
            battle.getActive().heal(health);
        }
    }
}