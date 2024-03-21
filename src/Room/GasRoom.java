package Room;

import Item.Item;
import Player.Player;

import java.util.List;

public class GasRoom extends Room {


    public GasRoom(int capacity, List<Item> items, List<Room> neighbours) {
        super(capacity, items, neighbours);
    }

    @Override
    public void Enter(Player player){

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
    }

    @Override
    public void ManageDoors() {

    }
}
