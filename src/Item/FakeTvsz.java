package Item;
import Logger.Logger;
import Player.*;

public class FakeTvsz extends Item {

    public FakeTvsz(){}

    @Override
    public void Use(Player player) {
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        Logger.logExit(this.getClass().getName(), "Use");
    }

}
