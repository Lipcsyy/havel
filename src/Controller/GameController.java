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
import java.awt.*;
import java.awt.event.ActionListener;

import java.util.*;
import java.util.List;
/**
 * This class represents the game controller in the game.
 * It implements the IObserver interface and has specific behaviors such as handling player input and rendering the game.
 */
public class GameController implements IObserver {

    public GameManager gameManager;
    private GamePanel gamePanel;
    private JPanel endGamePanel;

    //-------------------------------------The views to the in game elements--------------------------------------

    HashMap<Room, RoomView> roomViews = new HashMap<Room, RoomView>();

    public HashMap<Player, PlayerView > playerViews = new HashMap<Player, PlayerView>();

    HashMap<Item, ItemView > itemViews = new HashMap<Item, ItemView>();

    public HashMap<Student, PlayerView> studentToViews = new HashMap<Student, PlayerView>();


    //-------------------------------------------------------------------------------------------------------------

    private int currentPlayerIndex = 0; // Track the current player's index
    private int playerMoveCount = 0; // Track the number of player moves
    /**
     * Constructor for the GameController class.
     * It sets the game mode and the game panel.
     * @param gameMode The game mode to be set.
     * @param gamePanel The game panel to be set.
     */
    public GameController(EGameMode gameMode, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = new GameManager(gameMode, this); //Connecting to the model
        this.gamePanel = gamePanel; //Connecting to the view

        for ( Student student : studentToViews.keySet() ) {
            //System.out.println("Adding observer");
            student.GetRoom().AddObserver(this );
        }

        SetEndGamePanel();
    }

    private void SetEndGamePanel() {
        int roomWidth = gamePanel.GetGameConsoles().get(0).getConsoleWidth();
        int roomHeight = gamePanel.GetGameConsoles().get(0).getConsoleHeight();

        endGamePanel = new JPanel();
        endGamePanel.setSize(roomWidth, roomHeight);
        endGamePanel.setBackground(Color.BLACK);
        endGamePanel.setLayout(new GridBagLayout());

        JLabel gameOverLabel = new JLabel("Game Over!");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        endGamePanel.add(gameOverLabel, gbc);
    }
    /**
     * Constructor for the GameController class.
     * It sets the game manager and the game panel.
     * @param gameManager The game manager to be set.
     * @param gamePanel The game panel to be set.
     */
    public GameController(GameManager gameManager, GamePanel gamePanel) {

        // MVC triangle setup
        this.gameManager = gameManager;
        this.gameManager.SetGameController(this);
        this.gamePanel = gamePanel;

        for ( Student student : studentToViews.keySet() ) {
            student.GetRoom().AddObserver(this );
        }
    }

    /**
     * Starts the game.
     */
    public void StartGame() {

        System.out.println("Items size: " + itemViews.size());

        Render();

        Student player1 = studentToViews.keySet().iterator().next();
        HandleInput(player1);
    }

    /**
     * Handles the next turn.
     */
    private void handleNextTurn() {

        ArrayList<Student> students = new ArrayList<>(studentToViews.keySet());

        int numAlive = 0;
        for(int i = 0; i < students.size(); i++){
            Student actStudent = students.get(i);
            if(actStudent.GetIsAlive() == false){
                RoomView rV = roomViews.get(actStudent.GetRoom());
                removeDoorListeners(rV);
                disableButtons(rV);
                rV.add(endGamePanel);
            }
            else numAlive++;
        }

        if (numAlive != 0) {


            Student currentPlayer = students.get(currentPlayerIndex);
            System.out.println("(handleNextTurn): frozenForRounds:" + currentPlayer.GetFrozenForRound());

            HandleInput(currentPlayer);

            if(currentPlayer.GetIsAlive() == false){
                roomViews.get(currentPlayer.GetRoom()).add(endGamePanel);
            }

            //Decreasing the frozen rounds on the player
            currentPlayer.DecreaseFrozenForRound();

            //Decreasing every should-be-decreased-after-round-is-over items
            currentPlayer.DecreaseItemsTurnsLeft();

            //Drop the items that needs to be dropped
            for ( int i = 0; i < currentPlayer.GetInventory().size(); i++ ) {
                if ( currentPlayer.GetInventory().get(i).NeedToThrow() == true ) {
                    currentPlayer.RemoveFromInventory( currentPlayer.GetInventory().get(i) );
                    i--;
                }
            }

            List<Room> rooms = gameManager.getRooms();
            for( Room room : rooms){
                room.DecreaseTurnsLeftForEffect();
            }

            Random random = new Random();
            double needToManageDoors = random.nextDouble(0,1);

            if( needToManageDoors < 0 ){
                boolean manageType;
                if( needToManageDoors < 0.05){
                    manageType = true;
                } else {
                    manageType = false;
                }

                for( Room room : rooms) {
                    Room neighbour = gameManager.map.GetRandomNeighbour( room );
                    room.ManageDoors(neighbour, manageType);
                    this.roomViews.get(room).InitilizeDoors(50, 10);
                    this.roomViews.get(neighbour).InitilizeDoors(50, 10);
                }
            }
        }
    }

    private void disableButtons(RoomView roomView) {
        if (roomView.hasTopDoor) {
            DoorView topDoor = roomView.GetDoor(EDirection.NORTH);
            topDoor.setEnabled(false);
        }
        if (roomView.hasBottomDoor) {
            DoorView bottomDoor = roomView.GetDoor(EDirection.SOUTH);
            bottomDoor.setEnabled(false);
        }
        if (roomView.hasLeftDoor) {
            DoorView leftDoor = roomView.GetDoor(EDirection.WEST);
            leftDoor.setEnabled(false);
        }
        if (roomView.hasRightDoor) {
            DoorView rightDoor = roomView.GetDoor(EDirection.EAST);
            rightDoor.setEnabled(false);
        }
    }
    /**
     * Handles the input for a student.
     * @param student The student for which the input is handled.
     */
    private void HandleInput(Student student) {
        System.out.println("HandleInput for: " + student);

        RoomView currentRoomView = roomViews.get(student.GetRoom());
        Room currentRoom = student.GetRoom();

        //removeDoorListeners(currentRoomView);

        if (currentRoomView.hasTopDoor) {
            DoorView topDoor = currentRoomView.GetDoor(EDirection.NORTH);
            topDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y - 1)));
                this.gamePanel.requestFocusInWindow();
            });
        }

        if (currentRoomView.hasBottomDoor) {
            DoorView bottomDoor = currentRoomView.GetDoor(EDirection.SOUTH);
            bottomDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x, currentRoom.y - 1)));
                this.gamePanel.requestFocusInWindow();
            });
        }
        
        if (currentRoomView.hasLeftDoor) {
            DoorView leftDoor = currentRoomView.GetDoor(EDirection.WEST);
            leftDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x - 1, currentRoom.y)));
                this.gamePanel.requestFocusInWindow();
            });
        }
        if (currentRoomView.hasRightDoor) {
            DoorView rightDoor = currentRoomView.GetDoor(EDirection.EAST);
            rightDoor.addActionListener(e -> {
                MovePlayerToRoom(student, Objects.requireNonNull(findCell(currentRoom.x + 1, currentRoom.y)));
                this.gamePanel.requestFocusInWindow();
            });
        }
    }

    /**
     * Moves the in-game characters.
     */
    private void MoveInGameCharacters() {

        System.out.println("MoveInGameCharacters");

        for (Player player : playerViews.keySet()) {

            //We don't want to move the student only the other characters
            if ( studentToViews.containsKey(player) == false ) {
                player.ChangeRoom(gameManager.map.GetRandomNeighbour(player.GetRoom()));
            }

            if(player.getFrozenForRound() > 0){
                player.DecreaseFrozenRounds();
            }

        }


    }

    /**
     * Removes the door listeners from the given room view.
     *
     * @param roomView The room view to remove the door listeners from.
     */
    private void removeDoorListeners(RoomView roomView) {
        System.out.println("RemoveDoorListeners");
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

    /**
     * Moves the given player to the given room.
     *
     * @param player The player to move.
     * @param newRoom The room to move the player to.
     */
    public void MovePlayerToRoom(Student player, Room newRoom) {

        System.out.println("MovePlayerToRoom");

        int index = studentToViews.keySet().stream().toList().indexOf(player);

        System.out.println("Player moved to room: " + newRoom.GetX() + " " + newRoom.GetY());


        if (player.ChangeRoom(newRoom) == true) {
            removeDoorListeners(roomViews.get(player.GetRoom()));
        }

        //System.out.println("Player moved to room:(changeRoom true): " + newRoom.GetX() + " " + newRoom.GetY());
        //gamePanel.GetGameConsoles().get(index).remove(roomViews.get(player.GetRoom()));


        UpdateInventoryConsole();

        nextTurn(); // Move to the next turn after rendering

    }

    /**
     * Renders the game.
     */
    public void Render() {
        System.out.println("Gamecontroller.Render !!");
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

    /**
     *Updates the inventory console.
     */
    public void UpdateInventoryConsole(){

        //System.out.println("UpdateInventoryConsole");

        for ( Student student : studentToViews.keySet()) {

            int studentIndex = studentToViews.keySet().stream().toList().indexOf(student);

            List<Item> items = student.getItems();

            gamePanel.GetInventoryConsoles().get(studentIndex).removeAll();

            int placeIndex = 0;
            for (Item item : items) {
                gamePanel.GetInventoryConsoles().get(studentIndex).add( itemViews.get(item), 0, placeIndex );
                placeIndex++;
            }
            //adding JPanels only for the alignment of the inventoryConsole
            while(placeIndex < 5){
                gamePanel.GetInventoryConsoles().get(studentIndex).add(new JLabel());
                placeIndex++;
            }

            gamePanel.GetInventoryConsoles().get(studentIndex).Render();
        }

    }


    /**
     * Moves to the next turn.
     */
    private void nextTurn() {
        playerMoveCount++;

        if (playerMoveCount >= studentToViews.size()) {
            playerMoveCount = 0;
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % studentToViews.size();
        MoveInGameCharacters();
        handleNextTurn();
    }

    /**
     * Finds a cell with the given coordinates.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The cell with the given coordinates, or null if no such cell exists.
     */
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

    /**
     * Changes the room view to normal.
     *
     * @param room The room to change the view of.
     */
    public void ChangeRoomViewToNormal( Room room ) {

        RoomView roomView = roomViews.get(room);
        roomView.setBackgroundPicture( ERooms.ROOM );
    }

    /**
     * Changes the room view to gas.
     *
     * @param room The room to change the view of.
     */
    public void ChangeRoomViewToGas( Room room ) {

        RoomView roomView = roomViews.get(room);
        roomView.setBackgroundPicture( ERooms.GASROOM );
    }

    /**
     * Changes the room view to magic.
     *
     * @param room The room to change the view of.
     */

    public void ChangeRoomViewToMagic(Room room){
        RoomView roomView = roomViews.get(room);
        roomView.setBackgroundPicture( ERooms.MAGICROOM );
    }

    /**
     * Gets the room view by room.
     *
     * @param room The room to get the view of.
     * @return The room view of the given room.
     */
    
    public RoomView GetRoomViewByRoom( Room room ){
        return roomViews.get( room );
    }

    /**
     * Gets the game panel.
     *
     * @return The game panel.
     */
    public GamePanel GetGamePanel(){
        return gamePanel;
    }
    
    private void showGameOver(String message) {
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        int result = JOptionPane.showConfirmDialog(null, "Exit game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    /**
     * Shows the win game message.
     */
    public void WinGame(){

        JPanel winPanel = new JPanel();
        winPanel.setBackground( Color.YELLOW );

        JLabel winLabel = new JLabel("You won!");
        winLabel.setOpaque( false );
        winLabel.setForeground( Color.BLACK );
        winLabel.setHorizontalAlignment( SwingConstants.CENTER );
        winLabel.setVerticalAlignment( SwingConstants.CENTER );

        gamePanel.add(winPanel);
    }

}