package Room;

import GameManager.GameManager;
import Item.Item;
import Player.Player;
import Room.*;
import Enums.ELogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Logger.Logger;

public class MagicRoom extends Room {

    static int idNumber = 1;

    public static void ResetCounter(){
        idNumber = 1;
    }

    public MagicRoom(int capacity, GameManager gameManager) {
        super(capacity,gameManager);

        this.id = "MagicRoom_" + idNumber++;
    }

    @Override
    public void ManageDoors(Room room, boolean makeDoorDisappearOrAppear) {

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logEntry(this.getClass().getName(), "ManageDoors", makeDoorDisappearOrAppear ? "true" : "false");
        }

        //Disappear
        if ( makeDoorDisappearOrAppear ) {
            //Remove a random neighbour

            Set<Room> neighboursOfRoom = gameManager.GetNeighbours(this);

            if(neighboursOfRoom.size() == 0)
                return;

            gameManager.DisconnectRoomsOneWay(this, neighboursOfRoom.stream().toList().get(new Random().nextInt(neighboursOfRoom.size())));

        }
        else //Make two rooms neighbours
        {
            this.SetNeighbours(room);
        }

        if (GameManager.loggerStatus == ELogger.INFO) {
            Logger.logExit(this.getClass().getName(), "ManageDoors");
        }

    }

    @Override
    public void CleanRoom( boolean isWashed ) {
        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "CleanRoom", "");
        }

        this.turnsLeftForEffect = 0;


        if ( isWashed ) {
            this.SetRoomNumberOfPassagesBeforeStickiness(5);
        }

        gameManager.GetGameController().ChangeRoomViewToMagic( this );

        //We need to update the view

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "CleanRoom");
        }
    }

    @Override
    public void SetTurnsLeftForEffect(int turnsLeftForEffect) {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        }
        this.turnsLeftForEffect = turnsLeftForEffect;

        gameManager.GetGameController().ChangeRoomViewToGas( this );
        //System.out.println(players.size());

        for ( Player player : this.players ) {
            player.Freeze(3);
        }

        NotifyObservers();

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetTurnsLeftForEffect");
        }
    }

    public void DecreaseTurnsLeftForEffect() {

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logEntry(this.getClass().getName(), "SetTurnsLeftForEffect", String.valueOf(turnsLeftForEffect));
        }
        if( turnsLeftForEffect > 0)
            this.turnsLeftForEffect--;

        if( this.turnsLeftForEffect == 0) {
            gameManager.GetGameController().ChangeRoomViewToMagic( this );
        }

        if (GameManager.loggerStatus == ELogger.INFO ) {
            Logger.logExit(this.getClass().getName(), "SetTurnsLeftForEffect");
        }
    }
}
