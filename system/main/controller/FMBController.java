package system.main.controller;

import java.util.List;

import system.main.model.character.AttackType;
import system.main.model.character.Team;
import system.main.model.database.CSVCharacterDatabase;
import system.main.model.database.CharacterDatabase;
import system.main.model.searching.Searcher;
import system.main.view.UserInterface;
import system.main.model.character.Character;

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
    }

    /**
     * Initiates (but does not start) a battle between two players or between one player and the computer.
     * 
     * @param isMultiplayer True if this is a two player battle, false if it's one player vs the computer.
     */
    public void initiateBattle(boolean isMultiplayer) {
        team1 = new Team("Team 1");
        team2 = new Team("Team 2");
        battleSession = new BattleSession(isMultiplayer, team1, team2, ui);
        ui.putMessage("Please fill out the battle teams!");
    }

    public List<Character> searchDatabase(Searcher searcher, String searchString) {
        List<Character> results = db.searchDatabase(searcher, searchString);
        return results;
    }

    /**
     * Starts the battle between the two teams.
     */
    public void startBattle() {
        battleSession.startBattle();
    }

    public void launchAttack(AttackType type) {
        battleSession.playerAttack(type);
    }


}
