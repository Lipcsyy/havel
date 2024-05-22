package Commander;

/**
 * This class represents the ChangeRoom command in the game.
 * It implements the ICommand interface and has specific behaviors such as changing the room of a player.
 */

public class ChangeRoom implements ICommand {
    /**
     * Executes the ChangeRoom command.
     * It changes the room of a player based on the parameters.
     * @param params The parameters for the command. The first parameter is the player's ID and the second one is the room's ID.
     */
    public void execute( String[] params){

        if (params.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String playerId = params[0];
        String roomId = params[1];

        var room = CommandInterpreter.gameManager.GetRoomById( roomId );
        if (room == null) {
            System.out.println( "Room not found" );
            return;
        }

        var player = CommandInterpreter.gameManager.GetPlayerById( playerId );
        if (player == null) {
            System.out.println( "Player not found" );
            return;
        }

        if( ! room.HasMoreSpaceInRoom() ){
            System.out.println( "Not enough space in room" );
            return;
        }

        if( ! room.GetNeighbours().contains( player.GetRoom() ) ){
            System.out.println( "Invalid move, the rooms are not connected" );
            return;
        }

        player.ChangeRoom( room );

    }



}
