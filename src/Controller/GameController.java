package Controller;
import Enums.EGameMode;
import Interfaces.IObserver;
import Map.GameMap;
import Room.*;
import GameManager.*;
import Player.*;
import Item.*;
import Views.ItemView;
import Views.PlayerView;
import Views.RoomView;
import Panels.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController implements IObserver {

    public GameManager gameManager;
    private GamePanel gamePanel;

    //This is the room of the student
    private Room displayedRoom;

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView >();

    HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();

    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    public HashMap<Student, PlayerView> studentViews = new HashMap<Student, PlayerView>();

    private boolean isRunning = false;

    public GameController(EGameMode gameMode, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = new GameManager(gameMode, this);
        this.gamePanel =gamePanel;

        this.isRunning = true;

        System.out.println(studentViews.size());

        for ( Student student : studentViews.keySet() ) {
            System.out.println("Adding observer");
            student.GetRoom().AddObserver(this );
        }

        System.out.println("Running");

    }


    public void StartGame() {

        Render();

//        while (isRunning) {
//            HandleInput();  // Handle user input for the active room
//
//            //move non-playable character
//            Render();       // Render only the active room
//            try {
//                Thread.sleep(16);  // Approximately 60 FPS
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
    }

    private void HandleInput() {

        System.out.println("Handling input");

        //Gets the user input

        //Updates the model accordingly

        //Moves the student (who we are controlling) to the new room

        //Remove the controller as an observer from the old room

        //Add the controller as an observer to the new room
    }

    public void movePlayerToRoom(Player player, Room newRoom) {

    }

    public void Render() {

        System.out.println("Rendering");

       for ( Student studentView : studentViews.keySet() ) {
            studentViews.get(studentView).Render(gamePanel);
       }

       HashMap<Room,RoomView> studentRoomViews = new HashMap<Room, RoomView>();

       for ( Student studentView : studentViews.keySet() ) {
           roomViews.get(studentView.GetRoom()).Render();
           studentRoomViews.put(  studentView.GetRoom() ,roomViews.get(studentView.GetRoom()));
       }

       for ( Room room : studentRoomViews.keySet() ) {
           studentRoomViews.get(room).Render();
       }
    }

    public HashMap<Player, PlayerView> GetPlayerViews() {
        return playerViews;
    }

    public void SetPlayerViews(Player player) {
        this.playerViews.put(player, new PlayerView());
    }

    public HashMap<Item, ItemView> GetItemViews() {
        return itemViews;
    }

    public void SetItemViews(Item item) {
        this.itemViews.put(item, new ItemView());
    }

    public void SetRoomView(Room room) {
        this.roomViews.put(room, new RoomView());
    }

}
