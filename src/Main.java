import Logger.Tester.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static Logger.Tester.testMap;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter the test number to run: ");
        Scanner scanner = new Scanner(System.in);
        int testNumber = scanner.nextInt();
        Runnable test = testMap.get(testNumber);

        if (test != null) {
            test.run();
        } else {
            System.out.println("Invalid test number. Please try again.");
        }

    }
}