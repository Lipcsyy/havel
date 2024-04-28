package Commander;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LoadFromFile implements ICommand{
    public void execute(String[] params) {

        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("Test_")) {
                    System.out.println( listOfFiles[ i ].getName() );
                }
            }
        }

        System.out.println("Select a file to read from: ");
        
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner;

        try {
            
            String filePath = scanner.nextLine();
            
            File file = new File("./" + filePath);
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException exception) {
            System.out.println("File not found");
            return;
        }

        while(fileScanner.hasNextLine()){

            String cmd = fileScanner.nextLine();
            String[] cmdParams = cmd.split(" ");


            ICommand command = CommandInterpreter.commands.get(cmdParams[0]);
            command.execute(Arrays.copyOfRange(cmdParams, 1, cmdParams.length));

        }
    }
}
