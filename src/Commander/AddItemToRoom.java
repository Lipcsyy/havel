package Commander;

import Item.*;

public class AddItemToRoom implements ICommand {
    public void execute( String[] params){

        if( params.length != 2){
            System.out.println( "Invalid number of parameters");
            return;
        }

        var room = CommandInterpreter.gameManager.GetRoomById( params[1] );

        if( room == null){
            System.out.println( "Invalid room id");
            return;
        }

        String itemType = params[0];

        switch( itemType ) {
            case "TVSZ":
                room.AddItem( new Tvsz() );
                break;
            case "Beerglass" :
                room.AddItem( new BeerGlass() );
                break;
            case "Camembert" :
                room.AddItem( new Camembert() );
                break;
            case "Mask" :
                room.AddItem( new Mask() );
                break;
            case "FakeItem":
                room.AddItem( new FakeItem() );
                break;
            case "AirFreshener":
                room.AddItem( new AirFreshener() );
                break;
            case "SlideRule":
                room.AddItem( new SlideRule() );
                break;
            case "Rag" :
                room.AddItem( new Rag() );
                break;
            case "Transistor":
                room.AddItem( new Transistor() );
                break;

            default:
                System.out.println( "Invalid item type");
        }

    }

}
