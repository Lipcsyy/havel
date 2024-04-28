package Item;

import Player.*;
import Room.*;
import Logger.Logger;
import Enums.ELogger;
import GameManager.GameManager;

public class Transistor extends Item{

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    private Transistor pair;
    private boolean hasPair;
    private Room room;

    public Transistor(){
        super( "Transistor_" + idNumber++ );
        hasPair = false;
        pair = null;
    }
    @Override
    public boolean Pair(Player player){

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Pair", "player");
        }

        for(Item item : player.GetInventory()){

            if( !item.equals(this)){
                item.Connect(this);
            }

            if(this.GetHasPair()) {
                if (GameManager.loggerStatus == ELogger.INFO) {
                    Logger.logExit(this.getClass().getName(), "Pair", "true ");
                }
                return true;
            }
        }

        if ( GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Pair", "false");
        }

        return false;
    }

    public Transistor GetPair(){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "GetPair", "");
            Logger.logExit(this.getClass().getName(), "GetPair");
        }
        return pair;
    }

    public void SetPair(Transistor tr) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetPair", "tr");
        }

        this.pair = tr;

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "SetPair");
        }
    }

    public boolean GetHasPair(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "GetHasPair", "");
            Logger.logExit(this.getClass().getName(), "GetHasPair", hasPair ? "true" : "false");
        }
        return hasPair;
    }

    public void SetHasPair(boolean b) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "SetHasPair", "b");
            Logger.logExit(this.getClass().getName(), "SetHasPair");
        }
        this.hasPair = b;
    }

    @Override
    public void SetRoom(Room room){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "SetRoom", room == null ? "null" : "room");
        }

        this.room = room;

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "SetRoom");
        }
    }

    public Room GetRoom(){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "GetRoom", "");
            Logger.logExit(this.getClass().getName(), "GetRoom", room == null ? "null" : "room");
        }

        return room;
    }


    @Override
    public boolean UseTransistor(Player player) {

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "UseTransistor", "player");
        }

        //We need to check wether we have a pair or not
        if ( this.pair == null ) {

            //If we could pair we pair, if we couldn't, we couldn't
            this.Pair(player);

            if (GameManager.loggerStatus == ELogger.INFO) {
                Logger.logExit(this.getClass().getName(), "UseTransistor", "true");
            }

            //Wether we could pair or not, we need to return because we can't use the transistor just yet
            return true;
        }

        //If we have a pair we should check if this or the other transistor is deployed.
        if ( this.GetRoom() == null && this.pair.GetRoom() == null )  {
            this.Deploy(player);
            if (GameManager.loggerStatus == ELogger.INFO) {
                Logger.logExit(this.getClass().getName(), "UseTransistor", "true");
            }
            return true;
        }

        //If we have a pair and one of them is deployed, we can use the transistor
        this.Teleport(player);
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "UseTransistor", "true");
        }
        return true;

    }

    @Override
    public void Connect(Transistor transistor){

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Connect", "transistor");
        }

        if(!hasPair) {
            this.SetPair(transistor);
            this.SetHasPair(true);
            transistor.Connect(this);
        }

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "Connect");
        }
    }

    @Override
    public boolean Teleport(Player student){
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Teleport", "player");
        }
        return true;
    }

    @Override
    public void Use(Player player){

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Use", "player");
        }

        // we can only use the transistor if the other room has more space
        if( this.GetPair().GetRoom().HasMoreSpaceInRoom() ){

            // put the transistor at hand to the ground from the player's hand
            this.SetRoom(player.GetRoom());
            player.RemoveFromInventory(this);
            player.CollectItem(this.GetPair());

            // teleport the player to the room of the pair of the transistor
            this.GetPair().GetRoom().Enter(player);

            // set the pair's room to null and put it into the players inventory, removing it from the ground
            this.GetPair().SetRoom(null);
        }

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "Use");
        }
    }

    @Override
    public boolean Deploy(Player player){

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "Deploy", "student");
        }

        // put the transistor on the ground from the players inventory
        this.SetRoom(player.GetRoom());
        //player.GetRoom().AddItem(this);
        player.RemoveFromInventory(this);

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "Deploy", "true");
        }

        return true;
    }

    public void PickUpItem(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "PickUpItem", "player");
        }
        if(!this.hasPair){
            player.AddItem(this);
            player.GetRoom().RemoveItem(this);
        }
        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "PickUpItem");
        }
    }

    @Override
    public void PrintInfo() {
        System.out.println("\n");
        System.out.println(  "Item: " + id );
        if( hasPair)
            System.out.println( "Pair: " + pair.id );
        if( room != null)
            System.out.println( "Deployed: " + room.id );
    };
}