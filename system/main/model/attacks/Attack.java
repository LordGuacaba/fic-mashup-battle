package system.main.model.attacks;

import java.util.LinkedList;
import java.util.List;

public class Attack {
    
    private final String name;
    private final String description;
    private final int energy;
    private int currentEnergy;
    private List<AttackAction> actions;

    public Attack(String name, String description, int energy, int startingEnergy) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.currentEnergy = startingEnergy;
        this.actions = new LinkedList<>();
    }

    public Attack(String name, String description, int energy) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.currentEnergy = energy;
        this.actions = new LinkedList<>();
    }

    public void addAction(AttackAction action) {
        this.actions.add(action);
    }

    public List<AttackAction> getActions() {
        return actions;
    }

    public boolean isReady() {
        return currentEnergy == energy;
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
        return name + " | " + currentEnergy + "/" + energy + "\n" + description;
    }
}
