package src.main.model.searching;

import java.util.List;

import src.main.model.character.Character;

/**
 * A strategy interface for searching a list of {@link Characters} with a string to match.
 * 
 * @author Will Hoover
 */
public interface Searcher {
    
    /**
     * Searches a list of {@link Character}s based on a search string for matches in a specific field.
     * 
     * @param toSearch The list of {@link Character}s to search.
     * @param searchString The string being matched against.
     * @return A list of {@link Character}s that match the search input.
     */
    List<Character> searchForCharacters(List<Character> toSearch, String searchString);
}
