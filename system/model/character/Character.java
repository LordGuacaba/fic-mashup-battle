package system.model.character;

import java.util.LinkedList;
import java.util.List;

import system.model.attacks.Attack;
import system.model.effects.StatusEffect;
/**
 * Represents a character in the fiction mashup battle. May battle other characters.
 * 
 * @author Will Hoover
 */
public class Character {
    
    private final String name;
    private List<String> affiliations;

    private final int attack;
    private double attackMod;
    private double defenseMod;
    private final int maxHealth;
    private int currentHealth;
    private final int speed;
    private Attack basicAttack;
    private Attack superAttack;
    private Attack ultimateAttack;

    private TurnState turnState;
    private List<StatusEffect> effects;

    public Character(String name, List<String> affiliations, int attack, int maxHealth, int speed) {
        this.name = name;
        this.affiliations = affiliations;
        this.attack = attack;
        this.maxHealth = maxHealth;
        this.speed = speed;

        this.currentHealth = maxHealth;
        this.attackMod = 1;
        this.defenseMod = 1;
        this.basicAttack = null;
        this.superAttack = null;
        this.ultimateAttack = null;
        this.turnState = TurnState.NOT_BATTLING;
        this.effects = new LinkedList<>();
    }

    private void notifyEffects() {
        for (StatusEffect effect : this.effects) {
            if (effect.notify(this)) {
                this.effects.remove(effect);
            }
        }
    }

    public int getSpeed() {
        return this.speed;
    }

    public TurnState turnState() {
        return this.turnState;
    }

    public int[] getHealth() {
        int[] health = {currentHealth, maxHealth};
        return health;
    }

    public void startTurn() {
        if (turnState == TurnState.RESTING) {
            turnState = TurnState.READY;
            notifyEffects();
        } else {
            throw new UnsupportedOperationException("Character unable to start turn");
        }
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            this.turnState = TurnState.KNOCKED_OUT;
        }
    }

    public void heal(int health) {
        this.currentHealth += health;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    @Override
    public String toString() {
        String out = name + ": " + attack + " attack power, " + speed + " speed\n";
        if (this.effects.size() > 0) {
            StringBuilder effectString = new StringBuilder();
            for (StatusEffect effect : this.effects) {
                effectString.append(effect);
                effectString.append(" ");
                }
            effectString.append("\b");
            out += effectString.toString() + "\n";
        }
        return out;
    }

}