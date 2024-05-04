package Item;
import Logger.Logger;
import Player.*;
import Enums.ELogger;
import GameManager.GameManager;

import java.nio.channels.GatheringByteChannel;

public class Tvsz extends Item {
    private int abilityNumber;

    public void SetAbility( int _ability){
        abilityNumber = _ability;
    }

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public Tvsz(){
        super( "TVSZ_" + idNumber++);
        abilityNumber = 3;
    }

    @Override
    public boolean NeedToThrow() {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "NeedToThrow", "");
            Logger.logExit(this.getClass().getName(), "NeedToThrow", abilityNumber == 0 ? "true" : "false");
        }
        return abilityNumber == 0;
    }

    @Override
    public void Use(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Use", "player");
        }
        abilityNumber --;
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "Use");
        }
    }

    @Override
    public boolean CanSave(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "CanSave", "player");
        }

        if ( abilityNumber == 0 ) {
            return false;
        }

        this.Use(player);
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "CanSave", "true");
        }
        return true;
    }

    @Override
    public void PrintInfo() {
        System.out.println();
        System.out.println(  "Item: " + id );
        System.out.println( "Durability: " + abilityNumber );
    }

}
