package system.model.attacks;

import java.util.LinkedList;
import java.util.List;

public class Attack {
    
    private final String name;
    private final String description;
    private List<AttackAction> actions;

    public Attack(String name, String description) {
        this.name = name;
        this.description = description;
        this.actions = new LinkedList<>();
    }

    public void addAction(AttackAction action) {
        this.actions.add(action);
    }
}
