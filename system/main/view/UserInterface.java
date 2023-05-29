package system.main.view;

import java.util.List;

import system.main.model.character.Battle;
import system.main.model.character.Character;
import system.main.model.character.Team;

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

    /**
     * Displays the given team to the user.
     * 
     * @param team The team to be displayed.
     */
    void displayTeam(Team team);

    /**
     * Displays a list of {@link Character}s in the UI's format.
     * 
     * @param characters The characters to be displayed.
     */
    void displayCharacters(List<Character> characters);

    /**
     * Displays a {@link Character} and its attacks in the UI format.
     * 
     * @param character The character to be displayed with full details.
     */
    void displayCharacter(Character character);
}
