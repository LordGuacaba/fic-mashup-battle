package src.main.controller;

import java.util.List;

import src.main.model.character.AttackType;
import src.main.model.character.Character;
import src.main.model.character.Team;
import src.main.model.database.CSVCharacterDatabase;
import src.main.model.database.CharacterDatabase;
import src.main.model.searching.Searcher;
import src.main.view.UserInterface;

/**
 * Allows an FMB client to interact with the system through a set of allowed user operations.
 * 
 * @author Will Hoover
 */
public class FMBController {

    private CharacterDatabase db;
    private BattleSession battleSession;
    private UserInterface ui;
    private Team team1;
    private Team team2;
    
    /**
     * Constructor that initiates a controller with the proper back-end in place.
     */
    public FMBController(UserInterface ui) {
        db = new CSVCharacterDatabase();
        battleSession = null;
        this.ui = ui;
        team1 = new Team("Team 1");
        team2 = new Team("Team 2");
    }

    /**
     * Displays and returns team 1.
     * 
     * @return the current state of team 1.
     */
    public Team getTeam1(boolean display) {
        if (display) ui.displayTeam(team1);
        return team1;
    }

    /**
     * Displays and returns team 2.
     * 
     * @return the current state of team 2.
     */
    public Team getTeam2(boolean display) {
        if (display) ui.displayTeam(team2);
        return team2;
    }

    /**
     * Adds a character to the specified team (should be team 1 or team 2) if not full.
     * 
     * @param character The character to be added.
     */
    public void addToTeam(Team team, Character character) {
        if (!team.addCharacter(character)) {
            ui.putMessage(character.getName() + " could not be added.");
        } else {
            ui.displayTeam(team);
        }
    }

    /**
     * Removes the specified character from the team.
     * 
     * @param team The team to remove the character from.
     * @param character The character to remove from the team.
     */
    public void removeFromTeam(Team team, Character character) {
        if (!team.removeCharacter(character)) {
            ui.putMessage(team.getTeamName() + " is empty!");
        } else {
            ui.displayTeam(team);
        }
    }


    /**
     * Searches the database for characters matching the search specification and displays and returns
     * them to the UI.
     * 
     * @param searcher The searcher to be used.
     * @param searchString The search string to match against.
     * @return A list of characters that match the search parameters.
     */
    public List<Character> searchDatabase(Searcher searcher, String searchString) {
        if (searcher == null) {
            ui.putMessage("No search category specified");
            return null;
        }
        List<Character> results = db.searchDatabase(searcher, searchString);
        ui.displayCharacters(results);
        return results;
    }

    /**
     * Tells the UI to display the given character.
     * 
     * @param character The character to be displayed.
     */
    public void viewCharacter(Character character) {
        ui.displayCharacter(character);
    }

    /**
     * Starts the battle between the two teams.
     */
    public void startBattle(boolean isMultiplayer) {
        battleSession = new BattleSession(isMultiplayer, team1, team2, ui);
        battleSession.startBattle();
    }

    /**
     * Launches an attack in the battle of the specified type.
     * 
     * @param type The type of attack to be launched.
     */
    public void launchAttack(AttackType type) {
        if (battleSession == null) {
            ui.putMessage("No battle has been initiated!");
        } else {
            battleSession.playerAttack(type);
        }
    }

    /**
     * If a battle is in progress, the active character is displayed to the user.
     */
    public void viewActiveCharacter() {
        if (battleSession == null) {
            ui.putMessage("No battle has been initiated.");
        }
        Character active = battleSession.getActiveCharacter();
        if (active == null) {
            ui.putMessage("No battle in progress.");
        } else {
            ui.displayCharacter(active);
        }
    }

    public void setAttackTarget(Character character) {
        battleSession.setTarget(character);
    }

    public void endBattle() {
        try {
            battleSession.endBattle();
            battleSession = null;
            ui.putMessage("The battle has been ended.");
        } catch (NullPointerException e) {
            ui.putMessage("No battle in progress");
        }
    }


}
