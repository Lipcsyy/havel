package Commander;

public class SetNeighbour implements Command {
    public void execute( String[] params){

        if (params.length != 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        String room1Id = params[0];
        String room2Id = params[1];

        var room1 = CommandInterpreter.gameManager.GetRoomById( room1Id );
        var room2 = CommandInterpreter.gameManager.GetRoomById( room2Id );

        room1.SetNeighbours(room2);

    }

}
