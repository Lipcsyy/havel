package Commander;

import Item.*;
import Player.Player;

public class AddItemToPlayer implements ICommand {
    public void execute( String[] params){

        if( params.length != 2){
            System.out.println( "Invalid number of params");
            return;
        }

        Player player = CommandInterpreter.gameManager.GetPlayerById( params[1] );

        if( player == null){
            System.out.println( "Invalid player id");
            return;
        }

        if( player.HasMoreSpaceInInventory() == false){
            System.out.println( "Not enough space in player's inventory" );
            return;
        }

        switch ( params[0] ){
            case "TVSZ":
                player.AddItem( new Tvsz() );
                break;

            case "Mask":
                player.AddItem( new Mask() );
                break;

            case "Camembert":
                player.AddItem( new Camembert() );
                break;

            case "BeerGlass":
                player.AddItem( new BeerGlass() );
                break;

            case "AirFreshener":
                player.AddItem( new AirFreshener() );
                break;

            case "FakeItem":
                player.AddItem( new FakeItem() );
                break;

            case "SlideRule":
                player.AddItem( new SlideRule() );
                break;

            case "Transistor":
                player.AddItem( new Transistor() );
                break;

            case "Rag":
                player.AddItem( new Rag() );
                break;

            default:
                System.out.println( "Invalid item type");
        }
    }

}
