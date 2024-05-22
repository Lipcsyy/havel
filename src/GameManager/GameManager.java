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

        map = new GameMap(3, 1, this);
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

            for (  int i = 0; i < 4; i++ ) {

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
            Room playerStartRoom = map.GetRoomByIndex(2);
            Student student = new Student(playerStartRoom, this);
            gameController.studentToViews.put(student, new PlayerView( EPlayers.STUDENT));

            //TODO:REMOVE THIS
            Rag rag = new Rag();

            //add 8 teacher to the game with different starting room
//            for( int i = 0; i < 1; i++){
//                Room TeacherStartRoom = map.getRandomCell();
//                while( TeacherStartRoom == playerStartRoom || TeacherStartRoom.HasMoreSpaceInRoom() == false){
//                    TeacherStartRoom = map.getRandomCell();
//                }
//
//                Teacher teacher = new Teacher(TeacherStartRoom, this);
//
//                gameController.SetPlayerViews( teacher, new PlayerView(EPlayers.TEACHER) );
//            }

            //add 2 cleaner to the game
//            for( int i = 0; i < 1; i++){
//                Room CleanerStartRoom = map.getRandomCell();
//                if( CleanerStartRoom.HasMoreSpaceInRoom() == false){
//                    CleanerStartRoom = map.getRandomCell();
//                }
//                Cleaner cleaner = new Cleaner( CleanerStartRoom, this);
//
//                gameController.SetPlayerViews( cleaner, new PlayerView(EPlayers.CLEANER) );
//           }

            //adding items
//            SlideRule slideRule = new SlideRule();
//            Room SlideRuleRoom = map.getRandomCell();
//            while( SlideRuleRoom == playerStartRoom){
//                SlideRuleRoom = map.getRandomCell();
//            }
//            SlideRuleRoom.AddItem(slideRule);


        }

        //InitalizeItems( gameMode );
    }

    public void InitalizeItems( EGameMode gameMode ){
        int numberOfItems;
        if( gameMode == EGameMode.MULTIPLAYER){
            numberOfItems = 3;
        } else {
            numberOfItems = 2;
        }

        for( int i = 0; i < numberOfItems; i++){
            //Add Mask
            Mask mask = new Mask();
            map.getRandomCell().AddItem(mask);
            gameController.SetItemViews(mask, new ItemView(EItems.MASK));

            //Add TVSZ
            Tvsz tvsz = new Tvsz();
            map.getRandomCell().AddItem(tvsz);
            gameController.SetItemViews(tvsz, new ItemView( EItems.TVSZ));

            //Add AirFreshener
            AirFreshener airFreshener = new AirFreshener();
            map.getRandomCell().AddItem(airFreshener);
            gameController.SetItemViews(airFreshener, new ItemView(EItems.AIRFRESHENER));

            //Add BeerGlass
            //BeerGlass beerGlass = new BeerGlass();
            //map.getRandomCell().AddItem(beerGlass);
            //gameController.SetItemViews(beerGlass, new ItemView(EItems.BEERGLASS));

            //Add Camembert
            Camembert camembert = new Camembert();
            map.getRandomCell().AddItem(camembert);
            gameController.SetItemViews(camembert, new ItemView(EItems.CAMEMBERT));

            //Add Rag
            Rag rag = new Rag();
            map.getRandomCell().AddItem(rag);
            gameController.SetItemViews(rag, new ItemView(EItems.RAG));

            //Add Transistor pairs
            Transistor transistor = new Transistor();
            map.getRandomCell().AddItem(transistor);
            gameController.SetItemViews(transistor, new ItemView(EItems.TRANSISTOR));

            transistor = new Transistor();
            map.getRandomCell().AddItem(transistor);
            gameController.SetItemViews(transistor, new ItemView(EItems.TRANSISTOR));

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

    public ArrayList<Room> getRooms(){
        ArrayList<Room> allRooms = new ArrayList<Room >();
        allRooms.addAll( map.getAdjacencyList().keySet() );
        return allRooms;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

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

    /*
    public void ChangeRoomToNormalInList(Room targetRoom) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "ChangeRoomToNormalInList", "targetRoom");
        }

        // Create a new Room with the same properties as the targetRoom
        Room newRoom = new Room(targetRoom, this);

        // Move players from the targetRoom to the newRoom
        Iterator<Player> it = targetRoom.GetPlayers().iterator();
        while (it.hasNext()) {
            Player p = it.next();
            it.remove();
            p.SetRoom(newRoom);

            // Move items to the new room
            p.GetInventory().forEach(item -> item.SetRoom(newRoom));
        }

        // Get the neighbors of the targetRoom
        var neighborsOfTarget = targetRoom.GetNeighbours();

        // Remove the targetRoom from its neighbors' adjacency lists
        for (Room neighbor : neighborsOfTarget) {
            DisconnectRoomsTwoWay(neighbor, targetRoom);
        }

        // Remove the targetRoom from the adjacency list
        map.getAdjacencyList().remove(targetRoom);

        // Add the newRoom to the adjacency list and connect it to the neighbors
        map.getAdjacencyList().put(newRoom, new HashSet<>());
        for (Room neighbor : neighborsOfTarget) {
            ConnectRoomsTwoWay(neighbor, newRoom);
        }

        // Update the game controller with the new room
        gameController.ChangeRoomViewToNormal(targetRoom, newRoom);

        // Refresh the view for the new room
        gameController.GetRoomViewByRoom(newRoom).repaint();

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "ChangeRoomToNormalInList");
        }
    }
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

    public void AddPlayer( Player player){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "AddPlayer", "player");
        }
        players.add( player );
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "AddPlayer");
        }
    }

    public void ConnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbours = map.getAdjacencyList().get(room1);
        room1Neighbours.add(room2);
    }

    public void ConnectRoomsTwoWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.getAdjacencyList().get(room1);
        Set<Room> room2Neighbors = map.getAdjacencyList().get(room2);

        room1Neighbors.add(room2);
        room2Neighbors.add(room1);
    }

    public void DisconnectRoomsOneWay(Room room1, Room room2) {
        Set<Room> room1Neighbors = map.getAdjacencyList().get(room1);
        if (room1Neighbors != null) {
            room1Neighbors.remove(room2);
        }
    }

    public void DisconnectRoomsTwoWay(Room room1, Room room2) {
        DisconnectRoomsOneWay(room1, room2);
        DisconnectRoomsOneWay(room2, room1);
    }

    boolean isRuning = true;
    public void EndGame() {
        isRuning = false;
    }

    public Set<Room> GetNeighbours(Room room) {
        return map.getAdjacencyList().get(room);
    }

    public Room GetRoomById(String id) {
        for (Room r : map.getAdjacencyList().keySet()) {
            if (r.id.equals(id)) {
                return r;
            }
        }
        return null;
    }

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

    public void PrintInfo() {

        for ( Room r : map.getAdjacencyList().keySet() ) {
            r.PrintInfo();
        }

        for ( Player p : players ) {
            p.PrintInfo();
        }
    }

    public Item GetItemById(String id) {
        for (Item i : getItems()) {
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }


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
