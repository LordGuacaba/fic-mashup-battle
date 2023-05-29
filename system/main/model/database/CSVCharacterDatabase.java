package system.main.model.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import system.main.model.attacks.Attack;
import system.main.model.attacks.DamageAction;
import system.main.model.attacks.HealAction;
import system.main.model.attacks.SplashDamageAction;
import system.main.model.character.AttackType;
import system.main.model.character.Character;

public class CSVCharacterDatabase extends CharacterDatabase {

    private final String characterFile = "data/characters.csv";
    private final String attackFile = "data/attacks.csv";

    public CSVCharacterDatabase() {
        super();
    }

    private Character readCharacter(String line) {
        String[] charFields = line.split(",");
        String[] affiliations = charFields[1].split("\\|");
        int attack = Integer.parseInt(charFields[3]);
        int maxHealth = Integer.parseInt(charFields[4]);
        int speed = Integer.parseInt(charFields[5]);
        return new Character(charFields[0], affiliations, charFields[2], attack, maxHealth, speed);
    }

    private Attack readAttack(String line) {
        String[] attackFields = line.split(",");
        Attack newAttack = new Attack(attackFields[1], attackFields[2], Integer.parseInt(attackFields[3]), Integer.parseInt(attackFields[4]));
        String[] actions = attackFields[5].split("\\|");
        for (String action : actions) {
            String[] actionFields = action.split("-");
            switch (actionFields[0].charAt(0)) {
                case 'S':
                    newAttack.addAction(new SplashDamageAction(Double.parseDouble(actionFields[1]), Double.parseDouble(actionFields[2])));
                    break;
                case 'H':
                    newAttack.addAction(new HealAction(Integer.parseInt(actionFields[1])));
                    break;
                default:
                    newAttack.addAction(new DamageAction(Double.parseDouble(actionFields[1])));
            }
        } 
        return newAttack;
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
            line = charReader.readLine();
        }
        charReader.close();
        attackReader.close();
    }
    
}
