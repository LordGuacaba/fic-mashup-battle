package system.main.model.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import system.main.model.attacks.Attack;
import system.main.model.character.AttackType;
import system.main.model.character.Character;

public class CSVCharacterDatabase extends CharacterDatabase {

    private String characterFile;
    private String attackFile;

    public CSVCharacterDatabase() {
        super();
        characterFile = "data/characters.csv";
        attackFile = "data/attacks.csv";
    }

    private Character readCharacter(String line) {

        

        return new Character(line, null, null, 0, 0, 0);
    }

    private Attack readAttack(String line) {
        return new Attack(line, line, 0);
    }

    @Override
    protected void load() throws IOException {
        FileReader charFileReader = new FileReader(characterFile);
        BufferedReader charReader = new BufferedReader(charFileReader);
        FileReader attackFileReader = new FileReader(attackFile);
        BufferedReader attackReader = new BufferedReader(attackFileReader);

        String line = charReader.readLine();
        AttackType[] order = {AttackType.BASIC, AttackType.SUPER, AttackType.ULTIMATE};
        while (line != null) {
            Character next = readCharacter(line);
            for (int i=0; i<3; i++) {
                Attack newAttack = readAttack(attackReader.readLine());
                next.addAttack(order[i], newAttack);
            }
            addCharacter(next);
        }
        charReader.close();
        attackReader.close();
    }
    
}
