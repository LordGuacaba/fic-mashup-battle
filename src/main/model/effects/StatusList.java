package src.main.model.effects;

import java.util.ArrayList;
import java.util.List;
import src.main.model.character.Character;

/**
 * Represents a list of {@link StatusEffect}s on a {@link Character} in battle. 
 * 
 * @author Will Hoover
 */
public class StatusList {
    
    private List<StatusEffect> effects;

    public StatusList() {
        this.effects = new ArrayList<>();
    }

    /**
     * If a {@link StatusEffect} of the same type already exists within the list, add the number of the given effect
     * to the stack on the preexisting one. Otherwise, add the given {@link StatusEffect} as a new effect.
     * 
     * @param effect The {@link StatusEffect} to be added.
     */
    public void addStack(StatusEffect effect) {
        if (effects.contains(effect)) {
            StatusEffect stackOn = effects.get(effects.indexOf(effect));
            stackOn.addToStack(effect.getStack());
        } else {
            effects.add(effect);
        }
    }

    /**
     * Adds the given {@link StatusEffect} to the list as a new effect. The effect will perform its action along with
     * any other effects of the same type already in the list.
     * 
     * @param effect The {@link StatusEffect} to be added.
     */
    public void addNew(StatusEffect effect) {
        effects.add(effect);
    }

    /**
     * Clears one off the stack of each {@link StatusEffect} in the status list. If there is none left on the stack, the effect is
     * completely removed.
     */
    public void clear() {
        for (StatusEffect effect : effects) {
            if(effect.remove()) {
                effects.remove(effect);
            }
        }
    }

    /**
     * Completely removes each {@link StatusEffect} from the status list; used at the end of battle.
     */
    public void fullClear() {
        effects.clear();
    }

    /**
     * Notifies each status effect of a state change in the character.
     * 
     * @param character The character who had the state change.
     */
    public void notifyAll(Character character) {
        for (StatusEffect effect : effects) {
            effect.notify(character);
            
        }
    }

    @Override
    public String toString() {
        String out = "";
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
