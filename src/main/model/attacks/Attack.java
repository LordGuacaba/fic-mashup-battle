package src.main.model.attacks;

import java.util.LinkedList;
import java.util.List;

import src.main.model.exceptions.FMBException;

public class Attack {
    
    private final String name;
    private final String description;
    private final int energy;
    private final int startingEnergy;
    private int currentEnergy;
    private List<AttackAction> actions;

    public Attack(String name, String description, int energy, int startingEnergy) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.startingEnergy = startingEnergy;
        this.currentEnergy = startingEnergy;
        this.actions = new LinkedList<>();
    }

    public Attack(String name, String description, int energy) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.startingEnergy = energy;
        this.currentEnergy = energy;
        this.actions = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void addAction(AttackAction action) {
        this.actions.add(action);
    }

    public List<AttackAction> getActions() throws FMBException {
        if (!isReady()) {
            throw new FMBException("Attack does not have sufficient energy.");
        }
        return actions;
    }

    public boolean isReady() {
        return currentEnergy == energy;
    }

    public void gainEnergy() {
        if (currentEnergy < energy) {
            currentEnergy++;
        }
    }

    public void deplete() {
        currentEnergy = 0;
    }

    public void modifyDamage(double modifier) {
        for (AttackAction action : this.actions) {
            if (action instanceof DamageAction) {
                DamageAction damager = (DamageAction)action;
                damager.modifyDamage(modifier);
            }
        }
    }

    public void reset() {
        currentEnergy = startingEnergy;
    }

    @Override
    public String toString() {
        return name + " | " + currentEnergy + "/" + energy + "\n" + description;
    }
}
