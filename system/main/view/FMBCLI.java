package system.main.view;

/**
 * A {@link UserInterface} for FMB run from the command line.
 * 
 * @author Will Hoover
 */
public class FMBCLI implements UserInterface {

    @Override
    public void putMessage(String message) {
        System.out.println(message);
    }
    
}
