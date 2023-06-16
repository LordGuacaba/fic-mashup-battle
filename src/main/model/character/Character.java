package src.main.model.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import src.main.controller.MessageLogger;
import src.main.model.attacks.Attack;
import src.main.model.effects.StatusEffect;
import src.main.model.exceptions.FMBException;
/**
 * Represents a character in the fiction mashup battle. May battle other characters.
 * 
 * @author Will Hoover
 */
public class Character {
    
    private final String name;
    private String[] affiliations;
    private String description;

    private final int attack;
    private double attackMod;
    private double defenseMod;
    private final int maxHealth;
    private int currentHealth;
    private final int speed;
    private Map<AttackType, Attack> attacks;

    private TurnState turnState;
    private List<StatusEffect> effects;
    private List<CharacterObserver> observers;
    private MessageLogger logger;

/**
 * Creates a character with its name, affiliations, and basic stats.
 * 
 * @param name The name of the character.
 * @param affiliations The affiliations of the character.
 * @param description A description of the character.
 * @param attack The character's attack power - applied to every attack.
 * @param maxHealth The maximum health of the character - character starts at this health.
 * @param speed The character's speed - determines turn order in a battle.
 */
    public Character(String name, String[] affiliations, String description, int attack, int maxHealth, int speed) {
        this.name = name;
        this.affiliations = affiliations;
        this.description = description;
        this.attack = attack;
        this.maxHealth = maxHealth;
        this.speed = speed;

        this.currentHealth = maxHealth;
        this.attackMod = 1;
        this.defenseMod = 1;
        this.attacks = new HashMap<>();
        this.turnState = TurnState.NOT_BATTLING;
        this.effects = new LinkedList<>();
        this.observers = new LinkedList<>();
        this.logger = MessageLogger.getInstance();
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

    private void notifyObservers() {
        for (CharacterObserver observer : this.observers) {
            observer.notifyObserver(this);
        }
    }

    public void register(CharacterObserver observer) {
        this.observers.add(observer);
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
    public String[] getAffiliations() {
        return this.affiliations;
    }

    /**
     * Returns the character's description.
     * 
     * @return The character's description.
     */
    public String getDescription() {
        return this.description;
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
     * Returns a list of the character's attacks.
     * 
     * @return The character's attacks.
     */
    public List<Attack> getAttacks() {
        List<Attack> attackList = new ArrayList<>();
        AttackType[] types = {AttackType.BASIC, AttackType.SUPER, AttackType.ULTIMATE};
        for (AttackType type : types) {
            attackList.add(attacks.get(type));
        }
        return attackList;
    }

    /**
     * Called when the character is added to a team. The turn state is set to resting to 
     * allow them to participate in battle.
     */
    public void joinTeam() {
        this.turnState = TurnState.RESTING;
        notifyObservers();
    }

    public void leaveTeam() {
        this.turnState = TurnState.NOT_BATTLING;
        currentHealth = maxHealth;
        attackMod = 1;
        defenseMod = 1;
        for (Attack attack : getAttacks()) {
            attack.reset();
        }
        notifyObservers();
        this.observers.clear();
        this.effects.clear();
    }

    /**
     * Called when the character is taking their turn in a battle. The turnState is set to 
     * ready and any necessary status effects are applied.
     */
    public void startTurn() {
        if (turnState == TurnState.RESTING) {
            turnState = TurnState.READY;
            for (AttackType type : attacks.keySet()) {
                attacks.get(type).gainEnergy();
            }
            notifyEffects();
        } else {
            logger.logMessage("Issue with state: " + turnState);
            turnState = TurnState.READY;
            for (AttackType type : attacks.keySet()) {
                attacks.get(type).gainEnergy();
            }
            notifyEffects();
        }
    }

    /**
     * Returns the attack of the selected {@link AttackType} being performed. Returns only when
     * this character is ready to attack.
     * 
     * @param type The type of attack being performed
     * @return The attack to perform with all necessary modifications
     */
    public Attack attack(AttackType type) throws FMBException {
        Attack attack = attacks.get(type);
        if (attack == null) {
            logger.logMessage("Please use a valid attack type");
            return null;
        }
        if (!attack.isReady()) {
            throw new FMBException("Attack does not have sufficient energy");
        }
        else if (turnState == TurnState.READY) {
            turnState = TurnState.ATTACKING;
            notifyEffects();
            attack.modifyDamage(attackMod * this.attack);
            return attack;
        } else {
            throw new FMBException(name + " is not ready to attack");
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
            this.notifyObservers();
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
        String out = name + ": " + attack + " attack power, " + speed + " speed, " + currentHealth + "/" + maxHealth + " HP\n";
        out += description + "\n";
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

    public String shortString() {
        String out = name + ": " + currentHealth + "/" + maxHealth + " HP";
        return out;
    }

}
