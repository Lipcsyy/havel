package Room;

import GameManager.GameManager;
import Item.Item;
import Logger.Logger;
import Player.Player;
import Enums.ELogger;

import java.util.ArrayList;
import java.util.List;

public class GasRoom extends Room {

    static int idNumber = 1;
    public static void ResetCounter(){
        idNumber = 1;
    }

    public GasRoom(int capacity, GameManager gameManager) {
        super(capacity, gameManager);
        this.id = "GasRoom_" + idNumber++;
    }

    /**
     * This function is called when a player enters the room.
     * @param player The player that enters the room.
     */
    @Override
    public boolean Enter(Player player){

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Enter", "player");
        }

        if ( player.GetFrozenForRound() > 0 ) {
            if (GameManager.loggerStatus == ELogger.INFO ) {
                Logger.logExit(this.getClass().getName(), "Enter");
            }
            return false;
        }

        //Adding the player if there is more space in the room
        if (HasMoreSpaceInRoom()) {
            player.Move(this);
        } else {
            if (GameManager.loggerStatus == ELogger.INFO ) {
                Logger.logExit(this.getClass().getName(), "Enter");
            }
            return false;
        }

        boolean wasSavedFromFreeze = player.Freeze(3);

        //making the players interact with each-other
        for (Player playerInRoom : players) {

            if (playerInRoom == player) {
                continue;
            }

            player.Interact(playerInRoom);
            playerInRoom.Interact(player);
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Enter");
        }

        if ( wasSavedFromFreeze == true ) {
            return true;
        }

        if ( player.GetFrozenForRound() == 0 ) {
            return true;
        }

        return true;
    }

    @Override
    public void CleanRoom(boolean isWashed) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "CleanRoom", "");
        }

        if ( isWashed ) {
            this.passagesBeforeStickiness = 5;
        }

        //TODO: Make the players go out of the room
        this.gameManager.ChangeRoomToNormalInList(this);


        //clean the room, so it can be sticky later
        this.SetRoomNumberOfPassagesBeforeStickiness(5);


        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "CleanRoom");
        }
    }

    @Override
    public void PrintInfo() {

        System.out.println("\n");

        System.out.println("Room id: " + id);
        System.out.println("Room capacity: " + capacity);

        if ( items != null ) {
            System.out.print("Room items: ");
            for (Item item : items) {
                System.out.print(item.id + " ");
            }
            System.out.println();
        }

        if ( players != null ) {
            System.out.print("Room players: ");
            for (Player player : players) {
                System.out.print(player.id  + " ");
            }
            System.out.println();
        }

        var neighbours = gameManager.GetNeighbours(this);

        if ( neighbours != null ) {
            System.out.print("Room neighbours: ");
            for (Room neighbour : neighbours) {
                System.out.print(neighbour.id + " ");
            }
            System.out.println();
        }

        System.out.print("Room passages before stickiness: " + passagesBeforeStickiness);

    }
}
