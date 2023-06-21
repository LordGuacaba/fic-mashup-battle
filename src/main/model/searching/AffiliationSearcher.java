package src.main.model.searching;

import java.util.LinkedList;
import java.util.List;

import src.main.model.character.Character;

/**
 * A {@link Searcher} implementation that searches for characters based on their affiliations.
 * 
 * @author Will Hoover
 */
public class AffiliationSearcher implements Searcher {

    public AffiliationSearcher() {}

    @Override
    public List<Character> searchForCharacters(List<Character> toSearch, String searchString) {
        List<Character> results = new LinkedList<>();
        for (Character character : toSearch) {
            for (String affiliation : character.getAffiliations()) {
                if (affiliation.contains(searchString.toLowerCase())) {
                    results.add(character);
                }
            }
        }
        return results;
    }
    
}
