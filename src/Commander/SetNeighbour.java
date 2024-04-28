package Commander;

public class SetNeighbour implements ICommand {
    public void execute( String[] params){

        if (params.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String room1Id = params[0];
        String room2Id = params[1];

        var room1 = CommandInterpreter.gameManager.GetRoomById( room1Id );
        var room2 = CommandInterpreter.gameManager.GetRoomById( room2Id );

        if( room1 == null || room2 == null){
            System.out.println( "Invalid room id" );
        }

        room1.SetNeighbours(room2);

    }

}
