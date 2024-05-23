package GameManager;
import Controller.GameController;
import Enums.*;
import Logger.Logger;
import Room.*;
import Item.*;
import Map.GameMap;

import java.util.*;
import Player.*;
import Views.ItemView;
import Views.PlayerView;

public class GameManager implements java.io.Serializable{

    public static ELogger loggerStatus = ELogger.SUPRESS;

    private GameController gameController;
    public EGameMode gameMode;
    public GameMap map;
    private final ArrayList<Player> players;

    public GameManager(EGameMode gameMode){
        players = new ArrayList<Player>();
        this.gameMode = gameMode;

        Student.ResetCounter();
        Teacher.ResetCounter();
        Cleaner.ResetCounter();

        AirFreshener.ResetCounter();
        BeerGlass.ResetCounter();
        Camembert.ResetCounter();
        FakeItem.ResetCounter();
        Mask.ResetCounter();
        Rag.ResetCounter();
        SlideRule.ResetCounter();
        Transistor.ResetCounter();
        Tvsz.ResetCounter();

        Room.ResetCounter();
        GasRoom.ResetCounter();
        MagicRoom.ResetCounter();
    }

    public GameManager( EGameMode gameMode, GameController gameController ) {

       this.gameController = gameController;

       players = new ArrayList<Player>();
       this.gameMode = gameMode;

       Student.ResetCounter();
       Teacher.ResetCounter();
       Cleaner.ResetCounter();

       AirFreshener.ResetCounter();
       BeerGlass.ResetCounter();
       Camembert.ResetCounter();
       FakeItem.ResetCounter();
       Mask.ResetCounter();
       Rag.ResetCounter();
       SlideRule.ResetCounter();
       Transistor.ResetCounter();
       Tvsz.ResetCounter();

       Room.ResetCounter();
       GasRoom.ResetCounter();
       MagicRoom.ResetCounter();

       InitalizeGame(gameMode);
    }

    private void InitalizeGame(EGameMode gameMode)  {

        map = new GameMap(3, 3, this);
        map.generateMaze();
        map.displayMaze();

        if ( gameMode == EGameMode.MULTIPLAYER ) {

            Student student1 = new Student( map.getRandomCell() , this);
            Student student2 = new Student(map.getRandomCell(), this);

            //Adding the students to the studentToView
            gameController.studentToViews.put(student1, new PlayerView( EPlayers.STUDENT));
            gameController.studentToViews.put(student2, new PlayerView( EPlayers.STUDENT));

            //Adding the students to the playerViews too
            gameController.playerViews.put(student1, new PlayerView( EPlayers.STUDENT));
            gameController.playerViews.put(student2, new PlayerView( EPlayers.STUDENT));

            //Add 10 teacher to the game

            for (  int i = 0; i < 3; i++ ) {

                Teacher teacher = new Teacher(map.getRandomCell(), this);
                gameController.SetPlayerViews(teacher, new PlayerView( EPlayers.TEACHER));

                while ( teacher.GetRoom() == student1.GetRoom() || teacher.GetRoom() == student2.GetRoom() ||  teacher.GetRoom().HasMoreSpaceInRoom() == false) {
                    teacher.SetRoom(map.getRandomCell());
                }
            }

            //Adding three cleaners to the game

            for (int i = 0; i < 1; i++) {
                Cleaner cleaner = new Cleaner(map.getRandomCell(), this);
                if( cleaner.GetRoom().HasMoreSpaceInRoom() == false){
                    cleaner.SetRoom(map.getRandomCell());
                }
                gameController.SetPlayerViews(cleaner, new PlayerView( EPlayers.CLEANER));
            }

            //Adding the items to the game
            /*
            Room slideRuleRoom = map.getRandomCell();

            while ( slideRuleRoom == student1.GetRoom() || slideRuleRoom == student2.GetRoom() ) {
                slideRuleRoom = map.getRandomCell();
            }

            slideRuleRoom.AddItem(new SlideRule());
            */
        }
        else {

            //Add student to the game
            Room playerStartRoom = map.getRandomCell();
            Student student = new Student(playerStartRoom, this);
            gameController.studentToViews.put(student, new PlayerView( EPlayers.STUDENT));

            //TODO:REMOVE THIS

            //add 8 teacher to the game with different starting room
            for( int i = 0; i < 1; i++){
                Room TeacherStartRoom = map.getRandomCell();
                while( TeacherStartRoom == playerStartRoom || TeacherStartRoom.HasMoreSpaceInRoom() == false){
                    TeacherStartRoom = map.getRandomCell();
                }

                Teacher teacher = new Teacher(TeacherStartRoom, this);

                gameController.SetPlayerViews( teacher, new PlayerView(EPlayers.TEACHER) );
            }

            //add 2 cleaner to the game
            for( int i = 0; i < 1; i++){
                Room CleanerStartRoom = map.getRandomCell();
                if( CleanerStartRoom.HasMoreSpaceInRoom() == false){
                    CleanerStartRoom = map.getRandomCell();
                }
                Cleaner cleaner = new Cleaner( CleanerStartRoom, this);

                gameController.SetPlayerViews( cleaner, new PlayerView(EPlayers.CLEANER) );
           }

            for( int i = 0; i < 0; i++) {
                SlideRule slideRule = new SlideRule();
                Room SlideRuleRoom = map.getRandomCell();
                while( SlideRuleRoom == playerStartRoom){
                    SlideRuleRoom = map.getRandomCell();
                }
                SlideRuleRoom.AddItem(slideRule);
            }

        }

        InitalizeItems( gameMode );
    }

    /**
     * Initalize the starting items in the game and adding itt to rooms
     * @param gameMode decides how many items we need from each type
     */
    public void InitalizeItems( EGameMode gameMode ){
        int numberOfItems;
        if( gameMode == EGameMode.MULTIPLAYER){
            numberOfItems = 3;
        } else {
            numberOfItems = 4;
        }

        for( int i = 0; i < numberOfItems; i++){
//            //Add Mask
//            Mask mask = new Mask();
//            map.getRandomCell().AddItem(mask);
//            gameController.SetItemViews(mask, new ItemView(EItems.MASK));
//
//            //Add TVSZ
//            Tvsz tvsz = new Tvsz();
//            map.getRandomCell().AddItem(tvsz);
//            gameController.SetItemViews(tvsz, new ItemView( EItems.TVSZ));
//
//            //Add AirFreshener
//            AirFreshener airFreshener = new AirFreshener();
//            map.getRandomCell().AddItem(airFreshener);
//            gameController.SetItemViews(airFreshener, new ItemView(EItems.AIRFRESHENER));

            //Add BeerGlass
            BeerGlass beerGlass = new BeerGlass();
            map.getRandomCell().AddItem(beerGlass);
            gameController.SetItemViews(beerGlass, new ItemView(EItems.BEERGLASS));

//            //Add Camembert
//            Camembert camembert = new Camembert();
//            map.getRandomCell().AddItem(camembert);
//            gameController.SetItemViews(camembert, new ItemView(EItems.CAMEMBERT));

//            //Add Rag
//            Rag rag = new Rag();
//            map.getRandomCell().AddItem(rag);
//            gameController.SetItemViews(rag, new ItemView(EItems.RAG));

            //Add Transistor pairs
//            Transistor transistor = new Transistor();
//            map.getRandomCell().AddItem(transistor);
//            gameController.SetItemViews(transistor, new ItemView(EItems.TRANSISTOR));
//
//            transistor = new Transistor();
//            map.getRandomCell().AddItem(transistor);
//            gameController.SetItemViews(transistor, new ItemView(EItems.TRANSISTOR));

            //Add FakeItems
            FakeItem fakeMask = new FakeItem();
            fakeMask.setVersion(EVersion.MASK);
            //map.getRandomCell().AddItem(fakeMask);
            //gameController.SetItemViews(fakeMask, new ItemView(EItems.MASK));

            FakeItem fakeTvsz = new FakeItem();
            fakeTvsz.setVersion(EVersion.TVSZ);
            //map.getRandomCell().AddItem(fakeTvsz);
            //gameController.SetItemViews(fakeTvsz, new ItemView(EItems.TVSZ));

            FakeItem fakeSlideRule = new FakeItem();
            fakeSlideRule.setVersion(EVersion.SLIDERULE);
            //map.getRandomCell().AddItem(fakeSlideRule);
            gameController.SetItemViews(fakeSlideRule, new ItemView(EItems.SLIDERULE));
        }
    }

    /**
     * Returns the Rooms in the game in a list
     * @return the rooms in the game
     */
    public ArrayList<Room> getRooms(){
        ArrayList<Room> allRooms = new ArrayList<Room >();
        allRooms.addAll( map.getAdjacencyList().keySet() );
        return allRooms;
    }

    /**
     * Retruns the Player entities from the game
     * @return the entities in the game
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * Returns all items from the game
     * @return the items in the game
     */
    public ArrayList<Item> getItems(){

        ArrayList<Item> allItems = new ArrayList< Item >();
        ArrayList<Room> allRooms = getRooms();
        ArrayList<Item> transistorPairs = new ArrayList<Item>();

        for(Room r: allRooms){
            allItems.addAll( r.GetItems() );
        }
        ArrayList<Player> allPlayers = getPlayers();
        for(Player p: allPlayers){
            allItems.addAll(p.getItems());
        }

        for ( Item item : allItems ) {
            var temp = item.GetPair();
            if ( temp != null ) {
                transistorPairs.add(temp);
            }
        }

        allItems.addAll(transistorPairs);

        return allItems;
    }


    /**
     * Add a room to the game
     * @param room the room we want to attach to the map
     */
    public void AddRoom( Room room ) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "AddRoom", "room");
        }
        map.getAdjacencyList().put(room, new HashSet<Room>());
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "AddRoom");
        }
    }

    /**
     * Add a player to the game
     * @param player the player we want to add to the game
     */
    public void AddPlayer( Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");
        }
        players.add( player );
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "AddPlayer");
        }
    }

    /**
     * Connect two rooms in oneway mode, but only one room get new door
     * @param room1 the first room, who has the door
     * @param room2 the second room, who doesn't have the door
     */
    public void ConnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbours = map.getAdjacencyList().get(room1);
        room1Neighbours.add(room2);
    }

    /**
     * Connect two rooms in twoway mode, and each room get new door
     * @param room1 the first room
     * @param room2 the second room
     */
    public void ConnectRoomsTwoWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.getAdjacencyList().get(room1);
        Set<Room> room2Neighbors = map.getAdjacencyList().get(room2);

        room1Neighbors.add(room2);
        room2Neighbors.add(room1);
    }

    /**
     * Disconnect two rooms in oneway mode, but just one room lost its door
     * @param room1 the first room, who lost its door
     * @param room2 the second room, who doesn't lost his door
     */
    public void DisconnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.getAdjacencyList().get(room1);
        if (room1Neighbors != null) {
            room1Neighbors.remove(room2);
        }
    }

    /**
     * Disconnect two rooms in twoway mode, each room lost its door
     * @param room1 the first room
     * @param room2 the second room
     */
    public void DisconnectRoomsTwoWay(Room room1, Room room2) {
        DisconnectRoomsOneWay(room1, room2);
        DisconnectRoomsOneWay(room2, room1);
    }

    boolean isRuning = true;
    public void EndGame() {
        isRuning = false;
    }

    /**
     * Get a rooms neighbours from the map
     * @param room the room, which neighbours we need
     * @return a list of the neighbour rooms
     */
    public Set<Room> GetNeighbours(Room room) {
        return map.getAdjacencyList().get(room);
    }

    /**
     * Get a room by its ID
     * @param id the rooms ID, which we want to get
     * @return the room, what have this ID
     */
    public Room GetRoomById(String id) {
        for (Room r : map.getAdjacencyList().keySet()) {
            if (r.id.equals(id)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Get a player by its ID
     * @param id the players ID, which we want to get
     * @return the player, what have this ID
     */
    public Player GetPlayerById(String id) {
        for (Room r : map.getAdjacencyList().keySet()) {
            for (Player p : r.GetPlayers()) {
                if (p.id.equals(id)) {
                    return p;
                }
            }
        }
        return null;
    }

    //-----------INFORMATION FUNCTIONS----------------

    /**
     * Printing the important infos from gamemanager
     */
    public void PrintInfo() {

        for ( Room r : map.getAdjacencyList().keySet() ) {
            r.PrintInfo();
        }

        for ( Player p : players ) {
            p.PrintInfo();
        }
    }

    /**
     * Get an item by its ID
     * @param id the items ID, which we want to get
     * @return the item, what have this ID
     */
    public Item GetItemById(String id) {
        for (Item i : getItems()) {
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }


    /**
     * Getter for the gamecontroller attribute
     * @return the gamecontroller
     */
    public GameController GetGameController() {
        return gameController;
    }

    public void SetGameController(GameController gameController){
        this.gameController = gameController;
    }

    public void DecreaseFrozenValues(){
        for(Player player: players){
            if(player.GetFrozenForRound() > 0){
                player.DecreaseFrozenRounds();
            }
        }
    }
}
