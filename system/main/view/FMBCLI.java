package system.main.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import system.main.controller.FMBController;
import system.main.model.attacks.Attack;
import system.main.model.character.AttackType;
import system.main.model.character.Battle;
import system.main.model.character.Character;
import system.main.model.character.Team;
import system.main.model.searching.AffiliationSearcher;
import system.main.model.searching.NameSearcher;
import system.main.model.searching.Searcher;

/**
 * A {@link UserInterface} for FMB run from the command line.
 * 
 * @author Will Hoover
 */
public class FMBCLI implements UserInterface {

    private static final Map<String, AttackType> TYPE_MAP = new HashMap<>() {{
        put("basic", AttackType.BASIC);
        put("super", AttackType.SUPER);
        put("ultimate", AttackType.ULTIMATE);
    }};

    private static final Map<String, Searcher> SEARCH_MAP = new HashMap<>() {{
        put("name", new NameSearcher());
        put("affiliation", new AffiliationSearcher());
    }};

    private Team lastTeam;
    private List<Character> lastList;
    private FMBController controller;

    public FMBCLI() {
        controller = new FMBController(this);
        lastList = null;
        lastTeam = null;
    }

    @Override
    public void putMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayBattle(Battle battle) {
        System.out.println(battle);
    }

    @Override
    public void displayTeam(Team team) {
        System.out.println(team);
    }

    @Override
    public void displayCharacters(List<Character> characters) {
        int counter = 1;
        for (Character character : characters) {
            System.out.println("[" + counter + "] " + character);
            counter++;
        }
    }

    @Override
    public void displayCharacter(Character character) {
        System.out.println(character);
        for (Attack attack : character.getAttacks()) {
            System.out.println(attack);
        }
    }

    private void runUI() {
        putMessage("Welcome to Fiction-Mashup Battle, a game in which all of your favorite fictional\n"
            + "(and made up) characters go against each other in battle!\n");
        putMessage("Please enter a command:");
        Scanner scanner = new Scanner(System.in);
        boolean active = true;

        while(active) {
            System.out.print(">>");
            String line = scanner.nextLine();
            String[] args = line.split(" ");
            switch (args[0]) {
                case "search":
                    if (args.length != 3) {
                        putMessage("Search command has incorrect number of arguments.");
                    } else {
                        lastList = controller.searchDatabase(SEARCH_MAP.get(args[1]), args[2]);
                    }
                    break;

                case "view":
                    if (args.length != 2) {
                        putMessage("View command has incorrect number of arguments.");
                    } else if (lastList == null) {
                        putMessage("Search for characters first!");
                    } else {
                        try {
                            int index = Integer.parseInt(args[1])-1;
                            controller.viewCharacter(lastList.get(index));
                        } catch (NumberFormatException e) {
                            putMessage("Second argument for \"view\" must be a number");
                        } catch (IndexOutOfBoundsException e) {
                            putMessage("Your result number exceeds the previous results list");
                        }
                    }
                    break;

                case "quit":
                    putMessage("Bye bye now!");
                    active = false;
                    break;

                default:
                    putMessage("Please enter a valid command.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        FMBCLI ui = new FMBCLI();
        ui.runUI();
    }
    
}
