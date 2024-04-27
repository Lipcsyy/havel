package Commander;
import GameManager.GameManager;
import Room.Room;

import java.io.*;
import java.util.ArrayList;

public class Load implements Command {
    public void execute( String[] params){

        String filename = params[0];
        //GameManager gameManager = new GameManager();
        ArrayList< Room > allRooms = CommandInterpreter.gameManager.getRooms();
        for(Room r: allRooms){
            r.setIdNumberSer();
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
