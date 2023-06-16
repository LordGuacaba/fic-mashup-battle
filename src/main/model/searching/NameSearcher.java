package src.main.model.searching;

import java.util.LinkedList;
import java.util.List;

import src.main.model.character.Character;

/**
 * A {@link Searcher} implementation that searches for characters based on their name.
 * 
 * @author Will Hoover
 */
public class NameSearcher implements Searcher {

    public NameSearcher() {

    }

    @Override
    public List<Character> searchForCharacters(List<Character> toSearch, String searchString) {
        List<Character> results = new LinkedList<>();
        for (Character character : toSearch) {
            if (character.getName().contains(searchString)) {
                results.add(character);
            }
        }
        return results;
    }
    
}
