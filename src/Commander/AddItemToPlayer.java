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
                Tvsz tvsz = new Tvsz();
                player.AddItem( tvsz );
                System.out.println( tvsz.id );
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
                BeerGlass beerGlass = new BeerGlass();
                player.AddItem( beerGlass );
                System.out.println( beerGlass.id );
                break;

            case "AirFreshener":
                AirFreshener airFreshener = new AirFreshener();
                player.AddItem( airFreshener );
                System.out.println( airFreshener.id);
                break;

            case "FakeItem":
                FakeItem fakeItem = new FakeItem();
                player.AddItem( fakeItem );
                System.out.println( fakeItem.id );
                break;

            case "SlideRule":
                SlideRule slideRule = new SlideRule();
                player.AddItem( slideRule );
                System.out.println( slideRule );
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
