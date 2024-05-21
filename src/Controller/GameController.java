package Controller;
import Enums.EDirection;
import Enums.EGameMode;
import Enums.EItems;
import Enums.ERooms;
import Interfaces.IObserver;
import Room.*;
import GameManager.*;
import Player.*;
import Item.*;
import Views.*;
import Panels.*;

import javax.swing.*;
import java.awt.event.ActionListener;

import java.util.*;

public class GameController implements IObserver {

    public GameManager gameManager;
    private GamePanel gamePanel;

    //-------------------------------------The views to the in game elements--------------------------------------

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView>();

    public HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();

    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    public HashMap<Student, PlayerView> studentToViews = new HashMap<Student, PlayerView>();


    //-------------------------------------------------------------------------------------------------------------

    private int currentPlayerIndex = 0; // Track the current player's index
    private int playerMoveCount = 0; // Track the number of player moves

    public GameController(EGameMode gameMode, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = new GameManager(gameMode, this); //Connecting to the model
        this.gamePanel = gamePanel; //Connecting to the view

        for ( Student student : studentToViews.keySet() ) {
            //System.out.println("Adding observer");
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

        System.out.println("Items size: " + itemViews.size());

        Render();

        Student player1 = studentToViews.keySet().iterator().next();
        HandleInput(player1);
    }


    private void handleNextTurn() {

        ArrayList<Student> students = new ArrayList<>(studentToViews.keySet());
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).GetIsAlive() == false){
                students.remove(students.get(i));
                i--;
            }
        }


        if (!students.isEmpty()) {

            Student currentPlayer = students.get(currentPlayerIndex);
            System.out.println("(handleNextTurn):FROZEN MOTHERFUCKING ROUNDSSS:" + currentPlayer.GetFrozenForRound());

            HandleInput(currentPlayer);

            //Decreasing the frozen rounds on the player
            currentPlayer.DecreaseFrozenForRound();

            //Decreasing every should-be-decreased-after-round-is-over items
            currentPlayer.GetInventory().forEach( ( Item item ) -> {
                item.DecreaseTurnsLeft( currentPlayer );
            } );

            MoveInGameCharacters();
        }

    }

    private void HandleInput(Student student) {
        System.out.println("HandleInput for: " + student);

        RoomView currentRoomView = roomViews.get(student.GetRoom());
        Room currentRoom = student.GetRoom();

        removeDoorListeners(currentRoomView);

        if (currentRoomView.hasTopDoor) {
            DoorView topDoor = currentRoomView.GetDoor(EDirection.NORTH);
            topDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y - 1)));
            });
        }
        if (currentRoomView.hasBottomDoor) {
            DoorView bottomDoor = currentRoomView.GetDoor(EDirection.SOUTH);
            bottomDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y + 1)));
            });
        }
        if (currentRoomView.hasLeftDoor) {
            DoorView leftDoor = currentRoomView.GetDoor(EDirection.WEST);
            leftDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x - 1, currentRoom.y)));
            });
        }
        if (currentRoomView.hasRightDoor) {
            DoorView rightDoor = currentRoomView.GetDoor(EDirection.EAST);
            rightDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x + 1, currentRoom.y)));
            });
        }
    }

    private void MoveInGameCharacters() {

        System.out.println("MOVE IN GAME CHARACTERS");

        for (Player player : playerViews.keySet()) {

            //We don't want to move the student only the other characters
            if ( studentToViews.containsKey(player) == false ) {
                player.ChangeRoom(gameManager.map.GetRandomNeighbour(player.GetRoom()));
            }

            player.DecreaseFrozenRounds();

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

        System.out.println("MovePlayerToRoom");

        int index = studentToViews.keySet().stream().toList().indexOf(player);

        System.out.println("Player moved to room: " + newRoom.GetX() + " " + newRoom.GetY());

        if (player.ChangeRoom(newRoom)  == true) {
            System.out.println("Player moved to room:(changeRoom true): " + newRoom.GetX() + " " + newRoom.GetY());
            //gamePanel.GetGameConsoles().get(index).remove(roomViews.get(player.GetRoom()));
        };

        UpdateInventoryConsole();

        nextTurn(); // Move to the next turn after rendering

    }

    public void Render() {
        System.out.println("Render (gamecontroller)");
        gamePanel.Render();

        // clear the content of the game consoles
        int studentIndex = 0;
        for (Student studentView : studentToViews.keySet()) {
            gamePanel.GetGameConsoles().get(studentIndex).removeAll();
            gamePanel.GetInventoryConsoles().get(studentIndex).removeAll();
            studentIndex++;
        }

        // rendering the room the student is in
        studentIndex = 0;
        for (Student student : studentToViews.keySet()) {
            Room room = student.GetRoom();

            if (roomViews.containsKey(room)) {
                RoomView roomView = roomViews.get(room);
                Set<Room> neighbours = gameManager.map.getAdjacencyList().get(room);

                roomView.hasTopDoor = neighbours.contains(findCell(room.x, room.y - 1));
                roomView.hasBottomDoor = neighbours.contains(findCell(room.x, room.y + 1));
                roomView.hasLeftDoor = neighbours.contains(findCell(room.x - 1, room.y));
                roomView.hasRightDoor = neighbours.contains(findCell(room.x + 1, room.y));

                gamePanel.GetGameConsoles().get(studentIndex).AddRoomView(roomView);

                PlayerView studentView = studentToViews.get(student);
                ArrayList<PlayerView> viewToPlayersInRoom = new ArrayList<>();

                for (Player player : playerViews.keySet()) {
                    if (player != student && player.GetRoom() == room) {
                        PlayerView playerView = playerViews.get(player);
                        viewToPlayersInRoom.add(playerView);
                    }
                }

                ArrayList<ItemView> roomItemViews = new ArrayList<>();
                for (Item item : room.GetItems()) {
                    ItemView itemView = itemViews.get(item);
                    roomItemViews.add(itemView);
                }

                int roomWidth = gamePanel.GetGameConsoles().get(studentIndex).getConsoleWidth();
                int roomHeight = gamePanel.GetGameConsoles().get(studentIndex).getConsoleHeight();
                roomView.Render(roomWidth, roomHeight, studentToViews.get(student), roomItemViews, viewToPlayersInRoom, student.GetRoom());

                System.out.println("roomindex: " + room.GetX() + " " + room.GetY());
            }

            UpdateInventoryConsole();
            studentIndex++;
        }
    }

    public void UpdateInventoryConsole(){

        System.out.println("UpdateInventoryConsole");

        for ( Student student : studentToViews.keySet()) {

            int studentIndex = studentToViews.keySet().stream().toList().indexOf(student);

            List<Item> items = student.getItems();

            gamePanel.GetInventoryConsoles().get(studentIndex).removeAll();

            for (Item item : items) {
                gamePanel.GetInventoryConsoles().get(studentIndex).add( itemViews.get(item) );
            }

            gamePanel.GetInventoryConsoles().get(studentIndex).Render();
        }

    }


    private void nextTurn() {
        playerMoveCount++;

        if (playerMoveCount >= studentToViews.size()) {
            playerMoveCount = 0;
            //moveInGameCharacters();
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % studentToViews.size();
        handleNextTurn();
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

    public void SetItemViews(Item item, ItemView itemView) {
        this.itemViews.put(item, itemView);
    }

    public void SetRoomView(Room room, RoomView roomView) {
        this.roomViews.put(room, roomView);
    }

    public void AddActionListenerToItemView( Player player ,Item item ) {
        ItemView itemView = itemViews.get(item);
        itemView.AddClickListener(e -> player.DropItem(item));
    }

    public void RemoveActionListenerFromItemView( Player player ,Item item ) {
        ItemView itemView = itemViews.get(item);
        ActionListener[] listeners = itemView.getActionListeners();
        for (ActionListener listener : listeners) {
            itemView.removeActionListener(listener);
        }
    }

    public void AddActionListenerToItemViewTransistor( Player player ,Item item ) {
        ItemView itemView = itemViews.get(item);
        itemView.AddClickListener(e -> item.UseTransistor(player));
    }

    public void RenderAfterDrop( Player student ){

        RoomView roomView = roomViews.get(student.GetRoom());
        if (roomView != null) {
            // Update the item holder with the current items in the room
            ArrayList<ItemView> roomItemViews = new ArrayList<>();
            for (Item item : student.GetRoom().GetItems()) {
                ItemView itemView = itemViews.get(item);
                if (itemView != null) {
                    roomItemViews.add(itemView);
                }
            }

            roomView.getItemHolder().setItemViews(roomItemViews); // Ensure item views are updated
            roomView.getItemHolder().Render();
        }
        UpdateInventoryConsole();
    }

    public void ChangeRoomView( Room target, Room newRoom ) {
        RoomView roomView = roomViews.get(target);
        roomViews.remove(target);
        roomViews.put(newRoom, new RoomView( ERooms.ROOM, false, false, false, false ));
    }

    public RoomView GetRoomViewByRoom( Room room ){
        return roomViews.get( room );
    }

}