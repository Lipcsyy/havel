package Commander;

public class Info implements Command {
    public void execute( String[] params){

        if( params.length != 1){
            return;
        }

        String id = params[0];
        String type = id.split("_")[0];

        switch(type){
            case "Student", "Teacher", "Cleaner":
                var player = CommandInterpreter.gameManager.GetPlayerById( id );
                if( player != null) {
                    player.PrintInfo();
                } else {
                    System.out.println( "Invalid player id");
                }

                break;
            case "Room", "MagicRoom", "GasRoom":
                var room = CommandInterpreter.gameManager.GetRoomById( id );
                if( room != null) {
                    room.PrintInfo();
                } else {
                    System.out.println( "Invalid room id");
                }
                break;
            case "TVSZ", "Mask", "Rag", "BeerGlass", "SlideRule", "FakeItem", "AirFreshener", "Camembert", "Transistor":
                break;
            default:
                System.out.println( "invalid type");

        }

    }

}
