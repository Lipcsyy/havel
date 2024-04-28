package Item;
import Logger.Logger;
import Player.Player;
import Enums.ELogger;
import GameManager.GameManager;

public class AirFreshener extends Item{

    static int idNumber = 1;

    private int abilityNumber;

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public AirFreshener(){

        super( "AirFreshener_" + idNumber++);

        abilityNumber = 1;
    }

    @Override
    public boolean ReactToGas(Player player){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ReactToGas", "");
        }

        abilityNumber--;
        player.GetRoom().CleanRoom();

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "ReactToGas", "true");
        }
        return true;
    }

    @Override
    public boolean NeedToThrow(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow", "abilityNumber == 0");
        }
        return abilityNumber == 0;
    }
}
