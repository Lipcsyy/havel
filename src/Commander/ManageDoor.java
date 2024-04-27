package Commander;

public class ManageDoor implements Command {
    public void execute( String[] params){

        var room1 = Commander.CommandInterpreter.gameManager.GetRoomById(params[0]);
        var room2 = Commander.CommandInterpreter.gameManager.GetRoomById(params[2]);

        if( room1 == null || room2 == null ){
            System.out.println("Room not found");
            return;
        }

        String mode = params[1];

        switch ( mode ) {
            case "Disappear":
                CommandInterpreter.gameManager.DisconnectRoomsTwoWay( room1, room2 );
                break;
            case "Appear":
                CommandInterpreter.gameManager.ConnectRoomsTwoWay( room1, room2 );
                break;
            default:
                System.out.println("Invalid mode");
        }

    }

}
