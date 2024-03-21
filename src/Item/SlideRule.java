package Item;

import Player.*;

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

    }

    /**
     * Specifies the reaction of the SlideRule to gas exposure.
     * The method should be implemented to define any changes or effects on the SlideRule when exposed to gas.
     *
     * @return Whether the player can be saved from the gas
     */
    @Override
    public boolean ReactToGas() {
        return false;
    }

    /**
     * Determines the reaction of the SlideRule when hit by a teacher.
     * By default, this method returns true, suggesting that the SlideRule always reacts when hit by a teacher.
     * This behavior can be customized as needed for the game's mechanics.
     *
     * @param teacher The Teacher object that hits the SlideRule.
     * @return boolean indicating if the SlideRule reacts to being hit by a teacher.
     */
    @Override
    public boolean ReactToHit(Player player) {
        return  true;
    }

    /**
     * Defines the behavior when a player picks up the SlideRule.
     * This method should be implemented to specify the actions taken when the SlideRule is collected by a player,
     * such as adding the item to the player's inventory or triggering a specific game event.
     *
     * @param player The Player object picking up the SlideRule.
     */
    public void PickUpItem(Player player) {
    }
}