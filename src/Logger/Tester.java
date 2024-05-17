package Logger;
import GameManager.GameManager;
import Room.*;
import Item.*;
import Player.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tester {

    public static final Map<Integer, Runnable> testMap = new HashMap<>();

    static {
        testMap.put(1, Tester::Test1);
        testMap.put(2, Tester::Test2);
        testMap.put(3, Tester::Test3);
        testMap.put(4, Tester::Test4);
        testMap.put(5, Tester::Test5);
        testMap.put(6, Tester::Test6);
        testMap.put(7, Tester::Test7);
        testMap.put(8, Tester::Test8);
        testMap.put(9, Tester::Test9);
        testMap.put(10, Tester::Test10);
        testMap.put(11, Tester::Test11);
        testMap.put(12, Tester::Test12);
        testMap.put(13, Tester::Test13);
        testMap.put(14, Tester::Test14);
        testMap.put(15, Tester::Test15);
        testMap.put(16, Tester::Test16);
        testMap.put(17, Tester::Test17);
        testMap.put(18, Tester::Test18);
        testMap.put(19, Tester::Test19);
        testMap.put(20, Tester::Test20);
    }

    enum RoomType {
        Normal,
        Gas,
        Magic
    }

    static Room room1;
    static Room room2;
    static Student student1;

    static Student student2;
    static Teacher teacher;

    static GameManager gameManager = new GameManager();

    private static void SetupStudentEntersRoomWhereTeacher() {

        room1 = new Room( 5, gameManager);
        student1 = new Student(room1, gameManager);
        room2 = new Room( 5, gameManager);
        teacher = new Teacher(room2, gameManager);

        gameManager.ConnectRoomsTwoWay(room1, room2);

    }

    private static void SetupStudentEntersRoomWhereNobody( RoomType enterRoomType ) {

        System.out.println("Creating rooms");
        room1 = new Room( 5, gameManager);
        System.out.println("Creating student");
        student1 = new Student(room1, gameManager);

        switch( enterRoomType ) {
            case Normal:
                room2 = new Room( 5, gameManager);
                break;
            case Gas:
                System.out.println("Creating room2");
                room2 = new GasRoom( 5, gameManager);
                break;
            case Magic:
                room2 = new MagicRoom( 5,  gameManager);
                break;
        }

        System.out.println("Making the rooms neighbours");
        gameManager.ConnectRoomsTwoWay(room1, room2);

    }

    //1. Use Tvsz
    public static void Test1() {

        //Setting up the rooms and players
        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Tvsz());

        //Testing the interaction between student and teacher
        student1.ChangeRoom(room2);

    }

    //2. Use Camambert
    public static void Test2() {

        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Camembert());
        student1.AddItem(new Mask());

        student1.ChangeRoom(room2);
    }

    //3. Use BeerGlass
    public static void Test3() {

        SetupStudentEntersRoomWhereTeacher();

        student1.AddItem(new BeerGlass());

        student1.ChangeRoom(room2);

    }

    //4.1 Change Room, Normal to Normal where there is nobody
    public static void Test4() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);

        student1.ChangeRoom(room2);

    }

    //4.2.1. Student enters gas room where there is nobody and the student doesn't have a mask
    public static void Test5() {

        SetupStudentEntersRoomWhereNobody(RoomType.Gas);

        student1.ChangeRoom(room2);

    }

    //4.2.2. Student enters gas room where there is nobody and the student has a mask
    public static void Test6() {

        SetupStudentEntersRoomWhereNobody(RoomType.Gas);
        student1.AddItem(new Mask());

        student1.ChangeRoom(room2);

    }

    //4.3 Student enters a Room where there is another Student
    public static void Test7() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student2 = new Student(room2, gameManager);

        student1.ChangeRoom(room2);

    }

    //4.4 Student enters a Room where there is a Teacher and the Student doesn't have a TVSZ
    public static void Test8() {

        SetupStudentEntersRoomWhereTeacher();

        student1.ChangeRoom(room2);

    }

    //5. The Student enters a Room where there is a Teacher but the Student has a Rag
    public static void Test9() {

        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Rag());

        student1.ChangeRoom(room2);

    }

    //6. The Student enters a Room where there is no Teacher but the SlideRule is in the Room
    public static void Test10() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        room2.AddItem(new SlideRule());

        student1.ChangeRoom(room2);

    }

    //7. The Student enters a Room where there is no Teacher. In the Room there are items and the Student has enough space in the inventory
    public static void Test11() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        room2.AddItem(new Rag());
        room2.AddItem(new Mask());

        student1.ChangeRoom(room2);

    }

    //8. The inventory of a Student is not empty and he drops an item
    public static void Test12() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Rag());
        student1.AddItem(new Mask());

        student1.DropItem();

    }

    //9.1.1 The Student only has one transistor he wants to pair it, but he can't
    public static void Test13() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());

        student1.Transistor();

    }

    //9.1.2 The Student has two transistors and he pairs them
    public static void Test14() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());
        student1.AddItem(new Transistor());

        student1.Transistor();

    }

    //9.2.1 The Student has two transistors but none of them are placed in the room
    /* Megfigyeles

    */
    public static void Test15() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());
        student1.AddItem(new Transistor());

        student1.Transistor();
        student1.Transistor();

    }

    //9.2.2 parosit, lerak es atteleportal
    public static void Test16() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());
        student1.AddItem(new Transistor());

        student1.Transistor();
        student1.Transistor();
        student1.Transistor();

    }

    //10.1 A Door of the Magic Room disappears
    public static void Test17() {

        MagicRoom magicRoom = new MagicRoom(5, gameManager);
        Room room = new Room(5, gameManager);
        room.SetNeighbours(magicRoom);
        Student student = new Student(room, gameManager);
        magicRoom.ManageDoors(room, true);
        student.ChangeRoom(magicRoom);

    }

    //10.2 A Door of the Magic Room appears
    public static void Test18() {

        MagicRoom magicRoom = new MagicRoom(5, gameManager);
        Room room = new Room(5, gameManager);
        Student student = new Student(room, gameManager);
        magicRoom.ManageDoors(room, false);
        student.ChangeRoom(magicRoom);
    }

    public static void Test19() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);

        Cleaner cleaner = new Cleaner(room2, gameManager);

        cleaner.ChangeRoom(room1);

    }

    public static void Test20 () {
        room1 = new Room( 5,  gameManager);
        room2 = new GasRoom( 5, gameManager);
        gameManager.ConnectRoomsTwoWay(room1, room2);

        Player cleaner = new Cleaner(room1, gameManager);
        cleaner.ChangeRoom(room2);

    }

}
