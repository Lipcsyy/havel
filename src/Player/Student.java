package Player;

import Item.Item;
import Logger.*;
import Room.*;

public class Student extends Player {

    private enum TransistorState {
        Inactive,
        Paired,
        Deployed
    }

    private TransistorState transistorState;

    public Student(Room startRoom) {
        super(startRoom);
        transistorState = TransistorState.Inactive;
    }

    @Override
    public void ReactToTeacher(Player player) {

        Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");

        for( Item item: items ) {
            if( item.CanSave(player) ){
                //If the item can save the player, use it
                item.Use(player);
                return;
            }
        }
        setIsAlive(false);

        Logger.logExit(this.getClass().getName(), "ReactToTeacher");
    }

    @Override
    public void Interact(Player player) {
        Logger.logEntry(this.getClass().getName(), "Interact", "player");
        Logger.logExit(this.getClass().getName(), "Interact");
    }

    // ------OWN METHODS OF THE STUDENT CLASS TO BE USED BY BUTTONS-------

    // Uses camembert if there is one in the inventory
    public void Camembert(){
        Logger.logEntry(this.getClass().getName(), "Camembert", "");
        for(Item i: this.items){
            i.MakeGas(this.GetRoom());
        }

        Logger.logExit(this.getClass().getName(), "Camembert");
    }

    // Drops the oldest item in the invetory
    public void DropItem() {
        if(!this.items.isEmpty()){
            Item firstItem = this.items.remove(0);
            // Add the removed item to the room
            this.room.AddItem(firstItem);
        }
    }

    public void PairTransistor(){
        for(Item i: this.items){
            if(i.Pair(this)) {
                transistorState = TransistorState.Paired;
                break;
            }
        }
    }

    public void UseTransistor(){
        for(Item i: this.items){
            if(i.Teleport(this)){
                return;
            }
        }
    }

    public void DeployTransistor(){
        for(Item i: this.items){
            if(i.Deploy(this)){
                transistorState = TransistorState.Deployed;
                break;
            }
        }
    }

    public void Transistor(){
        if(transistorState == TransistorState.Inactive)
            PairTransistor();
        if(transistorState == TransistorState.Paired)
            DeployTransistor();
        else UseTransistor();
    }

}
