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
                Tvsz tvsz = new Tvsz();
                room.AddItem( tvsz );
                System.out.println( tvsz.id );
                break;
            case "Beerglass" :
                BeerGlass beerGlass = new BeerGlass();
                room.AddItem( beerGlass );
                System.out.println( beerGlass.id );
                break;
            case "Camembert" :
                Camembert camembert = new Camembert();
                room.AddItem( camembert );
                System.out.println( camembert.id );
                break;
            case "Mask" :
                Mask mask = new Mask();
                room.AddItem( mask);
                System.out.println( mask.id );
                break;
            case "FakeItem":
                FakeItem fakeItem = new FakeItem();
                room.AddItem( fakeItem);
                System.out.println( fakeItem.id );
                break;
            case "AirFreshener":
                AirFreshener airFreshener = new AirFreshener();
                room.AddItem( airFreshener);
                System.out.println( airFreshener.id );
                break;
            case "SlideRule":
                SlideRule slideRule = new SlideRule();
                room.AddItem( slideRule);
                System.out.println( slideRule.id );
                break;
            case "Rag" :
                Rag rag = new Rag();
                room.AddItem( rag );
                System.out.println( rag.id );
                break;
            case "Transistor":
                Transistor transistor = new Transistor();
                room.AddItem( transistor );
                System.out.println( transistor.id );
                break;

            default:
                System.out.println( "Invalid item type");
        }

    }

}
