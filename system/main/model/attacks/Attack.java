package system.main.model.attacks;

import java.util.LinkedList;
import java.util.List;

public class Attack {
    
    private final String name;
    private final String description;
    private List<AttackAction> actions;

    public Attack(String name, String description) {
        this.name = name;
        this.description = description;
        this.actions = new LinkedList<>();
    }

    public void addAction(AttackAction action) {
        this.actions.add(action);
    }

    public List<AttackAction> getActions() {
        return actions;
    }

    public void modifyDamage(double modifier) {
        for (AttackAction action : this.actions) {
            if (action instanceof DamageAction) {
                DamageAction damager = (DamageAction)action;
                damager.modifyDamage(modifier);
            }
        }
    }

    @Override
    public String toString() {
        return name + "\n" + description;
    }
}
