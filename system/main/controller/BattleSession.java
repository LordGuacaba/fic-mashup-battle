package system.main.controller;

import java.util.List;
import java.util.Random;

import system.main.model.character.AttackType;
import system.main.model.character.Battle;
import system.main.model.character.Character;
import system.main.model.character.Team;
import system.main.model.database.CSVCharacterDatabase;
import system.main.model.database.CharacterDatabase;
import system.main.model.exceptions.FMBException;
import system.main.model.searching.AffiliationSearcher;
import system.main.model.searching.Searcher;
import system.main.view.UserInterface;

/**
 * Responsible for running an FMB {@link Battle} by receiving attack orders, maintaining battle state, and updating
 * the client on battle status.
 * 
 * @author Will Hoover
 */
public class BattleSession {

    private Team team1;
    private Team team2;
    private boolean isMultiplayer;
    private Battle battle;
    private UserInterface ui;

    public BattleSession(boolean isMultiplayer, Team team1, Team team2, UserInterface ui) {
        this.isMultiplayer = isMultiplayer;
        this.team1 = team1;
        this.team2 = team2;
        this.battle = null;
        this.ui = ui;
    }

    private void runTeamAI() {
        if (!isMultiplayer) {
            Random random = new Random();
            while (team1 == battle.getDefenders()) {
                int position = random.nextInt(team1.size());
                battle.setTarget(team1.getActive().get(position));
                try {
                    battle.takeTurn(AttackType.ULTIMATE);
                } catch (FMBException e) {
                    try {
                        battle.takeTurn(AttackType.SUPER);
                    } catch (FMBException e2) {
                        try {
                            battle.takeTurn(AttackType.BASIC);
                        } catch (FMBException e3) {
                            ui.putMessage("Issue with computer attack: " + e3.getMessage());
                        }
                    }
                }
                battle.startTurn();
            }
        }
    }

    public boolean startBattle() {
        if (team1.size() < 5) {
            ui.putMessage(team1.getTeamName() + " must have 5 players");
            return false;
        } else if (isMultiplayer && team2.size() < 5) {
            ui.putMessage(team2.getTeamName() + " must have 5 players");
            return false;
        }
        if (team2.size() < 5) {
            Searcher evilSearcher = new AffiliationSearcher();
            CharacterDatabase db = new CSVCharacterDatabase();
            List<Character> evils = db.searchDatabase(evilSearcher, "Imperial");
            Random random = new Random();
            while (team2.size() < 5) {
                int position = random.nextInt(evils.size());
                team2.addCharacter(evils.get(position));
            }
        }
        battle = new Battle(team1, team2);
        ui.displayBattle(battle);
        runTeamAI();
        return true;
    }

    public boolean playerAttack(AttackType type) {
        if (battle == null) {
            ui.putMessage("Battle has not started");
            return false;
        }
        try {
            battle.takeTurn(type);
        } catch (FMBException e) {
            ui.putMessage(e.getMessage());
            return false;
        }
        ui.displayBattle(battle);
        battle.startTurn();
        if (!isMultiplayer) {
            Random random = new Random();
            while (team1 == battle.getDefenders()) {
                int position = random.nextInt(team1.size());
                battle.setTarget(team1.getActive().get(position));
                try {
                    battle.takeTurn(AttackType.ULTIMATE);
                } catch (FMBException e) {
                    try {
                        battle.takeTurn(AttackType.SUPER);
                    } catch (FMBException e2) {
                        try {
                            battle.takeTurn(AttackType.BASIC);
                        } catch (FMBException e3) {
                            ui.putMessage("Issue with computer attack: " + e3.getMessage());
                        }
                    }
                }
                battle.startTurn();
            }
        }
        return true;
    }
    
}
