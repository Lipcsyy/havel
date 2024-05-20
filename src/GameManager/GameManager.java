package GameManager;
import Controller.GameController;
import Enums.EGameMode;
import Enums.EVersion;
import Logger.Logger;
import Room.*;
import Item.*;
import Enums.ELogger;
import Map.GameMap;

import java.util.*;
import Player.*;
import Views.PlayerView;

public class GameManager implements java.io.Serializable{

    public static ELogger loggerStatus = ELogger.SUPRESS;

    private GameController gameController;

    public GameMap map;
    private final ArrayList<Player> players;

    public GameManager(EGameMode gameMod){
        players = new ArrayList<Player>();

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

    private void InitalizeGame(EGameMode gameMode) {

        map = new GameMap(4,4, this);
        map.generateMaze();
        map.displayMaze();

        if ( gameMode == EGameMode.MULTIPLAYER ) {

            Student student1 = new Student( map.getRandomCell() , this);
            Student student2 = new Student(map.getRandomCell(), this);

            gameController.studentToViews.put(student1, new PlayerView());
            gameController.studentToViews.put(student2, new PlayerView());

            //Add 10 teacher to the game

            for (  int i = 0; i < 10; i++ ) {

                Teacher teacher = new Teacher(map.getRandomCell(), this);
                gameController.SetPlayerViews(teacher);

                while ( teacher.GetRoom() == student1.GetRoom() || teacher.GetRoom() == student2.GetRoom() ) {
                    teacher.SetRoom(map.getRandomCell());
                }
            }

            //Adding three cleaners to the game

            for (int i = 0; i < 3; i++) {
                Cleaner cleaner = new Cleaner(map.getRandomCell(), this);
                gameController.SetPlayerViews(cleaner);
            }

            //Adding the items to the game

            Room slideRuleRoom = map.getRandomCell();

            while ( slideRuleRoom == student1.GetRoom() || slideRuleRoom == student2.GetRoom() ) {
                slideRuleRoom = map.getRandomCell();
            }

            slideRuleRoom.AddItem(new SlideRule());

        }
        else {
            //Add student to the game
            Room playerStartRoom = map.getRandomCell();
            Student student = new Student(playerStartRoom, this);

            gameController.studentToViews.put(student, new PlayerView());

            //add 8 teacher to the game with different starting room
            for( int i = 0; i < 8; i++){
                Room TeacherStartRoom = map.getRandomCell();
                while( TeacherStartRoom == playerStartRoom){
                    TeacherStartRoom = map.getRandomCell();
                }

                Teacher teacher = new Teacher(TeacherStartRoom, this);

                gameController.SetPlayerViews( teacher );
            }

            //add 2 cleaner to the game
            for( int i = 0; i < 2; i++){
                Room CleanerStartRoom = map.getRandomCell();
                Cleaner cleaner = new Cleaner( CleanerStartRoom, this);

                gameController.SetPlayerViews( cleaner );
            }

            //adding items
            SlideRule slideRule = new SlideRule();
            Room SlideRuleRoom = map.getRandomCell();
            while( SlideRuleRoom == playerStartRoom){
                SlideRuleRoom = map.getRandomCell();
            }
            SlideRuleRoom.AddItem(slideRule);
        }

        InitalizeItems( gameMode );
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
            gameController.SetItemViews(mask);

            //Add TVSZ
            Tvsz tvsz = new Tvsz();
            map.getRandomCell().AddItem(tvsz);
            gameController.SetItemViews(tvsz);

            //Add AirFreshener
            AirFreshener airFreshener = new AirFreshener();
            map.getRandomCell().AddItem(airFreshener);
            gameController.SetItemViews(airFreshener);

            //Add BeerGlass
            BeerGlass beerGlass = new BeerGlass();
            map.getRandomCell().AddItem(beerGlass);
            gameController.SetItemViews(beerGlass);

            //Add Camembert
            Camembert camembert = new Camembert();
            map.getRandomCell().AddItem(camembert);
            gameController.SetItemViews(camembert);

            //Add Rag
            Rag rag = new Rag();
            map.getRandomCell().AddItem(rag);
            gameController.SetItemViews(rag);

            //Add Transistor pairs
            Transistor transistor = new Transistor();
            map.getRandomCell().AddItem(transistor);
            gameController.SetItemViews(transistor);

            transistor = new Transistor();
            map.getRandomCell().AddItem(transistor);
            gameController.SetItemViews(transistor);

            //Add FakeItems
            FakeItem fakeMask = new FakeItem();
            fakeMask.setVersion(EVersion.MASK);
            map.getRandomCell().AddItem(fakeMask);
            gameController.SetItemViews(fakeMask);

            FakeItem fakeTvsz = new FakeItem();
            fakeTvsz.setVersion(EVersion.TVSZ);
            map.getRandomCell().AddItem(fakeTvsz);
            gameController.SetItemViews(fakeTvsz);

            FakeItem fakeSlideRule = new FakeItem();
            fakeSlideRule.setVersion(EVersion.SLIDERULE);
            map.getRandomCell().AddItem(fakeSlideRule);
            gameController.SetItemViews(fakeSlideRule);
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

    public void ChangeRoomToNormalInList( Room targetRoom )
    {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ChangeRoomToNormalInList", "targetRoom");
        }

        //Copying the room we want to change -> we make it into a normal room no matter what
        Room newRoom = new Room(targetRoom, this);

        //We need remove the players from the target room and add them to the new room
        Iterator<Player> it = targetRoom.GetPlayers().iterator();
        while (it.hasNext()) {

            Player p = it.next();

            // Remove player from the current room
            it.remove();
            p.SetRoom(newRoom);

            // Move items to the new room
            p.GetInventory().forEach((item) -> {
                item.SetRoom(newRoom);
            });
        }

        //We need to set the newRoom as the neighbour of the target room's (the room we want to delete) neighbours.

        //we get here all the neighbours of the room we want to delete
        var neighboursOfTarget = targetRoom.GetNeighbours();

        //we remove from the rooms neighbour list the room that will be deleted soon
        for (Room r : neighboursOfTarget) {
            r.RemoveNeighbour(targetRoom);
        }

        //There are no references left for the targetRoom, so it should be deleted
        map.getAdjacencyList().remove(targetRoom);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "ChangeRoomToNormalInList");
        }

    }

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

    public void GameLoop(){
        while( isRuning ) {
            //akkor anyád
            for ( Player p : players ) {
                //p.move();
            }

            for ( Player p : players ) {
                p.DecreaseItemsTurnsLeft();
            }
        }
    }

    public void SetGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController GetGameController() {
        return gameController;
    }
}
