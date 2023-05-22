package system.main.model.searching;

import java.util.LinkedList;
import java.util.List;

import system.main.model.character.Character;

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
