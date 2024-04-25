package Item;
import Logger.Logger;
import Player.Player;

public class AirFreshener extends Item{

    private int abilityNumber;

    public AirFreshener(){abilityNumber = 1;}

    @Override
    public boolean ReactToGas(Player player){
        Logger.logEntry(this.getClass().getName(), "ReactToGas", "");
        abilityNumber--;
        player.GetRoom().CleanRoom();
        Logger.logExit(this.getClass().getName(), "ReactToGas", "true");
        return true;
    }

    @Override
    public boolean NeedToThrow(){
        Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
        Logger.logExit(this.getClass().getName(), "NeedToThrow", "abilityNumber == 0");
        return abilityNumber == 0;
    }
}
