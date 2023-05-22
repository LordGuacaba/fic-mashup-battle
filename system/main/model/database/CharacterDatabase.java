package system.main.model.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import system.main.model.character.Character;
/**
 * A template for a database of {@link Character}s acessable to the user.
 * 
 * @author Will Hoover
 */
public abstract class CharacterDatabase {
    
    private List<Character> characters;

    public CharacterDatabase() {
        try {
            load();
        } catch (IOException e) {
            characters = new ArrayList<>();
        }
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Character> searchDatabase(String searchString) {
        List<Character> results = new LinkedList<>();
        for (Character chr : characters) {
            if (chr.getName().contains(searchString)) {
                results.add(chr);
            }
        }
        return results;
    }

    protected void addCharacter(Character character) {
        characters.add(character);
    }

    protected abstract void load() throws IOException;

}
