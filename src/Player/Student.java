package Player;
import GameManager.GameManager;
import Item.Item;
import Logger.*;
import Room.*;
import Enums.ELogger;

import java.util.Random;

public class Student extends Player {

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public int getIdNumber(){return idNumber;}
    public void setIdNumber(int idValue){idNumber = idValue;}

    public Student(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager, "Student_" + idNumber++);
    }

    @Override
    public void ReactToTeacher(Player player) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");
        }

        // the student can only defend itself if not frozen
        if( this.frozenForRound == 0){
            for( Item item: items ) {
                boolean canSave = item.CanSave(player);
                if(canSave){
                    if (GameManager.loggerStatus == ELogger.INFO) {
                        Logger.logExit(this.getClass().getName(), "ReactToTeacher");
                    }
                    if (item.NeedToThrow()) {
                        this.RemoveFromInventory(item);
                    }
                    return;
                }
            }
        }

        SetIsAlive(false);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "ReactToTeacher");
        }
    }

    @Override
    public void Interact(Player player) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Interact", "player");
        }

        if ( this.room != player.room ) {
            if (GameManager.loggerStatus == ELogger.INFO) {
                Logger.logExit(this.getClass().getName(), "Interact");
            }
            return;
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Interact");
        }
    }

    @Override
    public boolean Freeze( int freezeForRounds) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Freeze", "5");
        }

        for ( Item item : this.items ) {
             if(item.ReactToGas(this)) {
                 if (GameManager.loggerStatus == ELogger.INFO) {
                     Logger.logExit(this.getClass().getName(), "Freeze" );
                 }

                 if ( item.NeedToThrow() )
                     this.RemoveFromInventory(item);

                 return true;
             }
        }

        this.frozenForRound = freezeForRounds;
        this.DropAllItem();


        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Freeze");
        }
        return false;
    }

    // ------OWN METHODS OF THE STUDENT CLASS TO BE USED BY BUTTONS-------

    // Uses camembert if there is one in the inventory
    public void Camembert(){
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Camembert", "");
        }

        for(Item i: this.items){
            i.MakeGas(this.GetRoom());
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Camembert");
        }
    }

    // Drops the oldest item in the inventory
    @Override
    public void DropItem( Item item) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DropItem", "");
        }
            this.items.remove(item);
            // Add the removed item to the room
            this.room.AddItem(item);

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DropItem");
        }

        gameManager.GetGameController().RemoveActionListenerFromItemView(this, item);
        gameManager.GetGameController().RenderAfterDrop(this);
    }

    public void DropRandomItem() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DropRandomItem", "");
        }

        if( !this.items.isEmpty() ){

            Random rnd = new Random();
            int inventorySize = this.GetInventory().size();
            int indexToRemove = rnd.nextInt(inventorySize);
            Item droppedItem = this.items.remove(indexToRemove);

            // Add the removed item to the room
            this.room.AddItem(droppedItem);
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DropRandomItem");
        }
    }

    public void Transistor(){

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Transistor", "");
        }

        for ( Item item : this.GetInventory() ) {
            //after the first use of the transistor we return, because we don't want to use the second one too. Only one at a time.
            if ( item.UseTransistor( this ) ) {
                if (GameManager.loggerStatus == ELogger.INFO) {
                    Logger.logExit(this.getClass().getName(), "Transistor");
                }
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


        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Transistor");
        }
    }

    @Override
    public void DecreaseItemsTurnsLeft() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DecreaseItemsTurnsLeft", "");
        }

        for(Item i: items){
            i.DecreaseTurnsLeft(this);
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DecreaseItemsTurnsLeft");
        }
    }

    public void DecreaseFrozenForRound() {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "DecreaseFrozenForRound", "");
        }

        if( this.frozenForRound > 0 )
            this.frozenForRound--;

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "DecreaseFrozenForRound");
        }
    }

}
