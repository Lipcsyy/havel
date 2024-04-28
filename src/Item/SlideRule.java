package Item;

import Player.*;
import Logger.Logger;
import Enums.ELogger;
import GameManager.GameManager;

/**
 * Represents a SlideRule class which is a specific type of item within the game.
 * This class extends the behavior of an item to include interactions specific to a SlideRule, such as usage and reactions to various events.
 */
public class SlideRule extends Item {

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public SlideRule() {
        super( "SlideRule_" + idNumber++ );
    }

    /**
     * Defines the behavior when a player picks up the SlideRule.
     * This method should be implemented to specify the actions taken when the SlideRule is collected by a player,
     * such as adding the item to the player's inventory or triggering a specific game event.
     *
     * @param player The Player object picking up the SlideRule.
     */
    public void PickUpItem(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
            Logger.logExit(this.getClass().getName(), "PickUpItem");
        }
    }
}