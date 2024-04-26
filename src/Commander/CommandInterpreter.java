package Commander;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandInterpreter {

    static public Map<String, Command> commands = new HashMap<String, Command>();

    static{
        commands.put( "Add", new Add());
        commands.put( "Load", new Load());
        commands.put( "Save", new Save());
        commands.put( "ChangeRoom", new ChangeRoom());
        commands.put( "UseItem", new UseItem());
        commands.put( "DropItem", new DropItem());
        commands.put( "Transistor", new Transistor());
        commands.put( "AddToRoom", new AddToRoom());
        commands.put( "AddToPlayer", new AddToPlayer());
        commands.put( "SetNeighbour", new SetNeighbour());
        commands.put( "ManageDoor", new ManageDoor());
        commands.put( "Info", new Info());
    }
    
    Scanner scanner = new Scanner( System.in );
    public boolean isRunning;

    public void Interpreting(){

        System.out.Println("Welcome to the tester class!");
        System.out.Println("Commands:");

        isRunning = true;
        while( isRunning ){

            String cmd = scanner.nextLine();
            String[] cmdParams = cmd.split(" ");

            if(cmdParams[0].equals("Exit")){
                isRunning = false;
            }
            else {
                Command command = commands.get(cmdParams[0]);
                command.execute(cmdParams);
            }
        }
    }
}
