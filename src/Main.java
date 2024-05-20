import Commander.CommandInterpreter;
import Logger.Tester.*;
import Panels.GameFrame;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static Logger.Logger.callDepth;
import static Logger.Tester.testMap;

public class Main {
    public static void main(String[] args) {
//        System.out.println("1. Use TVSZ");
//        System.out.println("2. Use Camembert");
//        System.out.println("3. Use BeerGlass");
//        System.out.println("4. Student moves to an empty room");
//        System.out.println("5. Student moves to a gased room without a mask");
//        System.out.println("6. Student moves to a gased room with a m ask");
//        System.out.println("7. Student moves to a room where there is a student");
//        System.out.println("8. Student moves to a room where there is a teacher," +
//                " and the student doesn't have a TVSZ");
//        System.out.println("9. Student moves to a room where there is a teacher," +
//                " but the student has a rag");
//        System.out.println("10. Student moves to a room where there is a sliderule");
//        System.out.println("11. Student moves to a room where are multiple items, and " +
//                "picks up the first");
//        System.out.println("12. Student drops an item");
//        System.out.println("13. Student tries to pair transistors, but has only one");
//        System.out.println("14. Student pairs its two transistors");
//        System.out.println("15. Student pairs its two transistors, and deployes the first one");
//        System.out.println("16. Student pairs its two transistors," +
//                " deploys the first one and then teleports");
//        System.out.println("17. Door of a magic rooms disappears");
//        System.out.println("18. Door of a magic rooms appears");

        try {
//            System.out.println("Enter the test number to run or enter 0 to write your commands: ");
//            Scanner scanner = new Scanner(System.in);
//            int testNumber = scanner.nextInt();
//
//            while( testNumber != 0 ) {
//                Runnable test = testMap.get(testNumber);
//
//                if (test != null) {
//                    test.run();
//                    callDepth.set(0);
//                } else {
//                    System.out.println("Invalid test number. Please try again.");
//                }
//
//                testNumber = scanner.nextInt();
//            }
            //CommandInterpreter commandInterpreter = new CommandInterpreter();
            //commandInterpreter.Interpreting();

            GameFrame gameFrame = new GameFrame();

        } catch (Exception e) {
        }

    }
}