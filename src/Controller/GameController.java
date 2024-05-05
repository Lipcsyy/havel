package Controller;
import Interfaces.IObserver;
import Room.*;
import GameManager.*;
import Player.*;
import Item.*;
import Views.ItemView;
import Views.PlayerView;
import Views.RoomView;

import java.util.HashMap;

public class GameController implements IObserver {

    private GameManager gameManager;

    //This is the room of the student
    private Room displayedRoom;

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView >();
    HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();
    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    private boolean isRunning = false;

    public GameController(GameManager gameManager, Room initialRoom) {
        this.gameManager = gameManager;
        this.displayedRoom = initialRoom;
        this.isRunning = true;
        this.displayedRoom.addObserver( this );  // Add GameController as an observer to the active room
    }


    public void StartGame() {
        while (isRunning) {
            HandleInput();  // Handle user input for the active room

            //move non-playable character

            render();       // Render only the active room
            try {
                Thread.sleep(16);  // Approximately 60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void HandleInput() {
        //Gets the user input

        //Updates the model accordingly

        //Moves the student (who we are controlling) to the new room

        //Remove the controller as an observer from the old room

        //Add the controller as an observer to the new room
    }

    public void movePlayerToRoom(Player player, Room newRoom) {

    }

    public void Render() {

        for ( Player p : displayedRoom.GetPlayers() ) {
            //render the player
            playerViews.get(p).render();
        }

        for ( Item item : displayedRoom.getItems() ) {
            //render the item
            itemViews.get(item).render();
        }
    }

}
