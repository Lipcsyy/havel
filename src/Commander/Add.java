package Commander;

import Player.*;
import Room.*;
import Item.*;

import java.util.ArrayList;

public class Add implements ICommand {

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
                    Teacher teacher = new Teacher( null, CommandInterpreter.gameManager );
                    if ( params.length == 2 ) {
                        Room room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );
                        if ( room != null ) teacher.SetRoom( room );
                    }

                    System.out.println( teacher.id );
                    break;

                case "Cleaner":
                    Cleaner cleaner = new Cleaner( null, CommandInterpreter.gameManager );
                    if ( params.length == 2 ) {
                        Room room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );
                        if ( room != null ) cleaner.SetRoom( room );
                    }

                    System.out.println( cleaner.id );
                    break;

            case "Room":
                room = new Room( Integer.parseInt( params[ 1 ] ), new ArrayList< Item >(), new ArrayList< Room >(), CommandInterpreter.gameManager );
                System.out.println( room.id );
                break;

            case "GasRoom":
                GasRoom gasRoom = new GasRoom( Integer.parseInt( params[ 1 ] ), new ArrayList< Item >(), new ArrayList< Room >(), CommandInterpreter.gameManager );
                System.out.println( gasRoom.id );
                break;

            case "MagicRoom":
                MagicRoom magicRoom = new MagicRoom( Integer.parseInt( params[ 1 ] ), new ArrayList< Item >(), new ArrayList< Room >(), CommandInterpreter.gameManager );
                System.out.println( magicRoom.id );
                break;

                default:
            }
        } else {
            System.out.println( "Szar az add paraméterezése");
        }
    }

}
