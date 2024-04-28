package Commander;

import Room.*;
import Player.*;
import Item.*;

import java.io.*;
import java.util.ArrayList;

public class SaveGameState implements ICommand {
    public void execute( String[] params){

        String filename = params[0];
        filename = filename.concat(".ser");

        ArrayList<Room> allRooms = CommandInterpreter.gameManager.getRooms();
        for(Room r: allRooms){
            r.setIdNumberCopySer();
        }
        ArrayList<Player> allPlayers = CommandInterpreter.gameManager.getPlayers();
        for(Player p: allPlayers){
            p.setIdNumberCopySer();
        }
        ArrayList<Item> allItems = CommandInterpreter.gameManager.getItems();
        for(Item i: allItems){
            i.setIdNumberCopySer();
        }

        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(Commander.CommandInterpreter.gameManager);

            out.close();
            file.close();

            System.out.println("Object (gameManager) has been serialized");
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught (in save)");
        }

    }

}
