package Room;

import Item.Item;
import Logger.Logger;
import Player.Player;

import java.util.List;

public class GasRoom extends Room {


    public GasRoom(int capacity, List<Item> items, List<Room> neighbours) {
        super(capacity, items, neighbours);
    }

    /**
     * This function is called when a player enters the room.
     * @param player The player that enters the room.
     */
    @Override
    public void Enter(Player player){

        Logger.logEntry(this.getClass().getName(), "Enter", "player");

        //Adding the player if there is more space in the room
        if (HasMoreSpaceInRoom()) {
            AddPlayer(player);
            player.Move(this);
        } else {
            return;
        }

        player.Freeze(3);

        //making the players interact with each-other
        for (Player playerInRoom : players) {

            if (playerInRoom == player) {
                continue;
            }

            player.Interact(playerInRoom);
            playerInRoom.Interact(player);
        }

        Logger.logExit(this.getClass().getName(), "Enter");
    }

}
