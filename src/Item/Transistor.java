package Item;

import Player.*;
import Room.*;

import java.util.List;

public class Transistor extends Item{

    private Transistor pair;
    private boolean hasPair;
    private Room room;


    @Override
    public boolean Pair(Player player){
        for(Item item : player.GetInventory()){
            if( !item.equals(this)){
                item.Connect(this);
            }
            if(this.hasPair)
                return true;
        }
        return false;
    }

    public void SetRoom(Room room){
        this.room = room;
    }
    public Room GetRoom(){return room;}

    public boolean GetHasPair(){
        return hasPair;
    }

    @Override
    public void Connect(Transistor transistor){
        pair = transistor;
        hasPair = true;
        transistor.Connect(this);
    }

    @Override
    public void Teleport(Player player){
        this.Use(player);
    }

    @Override
    public void Use(Player player){
        // we can only use the transistor if the other room has more space
        if( this.room.HasMoreSpaceInRoom() ){
            // put the transistor at hand to the ground from the player's hand
            this.SetRoom(player.GetRoom());
            player.RemoveFromInventory(this);
            // teleport the player to the room of the pair of the transistor
            player.ChangeRoom(this.pair.GetRoom());
            // set the pair's room to null and put it into the players inventory, removing it from the ground
            this.pair.SetRoom(null);
            player.CollectItem(this.pair);
            player.GetRoom().RemoveItem(this.pair);
        }
    }
}
