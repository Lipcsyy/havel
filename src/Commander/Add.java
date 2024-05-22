package Commander;

import Player.*;
import Room.*;
import Item.*;

import java.util.ArrayList;

/**
 * This class represents the Add command in the game.
 * It implements the ICommand interface and has specific behaviors such as adding a new player or room.
 */

public class Add implements ICommand {

    /**
     * Executes the Add command.
     * It adds a new player or room based on the parameters.
     * @param params The parameters for the command.
     */
    public void execute( String[] params ) {

        if ( params.length != 2 ) {
            System.out.println( "Invalid number of parameters" );
            return;
        }

        switch ( params[ 0 ] ) {
            case "Student":
                Student student = null;

                Room room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );

                if ( room == null ) {
                    System.out.println( "Could not find the room" );
                    return;
                }

                if ( ! room.HasMoreSpaceInRoom() ) {
                    System.out.println( "Not enough space in room" );
                    return;
                }

                student = new Student( room, CommandInterpreter.gameManager );

                System.out.println( student.id );
                break;

            case "Teacher":
                Teacher teacher = null;

                room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );

                if ( room == null ) {
                    System.out.println( "Could not find the room" );
                    return;
                }

                if ( ! room.HasMoreSpaceInRoom() ) {
                    System.out.println( "Not enough space in room" );
                    return;
                }

                teacher = new Teacher( room, CommandInterpreter.gameManager );

                System.out.println( teacher.id );
                break;

            case "Cleaner":
                Cleaner cleaner = null;

                room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );

                if ( room == null ) {
                    System.out.println( "Could not find the room" );
                    return;
                }

                if ( ! room.HasMoreSpaceInRoom() ) {
                    System.out.println( "Not enough space in room" );
                    return;
                }

                cleaner = new Cleaner( room, CommandInterpreter.gameManager );

                System.out.println( cleaner.id );
                break;

            case "Room":
                room = new Room( Integer.parseInt( params[ 1 ] ), CommandInterpreter.gameManager );
                System.out.println( room.id );
                break;

            case "GasRoom":
                GasRoom gasRoom = new GasRoom( Integer.parseInt( params[ 1 ] ), CommandInterpreter.gameManager );
                System.out.println( gasRoom.id );
                break;

            case "MagicRoom":
                MagicRoom magicRoom = new MagicRoom( Integer.parseInt( params[ 1 ] ), CommandInterpreter.gameManager );
                System.out.println( magicRoom.id );
                break;

            default:
                System.out.println( "Invalid type of Add" );
        }
    }

}
