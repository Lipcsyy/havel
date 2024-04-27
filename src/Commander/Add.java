package Commander;

import Player.*;
import Room.*;
import Item.*;

import java.sql.SQLOutput;

public class Add implements Command {

    public void execute( String[] params){

        if ( params.length > 0) {
            switch ( params[ 0 ] ) {
                case "Student":
                    Student student = new Student( null, CommandInterpreter.gameManager );
                    if ( params.length == 2 ) {
                        Room room = CommandInterpreter.gameManager.GetRoomById( params[ 1 ] );

                        if( room == null ) {
                            System.out.println("Could not find the room");
                            return;
                        }

                        if (!room.HasMoreSpaceInRoom()) {
                            System.out.println("Not enough space in room");
                            return;
                        }

                        student.SetRoom( room );
                        room.AddPlayer(student);

                    }

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
                    if ( params.length == 2 ) {
                        Room room = new Room( Integer.parseInt( params[ 1 ] ), null, null, CommandInterpreter.gameManager );

                        System.out.println( room.id );
                    } else {
                        System.out.println( "Room parameters are BAD" );
                    }
                    break;

                case "GasRoom":
                    if ( params.length == 2 ) {
                        GasRoom gasRoom = new GasRoom( Integer.parseInt( params[ 1 ] ), null, null, CommandInterpreter.gameManager );

                        System.out.println( gasRoom.id );
                    } else {
                        System.out.println( "Room parameters are BAD" );
                    }
                    break;

                case "MagicRoom":
                    if ( params.length == 2 ) {
                        MagicRoom magicRoom = new MagicRoom( Integer.parseInt( params[ 1 ] ), null, null, CommandInterpreter.gameManager );

                        System.out.println( magicRoom.id );
                    } else {
                        System.out.println( "Room parameters are BAD" );
                    }
                    break;

                default:
            }
        } else {
            System.out.println( "Szar az add paraméterezése");
        }
    }

}
