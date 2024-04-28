package Player;
import Enums.ELogger;

import GameManager.GameManager;
import Logger.Logger;
import Room.Room;

import java.util.List;
import java.util.Set;

public class Cleaner extends Player {

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public void setIdNumberCopySer(){idNumberCopy = idNumber;}
    public void setIdNumberSer(){idNumber = idNumberCopy;}

    public Cleaner(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager, "Cleaner_" + idNumber++);
    }

    // moving the player into a neighbouring room and de-gasing the room
    //Every player who calls on interract on the cleaner will be moved out of the room
    @Override
    public void Interact(Player player) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Interact", "player");
        }

        if (this.room != player.GetRoom()) {
            Logger.logExit(this.getClass().getName(), "Interact");
            return;
        }

        // we try to move the player into any neighbouring room that has enough space
        // if two cleaners meet, the one who entered the room will move the other out
        // (the incomer reacts first)
        Set<Room> neighbours = this.GetRoom().GetNeighbours();

        for(Room observedRoom : neighbours){
            if( observedRoom.HasMoreSpaceInRoom() ){
                player.ChangeRoom(observedRoom);
                break;
            }
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Interact");
        }
    }

    // empty, the Cleaner doesn't need to react to teacher
    @Override
    public void ReactToTeacher(Player teacher) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");
            Logger.logExit(this.getClass().getName(), "ReactToTeacher");
        }
    }

    // Cleaner can't freeze
    @Override
    public void Freeze( int freezeForRounds ) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Freeze", "3");
            Logger.logExit(this.getClass().getName(), "Freeze");
        }
    }

    @Override
    public void Move(Room room) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "Move", "room" );
        }

        this.room.RemovePlayer(this);
        room.AddPlayer(this);
        this.SetRoom(room);

        room.CleanRoom();

        //When we go into a room we decrease the passages that are left before stickiness
        //This only decreases if the room was cleared before
        this.room.DecreasePassagesBeforeStickiness();

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "Move" );
        }
    };

}
