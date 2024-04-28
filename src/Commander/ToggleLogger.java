package Commander;
import Enums.ELogger;
import GameManager.*;

public class ToggleLogger implements ICommand {
    @Override
    public void execute( String[] params ) {

        if (GameManager.loggerStatus == ELogger.INFO) {
            System.out.println("Logger is now supressed");
            GameManager.loggerStatus = ELogger.SUPRESS;
            return;
        }

        if (GameManager.loggerStatus == ELogger.SUPRESS) {
            System.out.println("Logger is now in INFO mode");
            GameManager.loggerStatus = ELogger.INFO;
            return;
        }

    }
}
