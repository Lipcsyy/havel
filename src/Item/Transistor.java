package Item;

import Player.*;
import Room.*;
import Logger.Logger;

import java.util.List;

public class Transistor extends Item{

    private Transistor pair;
    private boolean hasPair;
    private Room room;


    @Override
    public boolean Pair(Player player){

        Logger.logEntry(this.getClass().getName(), "Pair", "player");

        for(Item item : player.GetInventory()){

            if( !item.equals(this)){
                item.Connect(this);
            }

            if(this.GetHasPair()) {
                Logger.logExit(this.getClass().getName(), "Pair", "true ");
                return true;
            }
        }

        Logger.logExit(this.getClass().getName(), "Pair", "false");
        return false;
    }

    public Transistor GetPair(){
        Logger.logEntry(this.getClass().getName(), "GetPair", "");
        Logger.logExit(this.getClass().getName(), "GetPair");
        return pair;
    }

    public void SetPair(Transistor tr) {
        Logger.logEntry(this.getClass().getName(), "SetPair", "tr");
        this.pair = tr;
        Logger.logExit(this.getClass().getName(), "SetPair");
    }

    public boolean GetHasPair(){
        Logger.logEntry(this.getClass().getName(), "GetHasPair", "");
        Logger.logExit(this.getClass().getName(), "GetHasPair", hasPair ? "true" : "false");
        return hasPair;
    }

    public void SetHasPair(boolean b) {
        Logger.logEntry(this.getClass().getName(), "SetHasPair", "b");
        Logger.logExit(this.getClass().getName(), "SetHasPair");
        this.hasPair = b;
    }

    public void SetRoom(Room room){
        Logger.logEntry(this.getClass().getName(), "SetRoom", "room");
        this.room = room;
        Logger.logExit(this.getClass().getName(), "SetRoom");
    }

    public Room GetRoom(){
        Logger.logEntry(this.getClass().getName(), "GetRoom", "");
        Logger.logExit(this.getClass().getName(), "GetRoom");
        return room;
    }


    @Override
    public boolean UseTransistor(Player player) {

        Logger.logEntry(this.getClass().getName(), "UseTransistor", "player");

        //We need to check wether we have a pair or not
        if ( this.pair == null ) {

            //If we could pair we pair, if we couldn't, we couldn't
            this.Pair(player);

            Logger.logExit(this.getClass().getName(), "UseTransistor", "true");

            //Wether we could pair or not, we need to return because we can't use the transistor just yet
            return true;
        }

        //If we have a pair we should check if this or the other transistor is deployed.
        if ( this.GetRoom() == null && this.pair.GetRoom() == null )  {
            this.Deploy(player);
            Logger.logExit(this.getClass().getName(), "UseTransistor", "true");
            return true;
        }

        //If we have a pair and one of them is deployed, we can use the transistor
        this.Teleport(player);
        Logger.logExit(this.getClass().getName(), "UseTransistor", "true");
        return true;

    }

    @Override
    public void Connect(Transistor transistor){
        Logger.logEntry(this.getClass().getName(), "Connect", "transistor");
        if(!hasPair) {
            this.SetPair(transistor);
            this.SetHasPair(true);
            transistor.Connect(this);
        }
        Logger.logExit(this.getClass().getName(), "Connect");
    }

    @Override
    public boolean Teleport(Player player){
        Logger.logEntry(this.getClass().getName(), "Teleport", "player");
        this.Use(player);
        Logger.logExit(this.getClass().getName(), "Teleport", "true");
        return true;
    }

    @Override
    public void Use(Player player){

        Logger.logEntry(this.getClass().getName(), "Use", "player");

        // we can only use the transistor if the other room has more space
        if( this.GetPair().GetRoom().HasMoreSpaceInRoom() ){

            // put the transistor at hand to the ground from the player's hand
            this.SetRoom(player.GetRoom());
            player.RemoveFromInventory(this);

            // teleport the player to the room of the pair of the transistor
            player.ChangeRoom(this.GetPair().GetRoom());

            // set the pair's room to null and put it into the players inventory, removing it from the ground
            this.GetPair().SetRoom(null);
            player.CollectItem(this.GetPair());
            player.GetRoom().RemoveItem(this.GetPair());
        }

        Logger.logExit(this.getClass().getName(), "Use");
    }

    @Override
    public boolean Deploy(Player player){

        Logger.logEntry(this.getClass().getName(), "Deploy", "student");

        // put the transistor on the ground from the players inventory
        this.SetRoom(player.GetRoom());
        player.GetRoom().AddItem(this);
        player.RemoveFromInventory(this);

        Logger.logExit(this.getClass().getName(), "Deploy", "true");

        return true;
    }
}