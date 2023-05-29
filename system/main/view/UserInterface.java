package system.main.view;

import system.main.model.character.Battle;

/**
 * Implemented by any class that directly acts as a user interface so a class may interact with it
 * as an abstraction.
 * 
 * @author Will Hoover
 */
public interface UserInterface {
    
    /**
     * Displays the given message to the user through whichever format is appropriate.
     * 
     * @param message The message to be displayed to the user.
     */
    void putMessage(String message);

    /**
     * Displays the current state of the {@link Battle} to the user.
     * 
     * @param battle The battle to be displayed.
     */
    void displayBattle(Battle battle);
}
