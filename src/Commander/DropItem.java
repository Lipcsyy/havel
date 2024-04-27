package Commander;
import Item.Item;

public class DropItem implements ICommand {
    public void execute( String[] params) {

        if ( params.length != 1 ) {
            return;
        }

        var student = CommandInterpreter.gameManager.GetPlayerById( params[ 0 ] );

        Item lastItem = student.GetInventory().get( 0 );

        student.DropItem();

        if ( student.GetInventory().stream().anyMatch( item -> item == lastItem ) == false )
            System.out.println( "Drop Failed" );
        else
            System.out.println( "Drop Success" );

    }

}
