package system.main.model.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import system.main.model.character.Character;
import system.main.model.searching.Searcher;
/**
 * A template for a database of {@link Character}s acessable to the user.
 * 
 * @author Will Hoover
 */
public abstract class CharacterDatabase {
    
    private List<Character> characters;

    public CharacterDatabase() {
        characters = new LinkedList<>();
        try {
            load();
        } catch (IOException e) {
            characters = new ArrayList<>();
        }
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Character> searchDatabase(Searcher searcher, String searchString) {
        return searcher.searchForCharacters(characters, searchString);
    }

    /**
     * Adds a {@link Character} to the maintained list of {@link Character}s.
     * 
     * @param character The {@link Character} to be added.
     */
    protected void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Loads {@link Character}s into the active list from persistent storage.
     * 
     * @throws IOException If there's an internal issue with file reading.
     */
    protected abstract void load() throws IOException;

}
