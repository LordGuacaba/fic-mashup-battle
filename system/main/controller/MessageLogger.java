package system.main.controller;

import system.main.view.UserInterface;
/**
 * A singleton class that logs messages to the {@link UserInterface} currently being used.
 * 
 * @author Will Hoover
 */
public class MessageLogger {
    
    private static MessageLogger INSTANCE;
    private UserInterface ui;

    private MessageLogger() {
        ui = null;
    }

    public static MessageLogger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageLogger();
        }
        
        return INSTANCE;
    }

    public void setUI(UserInterface ui) {
        this.ui = ui;
    }

    public void logMessage(String message) {
        ui.putMessage(message);
    }
}
