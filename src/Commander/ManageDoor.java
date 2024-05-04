package Commander;

public class ManageDoor implements ICommand {
    public void execute( String[] params){


        if( params.length != 3){
            System.out.println( "Invalid number of parameters" );
            return;
        }

        String magicRoom = params[0].split( "_" )[0];

        if( ! magicRoom.equals( "MagicRoom" ) ){
            System.out.println( "Room isn't MagicRoom");
            return;
        }

        var room1 = Commander.CommandInterpreter.gameManager.GetRoomById(params[0]);
        var room2 = Commander.CommandInterpreter.gameManager.GetRoomById(params[2]);

        if( room1 == null || room2 == null ){
            System.out.println("Room not found");
            return;
        }

        String mode = params[1];

        switch ( mode ) {
            case "Disappear":
                if( ! room1.GetNeighbours().contains( room2 ) ){
                    System.out.println( "The Rooms are not connected" );
                    return;
                }
                CommandInterpreter.gameManager.DisconnectRoomsTwoWay( room1, room2 );
                break;
            case "Appear":
                if( room1.GetNeighbours().contains( room2 ) ){
                    System.out.println( "The Rooms are already connected" );
                    return;
                }
                CommandInterpreter.gameManager.ConnectRoomsTwoWay( room1, room2 );
                break;
            default:
                System.out.println("Invalid mode");
        }

    }

}
