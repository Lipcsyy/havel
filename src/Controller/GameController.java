package Controller;
import Enums.EGameMode;
import Interfaces.IObserver;
import Room.*;
import GameManager.*;
import Player.*;
import Item.*;
import Views.ItemView;
import Views.PlayerView;
import Views.DoorView;
import Panels.*;
import Views.RoomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GameController implements IObserver {

    public GameManager gameManager;
    private GamePanel gamePanel;

    //This is the room of the student
    private Room displayedRoom;

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView>();

    HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();

    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    public HashMap<Student, PlayerView> studentToViews = new HashMap<Student, PlayerView>();

    private boolean isRunning = false;

    public GameController(EGameMode gameMode, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = new GameManager(gameMode, this);
        this.gamePanel =gamePanel;

        this.isRunning = true;

        System.out.println(studentToViews.size());

        for ( Student student : studentToViews.keySet() ) {
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


       int studentIndex = 0;
       for ( Student studentView : studentToViews.keySet() ) {
           //studentToViews.get(studentView).Render(gamePanel.GetGameConsoles().get(studentIndex));
           System.out.println(studentIndex);
           studentIndex++;
       }

        //rendering the room the student is in
        studentIndex = 0;
        for ( Student student : studentToViews.keySet() ) {
            Room room = student.GetRoom();

            if (roomViews.containsKey(room)) {
                RoomView roomView = roomViews.get(room);

                Set<Room> neighbours = room.GetNeighbours();
                for(Room neighbour : neighbours) {
                    if (neighbour.GetX() == room.GetX() + 1) {
                        roomViews.get(room).hasRightDoor = true;
                    } else if (neighbour.GetX() == room.GetX() - 1) {
                        roomViews.get(room).hasLeftDoor = true;
                    } else if (neighbour.GetY() == room.GetY() + 1) {
                        roomViews.get(room).hasBottomDoor = true;
                    } else if (neighbour.GetY() == room.GetY() - 1) {
                        roomViews.get(room).hasTopDoor = true;
                    }
                }

                //gamePanel.GetGameConsoles().get(studentIndex).add(roomView);
                gamePanel.GetGameConsoles().get(studentIndex).addRoomView(roomView);
                int roomWidth = gamePanel.GetGameConsoles().get(studentIndex).getConsoleWidth();
                int roomHeight = gamePanel.GetGameConsoles().get(studentIndex).getConsoleHeight();

                //for(RoomView rView : roomViews.values())
                //    rView.Initialize(roomWidth, roomHeight);
                roomView.Initialize(roomWidth, roomHeight);
            }

            for (Item item : room.GetItems()) {
                if (itemViews.containsKey(item)) {
                    //itemViews.get(item).Render(gamePanel.GetGameConsoles().get(studentIndex));
                    System.out.println("Rendered an item to: " + studentIndex);
                }
            }
            studentIndex++;

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
        RoomView roomView = new RoomView(false, false, false, false);
        this.roomViews.put(room, roomView);
    }

}
