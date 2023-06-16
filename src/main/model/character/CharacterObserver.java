package src.main.model.character;

/**
 * Implemented by a class that must observe a {@link Character}'s state. The 
 * {@link Character} in question will notify its observers after a state change.
 */
public interface CharacterObserver {
    
    /**
     * Notifies the observer of a state change.
     * 
     * @param character The character to be acted on by any necessary notification alterations.
     */
    void notifyObserver(Character character);
}
