package Commander;
import GameManager.GameManager;
import Item.Item;
import Player.Player;
import Room.Room;

import java.io.*;
import java.util.ArrayList;

public class LoadGameState implements ICommand {
    public void execute( String[] params){

        String filename = params[0];
        filename = filename.concat(".ser");

        ArrayList<Room> allRooms = CommandInterpreter.gameManager.getRooms();
        for(Room r: allRooms){
            r.setIdNumberSer();
        }
        ArrayList< Player > allPlayers = CommandInterpreter.gameManager.getPlayers();
        for(Player p: allPlayers){
            p.setIdNumberSer();
        }
        ArrayList< Item > allItems = CommandInterpreter.gameManager.getItems();
        for(Item i: allItems){
            i.setIdNumberSer();
        }

        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Commander.CommandInterpreter.gameManager = (GameManager)in.readObject();

            in.close();
            file.close();

            System.out.println("Object (gameManager) has been deserialized ");
            System.out.println("my gameManager: " + Commander.CommandInterpreter.gameManager);
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught (in load)");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught(in load)");
        }


    }

}
