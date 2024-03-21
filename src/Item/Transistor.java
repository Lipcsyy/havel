package Item;

import Player.*;
import Room.*;

import java.util.List;

public class Transistor extends Item{

    private Transistor pair;
    private boolean hasPair;
    private Room room;



    public void Pair(Player player){
        List<Item> playerItems = player.GetInventiory();

        for( Item item : playerItems){
            item.Connect(this);
        }
    }

    public void SetRoom(Room room){
        this.room = room;
    }
    public Room GetRoom(){return room;}

    public boolean GetHasPair(){
        return hasPair;
    }

    public void Connect(Transistor transistor){

        pair = transistor;
        hasPair = true;

        if( !transistor.GetHasPair() ){
            transistor.Connect(this);
        }
    }

    @Override
    public void UseTransistor(Player player){
        //teleport
    }
}
