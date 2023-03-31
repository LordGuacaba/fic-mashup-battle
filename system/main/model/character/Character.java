package system.main.model.character;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import system.main.model.attacks.Attack;
import system.main.model.effects.StatusEffect;
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
    private Map<AttackType, Attack> attacks;

    private TurnState turnState;
    private List<StatusEffect> effects;
/**
 * Creates a character with its name, affiliations, and basic stats.
 * 
 * @param name The name of the character.
 * @param affiliations The affiliations of the character.
 * @param attack The character's attack power - applied to every attack.
 * @param maxHealth The maximum health of the character - character starts at this health.
 * @param speed The character's speed - determines turn order in a battle.
 */
    public Character(String name, List<String> affiliations, int attack, int maxHealth, int speed) {
        this.name = name;
        this.affiliations = affiliations;
        this.attack = attack;
        this.maxHealth = maxHealth;
        this.speed = speed;

        this.currentHealth = maxHealth;
        this.attackMod = 1;
        this.defenseMod = 1;
        this.attacks = new HashMap<>();
        this.turnState = TurnState.NOT_BATTLING;
        this.effects = new LinkedList<>();
    }

    /**
     * Notifies the {@link StatusEffect}s on this character of a change in state. Each 
     * {@link StatusEffect} applies its action and is removed from the list if the character
     * is in the correct state.
     */
    private void notifyEffects() {
        for (StatusEffect effect : this.effects) {
            if (effect.notify(this)) {
                this.effects.remove(effect);
            }
        }
    }

    /**
     * Returns the character's name.
     * 
     * @return The name of the character.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the character's affiliations.
     * 
     * @return The character's affiliations.
     */
    public List<String> getAffiliations() {
        return this.affiliations;
    }

    /**
     * Returns the character's speed.
     * 
     * @return The speed of the character.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Returns the current turn state of the character.
     * 
     * @return The character's current turn state.
     */
    public TurnState turnState() {
        return this.turnState;
    }

    /**
     * Returns an array of the character's current and maximum health.
     * 
     * @return An array in the following format: [current health, max health]
     */
    public int[] getHealth() {
        int[] health = {currentHealth, maxHealth};
        return health;
    }

    /**
     * Called when the character is added to a team. The turn state is set to resting to 
     * allow them to participate in battle.
     */
    public void addToTeam() {
        this.turnState = TurnState.RESTING;
    }

    /**
     * Called when the character is taking their turn in a battle. The turnState is set to 
     * ready and any necessary status effects are applied.
     */
    public void startTurn() {
        if (turnState == TurnState.RESTING) {
            turnState = TurnState.READY;
            notifyEffects();
        } else {
            throw new UnsupportedOperationException("Character unable to start turn");
        }
    }

    /**
     * Returns the attack of the selected {@link AttackType} being performed. Returns only when
     * this character is ready to attack.
     * 
     * @param type The type of attack being performed
     * @return The attack to perform with all necessary modifications
     */
    public Attack attack(AttackType type) {
        if (turnState == TurnState.READY) {
            turnState = TurnState.ATTACKING;
            notifyEffects();
            Attack attack = attacks.get(type);
            attack.modifyDamage(attackMod * this.attack);
            return attack;
        } else {
            throw new UnsupportedOperationException("Character cannot attack while not ready");
        }
    }

    /**
     * Called to end the character's turn. Resets any changed statistics and switches to a resting state.
     */
    public void endTurn() {
        if (turnState == TurnState.ATTACKING) {
            turnState = TurnState.RESTING;
            notifyEffects();
            attackMod = 1;
            defenseMod = 1;
        }
    }

    /**
     * Adds an attack of the specified {@link AttackType}. That attack will override a previous 
     * attack of that type.
     * 
     * @param type The type of attack (basic, super, ultimate)
     * @param attack The attack being given to the character.
     */
    public void addAttack(AttackType type, Attack attack) {
        this.attacks.put(type, attack);
    }

    /**
     * The character takes the specified amount of damage. If health is reduced to 
     * zero, the character is knocked out.
     * 
     * @param damage The damage to be done to the character.
     */
    public void takeDamage(int damage) {
        this.currentHealth -= damage*defenseMod;
        if (currentHealth <= 0) {
            currentHealth = 0;
            this.turnState = TurnState.KNOCKED_OUT;
        }
    }

    /**
     * Heals the character for the specified amount of health. Health may not 
     * exceed the character's max health.
     * 
     * @param health The amount of health to heal by.
     */
    public void heal(int health) {
        this.currentHealth += health;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    @Override
    public String toString() {
        String out = name + ": " + attack + " attack power, " + speed + " speed, " + currentHealth + "/" + maxHealth + "HP\n";
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