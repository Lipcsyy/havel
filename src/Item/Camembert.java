package Item;

import Logger.Logger;
import Player.Player;
import Room.*;

public class Camembert extends Item{

    @Override
    public void Use(Player player) {

        Logger.logEntry(this.getClass().getName(), "Use", "player");

        this.MakeGas(player.GetRoom());

        Logger.logExit(this.getClass().getName(), "Use");
    }

    public void MakeGas(Room room){
        Logger.logEntry(this.getClass().getName(), "MakeGas", "room");
        room.SetTurnsLeftForEffect(5);
        Logger.logExit(this.getClass().getName(), "MakeGas");
    }

    @Override
    public boolean CanSave(Player player){

        Logger.logEntry(this.getClass().getName(), "CanSave", "player");

        this.Use(player);

        Logger.logExit(this.getClass().getName(), "CanSave", "true");

        return true;
    }
}
