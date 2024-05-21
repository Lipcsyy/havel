package Controller;
import Enums.EDirection;
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
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class GameController implements IObserver {

    public GameManager gameManager;
    private GamePanel gamePanel;

    //-------------------------------------The views to the in game elements--------------------------------------

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView>();

    public HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();

    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    public HashMap<Student, PlayerView> studentToViews = new HashMap<Student, PlayerView>();


    //-------------------------------------------------------------------------------------------------------------

    private boolean isRunning = false;
    private ArrayList<Boolean> isPlayerActive;

    public GameController(EGameMode gameMode, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = new GameManager(gameMode, this); //Connecting to the model
        this.gamePanel = gamePanel; //Connecting to the view

        for ( Student student : studentToViews.keySet() ) {
            System.out.println("Adding observer");
            student.GetRoom().AddObserver(this );
        }
    }

    public GameController(GameManager gameManager, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = gameManager;
        this.gameManager.SetGameController(this);
        this.gamePanel = gamePanel;

        for ( Student student : studentToViews.keySet() ) {
            student.GetRoom().AddObserver(this );
        }
    }

    public void StartGame() {

        this.isRunning = true;
        this.isPlayerActive = new ArrayList<Boolean>();

        Render();

        Student player1 = studentToViews.keySet().iterator().next();
        HandleInput(player1);
    }

    private void HandleInput(Student student) {

        System.out.println("Handling input");

        RoomView currentRoomView = roomViews.get(student.GetRoom());
        Room currentRoom = student.GetRoom();

        //removeDoorListeners(currentRoomView);

        if ( currentRoomView.hasTopDoor ) {
            DoorView topDoor = currentRoomView.GetDoor(EDirection.NORTH);
            topDoor.AddClickListener(e -> MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y - 1))));
        }
        if ( currentRoomView.hasBottomDoor ) {
            DoorView bottomDoor = currentRoomView.GetDoor(EDirection.SOUTH);
            bottomDoor.AddClickListener(e -> MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y + 1))));
        }
        if ( currentRoomView.hasLeftDoor ) {
            DoorView leftDoor = currentRoomView.GetDoor(EDirection.WEST);
            leftDoor.AddClickListener(e -> MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x - 1, currentRoom.y))));
        }
        if ( currentRoomView.hasRightDoor ) {
            DoorView rightDoor = currentRoomView.GetDoor(EDirection.EAST);
            rightDoor.AddClickListener(e -> MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x + 1, currentRoom.y))));
        }
    }

    private void removeDoorListeners(RoomView roomView) {
        if (roomView.hasTopDoor) {
            DoorView topDoor = roomView.GetDoor(EDirection.NORTH);
            for (ActionListener listener : topDoor.getActionListeners()) {
                topDoor.removeActionListener(listener);
            }
        }
        if (roomView.hasBottomDoor) {
            DoorView bottomDoor = roomView.GetDoor(EDirection.SOUTH);
            for (ActionListener listener : bottomDoor.getActionListeners()) {
                bottomDoor.removeActionListener(listener);
            }
        }
        if (roomView.hasLeftDoor) {
            DoorView leftDoor = roomView.GetDoor(EDirection.WEST);
            for (ActionListener listener : leftDoor.getActionListeners()) {
                leftDoor.removeActionListener(listener);
            }
        }
        if (roomView.hasRightDoor) {
            DoorView rightDoor = roomView.GetDoor(EDirection.EAST);
            for (ActionListener listener : rightDoor.getActionListeners()) {
                rightDoor.removeActionListener(listener);
            }
        }
    }

    public void MovePlayerToRoom(Student player, Room newRoom) {

        System.out.println("Moving player to room");

        int index = studentToViews.keySet().stream().toList().indexOf(player);

        gamePanel.GetGameConsoles().get(index).remove(roomViews.get(player.GetRoom()));

        player.ChangeRoom(newRoom);
        Render();
    }

    public void Render() {

        //Rendering the maze display
        gamePanel.Render();

        // clear the content of the gameconsoles
        int studentIndex = 0;
        for ( Student studentView : studentToViews.keySet() ) {
            //studentToViews.get(studentView).Render(gamePanel.GetGameConsoles().get(studentIndex));
            gamePanel.GetGameConsoles().get(studentIndex).removeAll();
            gamePanel.GetInventoryConsoles().get(studentIndex).removeAll();
            studentIndex++;
        }

        //rendering the room the student is in
        studentIndex = 0;
        for ( Student student : studentToViews.keySet()) {

            Room room = student.GetRoom();

            // set the contents of the room
            if (roomViews.containsKey(room)) {

                RoomView roomView = roomViews.get(room);

                Set<Room> neighbours = gameManager.map.getAdjacencyList().get(room);

                //setting whether there are doors to other rooms
                if (neighbours.contains(findCell(room.x, room.y - 1))) { // Top wall
                    roomView.hasTopDoor = true;
                }
                if (neighbours.contains(findCell(room.x, room.y + 1))) { // Bottom wall
                    roomView.hasBottomDoor = true;
                }
                if (neighbours.contains(findCell(room.x - 1, room.y))) { // Left wall
                    roomView.hasLeftDoor = true;
                }
                if (neighbours.contains(findCell(room.x + 1, room.y))) { // Right wall
                    roomView.hasRightDoor = true;
                }


                //Adding the room view to the gamepanel's corresponding gameconsole
                gamePanel.GetGameConsoles().get(studentIndex).AddRoomView(roomView);

                // student and other players in the room
                PlayerView studentView = studentToViews.get(student);

                ArrayList<PlayerView> viewToPlayersInRoom = new ArrayList<>();

                for ( Player player : playerViews.keySet() ) {
                    if (player != student && player.GetRoom() == room) {
                        PlayerView playerView = playerViews.get(player);
                        viewToPlayersInRoom.add(playerView);
                    }
                }

                // items in the room
                ArrayList<ItemView> roomItemViews = new ArrayList<>();
                for (Item item : room.GetItems()) {
                    ItemView itemView = itemViews.get(item);
                    roomItemViews.add(itemView);
                }

                // setting size and rendering
                int roomWidth = gamePanel.GetGameConsoles().get(studentIndex).getConsoleWidth();
                int roomHeight = gamePanel.GetGameConsoles().get(studentIndex).getConsoleHeight();

                roomView.Render( roomWidth, roomHeight, studentView, roomItemViews, viewToPlayersInRoom, student.GetRoom() );

                System.out.println("roomindex: " + room.GetX() + " " + room.GetY() + "\n");
            }

            // set the contents of the inventorypanel
            for(Item item: student.getItems()){
                ItemView itemView = itemViews.get(item);
                gamePanel.GetInventoryConsoles().get(studentIndex).add(itemView);
            }


            studentIndex++;
        }

    }

    private Room findCell(int x, int y) {
        for (Room cell : gameManager.getRooms()) {
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null;
    }


    public HashMap<Player, PlayerView> GetPlayerViews() {
        return playerViews;
    }

    public void SetPlayerViews(Player player, PlayerView playerView) {
        this.playerViews.put(player, playerView);
    }

    public HashMap<Item, ItemView> GetItemViews() {
        return itemViews;
    }

    public void SetItemViews(Item item) {
        this.itemViews.put(item, new ItemView());
    }

    public void SetRoomView(Room room, RoomView roomView) {
        this.roomViews.put(room, roomView);
    }

}