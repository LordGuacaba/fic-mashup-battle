package system.model.attacks;

import system.model.character.Battle;

public class HealAction implements AttackAction {

    private int health;

    public HealAction(int health) {
        this.health = health;
    }
    
    @Override
    public void actOn(Battle battle) {
        battle.getActive().heal(health);
    }
}