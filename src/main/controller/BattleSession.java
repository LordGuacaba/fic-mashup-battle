package src.main.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import src.main.model.character.AttackType;
import src.main.model.character.Battle;
import src.main.model.character.Character;
import src.main.model.character.Team;
import src.main.model.database.CSVCharacterDatabase;
import src.main.model.database.CharacterDatabase;
import src.main.model.exceptions.FMBException;
import src.main.model.searching.AffiliationSearcher;
import src.main.model.searching.Searcher;
import src.main.view.UserInterface;

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
                if (battle.isOver()) {
                    battle.end();
                }
                try {
                    battle.startTurn();
                    ui.displayBattle(battle);
                } catch (FMBException e) {
                    ui.putMessage("The battle is over, " + battle.getAttackers().getTeamName() + " wins!");
                    battle = null;
                }
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
            Collections.shuffle(evils);
            int counter = 0;
            while (team2.size() < 5) {
                if (!team2.isOnTeam(evils.get(counter))) {
                    team2.addCharacter(evils.get(counter));
                }
                counter++;
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
        if (battle.isOver()) {
            battle.end();
            ui.putMessage("The battle is over, " + battle.getAttackers().getTeamName() + " wins!");
            battle = null;
            return true;
        }
        try {
            battle.startTurn();
        } catch (FMBException e) {
            ui.putMessage("The battle is over, " + battle.getAttackers().getTeamName() + " wins!");
            battle = null;
            return true;
        }
        ui.displayBattle(battle);
        runTeamAI();
        return true;
    }

    public void setTarget(Character character) {
        if (battle.setTarget(character)) ui.displayBattle(battle);
        
    }

    public Character getActiveCharacter() {
        if (battle != null) {
            return battle.getActive();
        } else {
            return null;
        }
    }

    public void endBattle() {
        battle.end();
        battle = null;
    }
    
}
