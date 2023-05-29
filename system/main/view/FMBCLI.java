package system.main.view;

import java.util.List;

import system.main.model.character.Battle;
import system.main.model.character.Character;
import system.main.model.character.Team;

/**
 * A {@link UserInterface} for FMB run from the command line.
 * 
 * @author Will Hoover
 */
public class FMBCLI implements UserInterface {

    @Override
    public void putMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayBattle(Battle battle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayBattle'");
    }

    @Override
    public void displayTeam(Team team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayTeam'");
    }

    @Override
    public void displayCharacters(List<Character> characters) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayCharacters'");
    }

    @Override
    public void displayCharacter(Character character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayCharacter'");
    }
    
}
