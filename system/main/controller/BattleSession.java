package system.main.controller;

import java.util.Random;

import system.main.model.character.AttackType;
import system.main.model.character.Battle;
import system.main.model.character.Team;
import system.main.model.exceptions.FMBException;
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
        this.battle = null;
    }

    public boolean playerAttack(AttackType type) {
        try {
            battle.takeTurn(type);
        } catch (FMBException e) {
            ui.putMessage(e.getMessage());
            return false;
        }
        // ui.displayBattle(Battle);
        battle.startTurn();
        if (!isMultiplayer) {
            Random random = new Random();
            while (team1 == battle.getDefenders()) {
                int position = random.nextInt(team1.getActive().size());
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
