package Player;

import GameManager.GameManager;
import Logger.Logger;
import Room.Room;

import java.util.List;

public class Cleaner extends Player {

    public Cleaner(Room startRoom, GameManager gameManager) {
        super(startRoom, gameManager);
    }

    // moving the player into a neighbouring room and de-gasing the room
    //Every player who calls on interract on the cleaner will be moved out of the room
    @Override
    public void Interact(Player player) {

        Logger.logEntry(this.getClass().getName(), "Interact", "player");

        // we try to move the player into any neighbouring room that has enough space
        // if two cleaners meet, the one who entered the room will move the other out
        // (the incomer reacts first)
        List<Room> neighbours = this.GetRoom().GetNeighbours();

        for(Room observedRoom : neighbours){
            if( observedRoom.HasMoreSpaceInRoom() ){
                player.ChangeRoom(observedRoom);
                break;
            }
        }

        Logger.logExit(this.getClass().getName(), "Interact");
    }

    // empty, the Cleaner doesn't need to react to teacher
    @Override
    public void ReactToTeacher(Player teacher) {
        Logger.logEntry(this.getClass().getName(), "ReactToTeacher", "player");
        Logger.logExit(this.getClass().getName(), "ReactToTeacher");
    }

    // Cleaner can't freeze
    @Override
    public void Freeze( int freezeForRounds ) {
        Logger.logEntry(this.getClass().getName(), "Freeze", "5");
        Logger.logExit(this.getClass().getName(), "Freeze");
    }

    public void Clean(){

        Logger.logEntry(this.getClass().getName(), "Clean", "5");

        this.room.CleanRoom();

        Logger.logExit(this.getClass().getName(), "Clean");
    }
}
