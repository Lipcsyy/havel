package Commander;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LoadFromFile implements ICommand{
    public void execute(String[] params) {

        System.out.println("Reading commands from file...");

        File file = new File(params[0]);

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException exception) {
            System.out.println("File not found");
            return;
        }

        while(scanner.hasNextLine() ){

            String cmd = scanner.nextLine();
            String[] cmdParams = cmd.split(" ");

            ICommand command = CommandInterpreter.commands.get(cmdParams[0]);
            command.execute(Arrays.copyOfRange(cmdParams, 1, cmdParams.length));

        }
    }
}
