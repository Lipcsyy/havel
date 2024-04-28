package Commander;

public class ChangeRoom implements ICommand {
    public void execute( String[] params){

        if (params.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String playerId = params[0];
        String roomId = params[1];

        var room = CommandInterpreter.gameManager.GetRoomById( roomId );
        if (room == null) {
            System.out.println("Room not found");
            return;
        }

        var player = CommandInterpreter.gameManager.GetPlayerById( playerId );
        if (player == null) {
            System.out.println("Player not found");
            return;
        }

        player.ChangeRoom( room );

    }



}