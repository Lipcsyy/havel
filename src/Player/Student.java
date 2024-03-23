package Player;

import Item.Item;
import Logger.*;
import Room.*;

public class Student extends Player {

    private boolean hasTransistorPair;
    private boolean hasDeployedTransistors;
    public Student(Room startRoom) {
        super(startRoom);

    }

    @Override
    public void ReactToTeacher(Player player) {

        Logger.logEntry(this.getClass().getName(), "ReactToTeacher");

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
        Logger.logEntry(this.getClass().getName(), "Interact");
        Logger.logExit(this.getClass().getName(), "Interact");
    }

    // ------OWN METHODS OF THE STUDENT CLASS TO BE USED BY BUTTONS-------

    // Uses camembert if there is one in the inventory
    public void Camembert(){
        for(Item i: this.items){
            i.MakeGas(this.GetRoom());
        }
    }

    // Drops the oldest item in the invetory
    public void DropItem() {
        if(!this.items.isEmpty()){
            this.room.AddItem(items.getFirst());
            this.items.remove(items.getFirst());
        }
    }

    public void PairTransistor(){
        for(Item i: this.items){
            if(i.Pair(this)) {
                hasTransistorPair = true;
                break;
            }
        }
    }

    public void UseTransistor(){
        for(Item i: this.items){
            i.Teleport(this);
        }
    }

    public void DeployTransistor(){
        for(Item i: this.items){
            i.Deploy(this);
        }
    }

}
