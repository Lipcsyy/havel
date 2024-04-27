package Commander;
import Player.*;

public class UseTransistor implements Command {
    public void execute( String[] params){

        String studentId = params[0];

        Player student = CommandInterpreter.gameManager.GetPlayerById( studentId );

        if( student == null ){
            System.out.println("Student not found");
            return;
        }

        student.Transistor();

    }

}
