package Item;

import Player.*;
import Logger.Logger;

/**
 * Represents a SlideRule class which is a specific type of item within the game.
 * This class extends the behavior of an item to include interactions specific to a SlideRule, such as usage and reactions to various events.
 */
public class SlideRule extends Item {

    /**
     * Defines the action taken when the SlideRule is used by a player.
     * The method's implementation should be defined to specify how the SlideRule affects the player or the game state.
     *
     * @param player The Player object using the SlideRule.
     */

    @Override
    public void Use(Player player) {
        Logger.logEntry(this.getClass().getName(), "Use", "player");
        Logger.logExit(this.getClass().getName(), "Use");
    }

    /**
     * Defines the behavior when a player picks up the SlideRule.
     * This method should be implemented to specify the actions taken when the SlideRule is collected by a player,
     * such as adding the item to the player's inventory or triggering a specific game event.
     *
     * @param player The Player object picking up the SlideRule.
     */
    public void PickUpItem(Player player) {
        Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
        Logger.logExit(this.getClass().getName(), "PickUpItem");
    }
}