package Commander;

import java.sql.SQLOutput;
import java.util.*;

import Controller.GameController;
import Enums.EGameMode;
import GameManager.GameManager;
import Panels.GamePanel;

import javax.swing.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors


public class CommandInterpreter {

    public static GameManager gameManager;
    static public Map<String, ICommand> commands = new HashMap<String, ICommand>();

    static{
        commands.put( "Add", new Add());
        commands.put( "LoadGameState", new LoadGameState());
        commands.put( "SaveGameState", new SaveGameState());
        commands.put( "ChangeRoom", new ChangeRoom());
        commands.put( "DropItem", new DropItem());
        commands.put( "UseTransistor", new UseTransistor());
        commands.put( "AddItemToRoom", new AddItemToRoom());
        commands.put( "AddItemToPlayer", new AddItemToPlayer());
        commands.put( "SetNeighbour", new SetNeighbour());
        commands.put( "ManageDoor", new ManageDoor());
        commands.put( "Info", new Info());
        commands.put( "LoadFromFile", new LoadFromFile());
        commands.put( "ToggleLogger", new ToggleLogger());
    }
    public boolean isRunning;

    public void Interpreting(){

        System.out.println("Welcome to the tester class!");
        System.out.println("Info you want to toggle logger, type 'ToggleLogger'");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("Add <RoomType | PlayerType>");
        System.out.println("LoadGameState <FilePath>");
        System.out.println("ChangeRoom <PlayerId> <RoomId>");
        System.out.println("DropItem <PlayerId>" );
        System.out.println("UseTransistor <PlayerId>");
        System.out.println("AddItemRoom <ItemType> <RoomId>");
        System.out.println("AddItemToPlayer <ItemType> <PlayerId>");
        System.out.println("SetNeighbour <RoomId> <RoomId>");
        System.out.println("ManageDoor <RoomId> <Disappear | Appear> <RoomId>");
        System.out.println("Info <PlayerType | RoomType | ItemType | GameManager>");
        System.out.println("LoadFromFile <FilePath>");

        gameManager = new GameManager( EGameMode.SINGLEPLAYER);
        isRunning = true;
        Scanner scanner = new Scanner( System.in );

        while( isRunning ){

            System.out.println();
            System.out.println("Please give a command:");

            String cmd = scanner.nextLine();
            String[] cmdParams = cmd.split(" ");

            if (cmdParams[0].equals("Exit")) {
                isRunning = false;
                scanner.close();
                return;
            } else {
                try {
                    ICommand command = commands.get(cmdParams[0]);
                    command.execute(Arrays.copyOfRange(cmdParams, 1, cmdParams.length));
                } catch (Exception e) {
                    System.out.println("Invalid command");
                }
            }
        }
    }
}
