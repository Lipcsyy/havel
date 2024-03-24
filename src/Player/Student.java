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
            boolean canSave = item.CanSave(player);
            if(canSave){
                Logger.logExit(this.getClass().getName(), "ReactToTeacher");
                return;
            }
        }

        SetIsAlive(false);

        Logger.logExit(this.getClass().getName(), "ReactToTeacher");
    }

    @Override
    public void Interact(Player player) {
        Logger.logEntry(this.getClass().getName(), "Interact", "player");
        Logger.logExit(this.getClass().getName(), "Interact");
    }

    @Override
    public void Freeze( int freezeForRounds) {

        Logger.logEntry(this.getClass().getName(), "Freeze", "5");

        for ( Item item : this.items ) {
             if(item.ReactToGas()) {
                Logger.logExit(this.getClass().getName(), "Freeze" );
                 return;
             }
        }

        this.frozenForRound = freezeForRounds;

        Logger.logExit(this.getClass().getName(), "Freeze");
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

        Logger.logEntry(this.getClass().getName(), "DropItem", "");

        if( !this.items.isEmpty() ){

            Item firstItem = this.items.remove(0);

            // Add the removed item to the room
            this.room.AddItem(firstItem);
        }

        Logger.logExit(this.getClass().getName(), "DropItem");
    }

    public void PairTransistor(){

        Logger.logEntry(this.getClass().getName(), "PairTransistor", "");

        for(Item i: this.items){
            if(i.Pair(this)) {
                transistorState = TransistorState.Paired;
                break;
            }
        }

        Logger.logExit(this.getClass().getName(), "PairTransistor", "");
    }

    public void UseTransistor(){

        Logger.logEntry(this.getClass().getName(), "UseTransistor", "");

        for(Item i: this.items){
            if(i.Teleport(this)){
                return;
            }
        }

        Logger.logExit(this.getClass().getName(), "UseTransistor" );
    }

    public void DeployTransistor(){

        Logger.logEntry(this.getClass().getName(), "DeployTransistor", "");

        for(Item i: this.items){
            if(i.Deploy(this)){
                transistorState = TransistorState.Deployed;
                break;
            }
        }

        Logger.logExit(this.getClass().getName(), "DeployTransistor");
    }

    public void Transistor(){

        Logger.logEntry(this.getClass().getName(), "Transistor", "");

        if(transistorState == TransistorState.Inactive)
            PairTransistor();
        else if(transistorState == TransistorState.Paired)
            DeployTransistor();
        else UseTransistor();

        Logger.logExit(this.getClass().getName(), "Transistor");
    }

}
