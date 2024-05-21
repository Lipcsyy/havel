package Commander;
import Item.Item;

public class DropItem implements ICommand {
    public void execute( String[] params) {

        if ( params.length != 1 ) {
            System.out.println( "Invalid number of parameters");
            return;
        }

        var student = CommandInterpreter.gameManager.GetPlayerById( params[ 0 ] );

        if( student == null){
            System.out.println( "Student not found" );
            return;
        }

        if( student.GetInventory().isEmpty() ){
            System.out.println( "Student has no items" );
            return;
        }

       // student.DropItem();
    }

}
