package Item;

import Logger.Logger;
import Player.Player;
import Enums.ELogger;
import GameManager.GameManager;

public class Mask extends Item{

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public void SetAbility( int _ability){
        abilityNumber = _ability;
    }

    private int abilityNumber;

    public Mask(){
        super( "Mask_" + idNumber++ );
        abilityNumber = 3;
    }

    @Override
    public boolean ReactToGas(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "ReactToGas", "");
        }
        DecreaseTurnsLeft(player);
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "ReactToGas", "true");
        }
        return true;
    }

    @Override
    public void DecreaseTurnsLeft(Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "DecreaseTurnsLeft", "");
        }
        abilityNumber --;
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "DecreaseTurnsLeft", "abilityNumber --");
        }
    }

    @Override
    public boolean NeedToThrow(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow", "abilityNumber == 0");
        }
        return abilityNumber == 0;
    }

    @Override
    public void PrintInfo() {
        System.out.println();
        System.out.println(  "Item: " + id );
        System.out.println( "Durability: " + abilityNumber);
    }
}
