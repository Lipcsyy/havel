package Player;
import GameManager.GameManager;
import Item.Item;
import Logger.*;
import Room.*;

import java.util.Random;

public class Student extends Player {

    public Student(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager) ;
    }

    @Override
    public void ReactToTeacher(Player player) {

        Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");

        // the student can only defend itself if not frozen
        if( this.frozenForRound == 0){
            for( Item item: items ) {
                if(item.CanSave(player)){
                    Logger.logExit(this.getClass().getName(), "ReactToTeacher");
                    return;
                }
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

    // Drops the oldest item in the inventory
    public void DropItem() {

        Logger.logEntry(this.getClass().getName(), "DropItem", "");

        if( !this.items.isEmpty() ){

            Item firstItem = this.items.remove(0);

            // Add the removed item to the room
            this.room.AddItem(firstItem);
        }

        Logger.logExit(this.getClass().getName(), "DropItem");
    }

    public void DropRandomItem() {
        Logger.logEntry(this.getClass().getName(), "DropRandomItem", "");

        if( !this.items.isEmpty() ){

            Random rnd = new Random();
            int inventorySize = this.GetInventory().size();
            int indexToRemove = rnd.nextInt(inventorySize);
            Item droppedItem = this.items.remove(indexToRemove);

            // Add the removed item to the room
            this.room.AddItem(droppedItem);
        }

        Logger.logExit(this.getClass().getName(), "DropRandomItem");
    }

    public void Transistor(){

        Logger.logEntry(this.getClass().getName(), "Transistor", "");

        for ( Item item : this.GetInventory() ) {
            //after the first use of the transistor we return, because we don't want to use the second one too. Only one at a time.
            if ( item.UseTransistor(this) ) {
                Logger.logExit(this.getClass().getName(), "Transistor");
                return;
            }
        }

        //Honnan tudjuk, hogy párosítva van két tranzisztor?
        // -> Onnan, hogy a transistorban a pair az nem null;

        //Honnan tudjuk, hogy le van rakva az egyik párja a transistornak?
        // -> Onnan, hogy a pair transistor roomja nem null;

        /*
        * Alap állapot: Felveszünk egy tranzisztort.
        * Megnyomjuk a player transistor gombját, nem történik semmi
        *
        * Felveszünk egy másik tranzisztort.
        * Megnyomjuk a player transistor gombját:
        * a) Ha az a transistor, amelyik elso a listaban, nincsen neki parja, akkor vegigmegyunk a listan es megnezzuk, hogy van-e olyan tranzisztor, amelyiknek nincs parja, es ha van van meghivjuk rajta a pair fuggvenyt.
        *   Ekkor ha nincs másik transistor az inventoryban, akkor nem fog tortenni semmi
        * b) Mostmár párosítva vannak a transistorok. Most a következő gombnyomásnál megnézzük, hogy a nálunk lévő transistorok közül van e room beallitva valakinek.
        *   Ha nincs, akkor meghivjuk a deploy fuggvenyt, ami a roomban lévő tranzisztort deployolja.
        * c) Ha idáig eljutottunk, akkor a következő gombnyomásnál meghivjuk a use fuggvenyt, ami teleportálja a playert.
        *
        * */


        Logger.logExit(this.getClass().getName(), "Transistor");
    }

}
