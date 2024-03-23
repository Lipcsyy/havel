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
            if(this.GetHasPair())
                return true;
        }
        return false;
    }

    public Transistor GetPair(){
        return pair;
    }
    public void SetPair(Transistor tr) { this.pair = tr; }
    public boolean GetHasPair(){
        return hasPair;
    }
    public void SetHasPair(boolean b) { this.hasPair = b; }
    public void SetRoom(Room room){
        this.room = room;
    }
    public Room GetRoom(){return room;}

    @Override
    public void Connect(Transistor transistor){
        this.SetPair(transistor);
        this.SetHasPair(true);
        transistor.Connect(this);
    }

    @Override
    public void Teleport(Player player){
        this.Use(player);
    }

    @Override
    public void Use(Player player){

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
    }
}
