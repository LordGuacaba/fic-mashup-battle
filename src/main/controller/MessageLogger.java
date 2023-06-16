package src.main.controller;

import src.main.view.UserInterface;
/**
 * A singleton class that logs messages to the {@link UserInterface} currently being used.
 * 
 * @author Will Hoover
 */
public class MessageLogger {
    
    private static MessageLogger instance;
    private UserInterface ui;

    private MessageLogger() {
        ui = null;
    }

    public static MessageLogger getInstance() {
        if (instance == null) {
            instance = new MessageLogger();
        }
        
        return instance;
    }

    public void setUI(UserInterface ui) {
        this.ui = ui;
    }

    public void logMessage(String message) {
        ui.putMessage(message);
    }
}
