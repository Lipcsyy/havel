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
                Mask mask = new Mask();
                player.AddItem(mask);
                System.out.println( mask.id );
                break;

            case "Camembert":
                Camembert camembert = new Camembert();
                player.AddItem( camembert);
                System.out.println( camembert.id );
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
                Transistor transistor = new Transistor();
                player.AddItem( transistor);
                System.out.println( transistor.id );
                break;

            case "Rag":
                Rag rag = new Rag();
                player.AddItem( rag );
                System.out.println( rag.id );
                break;

            default:
                System.out.println( "Invalid item type");
        }
    }

}
