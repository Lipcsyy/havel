package Commander;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import GameManager.GameManager;
import java.util.Arrays;

public class CommandInterpreter {

    public static GameManager gameManager;
    static public Map<String, Command> commands = new HashMap<String, Command>();

    static{
        commands.put( "Add", new Add());
        commands.put( "Load", new Load());
        commands.put( "Save", new Save());
        commands.put( "ChangeRoom", new ChangeRoom());
        commands.put( "DropItem", new DropItem());
        commands.put( "UseTransistor", new UseTransistor());
        commands.put( "AddToRoom", new AddItemToRoom());
        commands.put( "AddToPlayer", new AddItemToPlayer());
        commands.put( "SetNeighbour", new SetNeighbour());
        commands.put( "ManageDoor", new ManageDoor());
        commands.put( "Info", new Info());
    }
    public boolean isRunning;

    public void Interpreting(){

        System.out.println("Welcome to the tester class!");
        System.out.println("Commands:");

        isRunning = true;
        Scanner scanner = new Scanner( System.in );

        while( isRunning ){

            String cmd = scanner.nextLine();
            String[] cmdParams = cmd.split(" ");

            if(cmdParams[0].equals("Exit")){
                isRunning = false;
            }
            else {
                Command command = commands.get(cmdParams[0]);
                command.execute(Arrays.copyOfRange(cmdParams, 1, cmdParams.length));
            }
        }
    }
}
