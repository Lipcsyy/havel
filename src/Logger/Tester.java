package Logger;
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



    private static void SetupStudentEntersRoomWhereTeacher() {
        room1 = new Room( 5, new ArrayList<Item>(), null);
        student1 = new Student(room1);
        room2 = new Room( 5, new ArrayList<Item>(), null);
        teacher = new Teacher(room2);
    }

    private static void SetupStudentEntersRoomWhereNobody( RoomType enterRoomType ) {
        room1 = new Room( 5, new ArrayList<Item>(), null);
        student1 = new Student(room1);

        switch( enterRoomType ) {
            case Normal:
                room2 = new Room( 5, new ArrayList<Item>(), null);
                break;
            case Gas:
                room2 = new GasRoom( 5, new ArrayList<Item>(), null);
                break;
            case Magic:
                room2 = new MagicRoom( 5, new ArrayList<Item>(), null);
                break;
        }
    }

    //1. Use Tvsz
    /* Megfigyeles
    - nincs feeze hivas
    */
    public static void Test1() {

        //Setting up the rooms and players
        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Tvsz());
        //Testing the interaction between student and teacher
        room2.Enter(student1);

    }

    //2. Use Camambert
    /* Megfigyeles
    - nincs setgas, freeze, reacttogas es cansave fuggveny
    */
    public static void Test2() {

        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Camembert());
        student1.AddItem(new Mask());

        room2.Enter(student1);

    }

    //3. Use BeerGlass
    /* Megfigyeles
    - nincs setgas es freeze fuggveny
    */
    public static void Test3() {

        SetupStudentEntersRoomWhereTeacher();

        student1.AddItem(new BeerGlass());

        room2.Enter(student1);

    }

    //4.1 Change Room, Normal to Normal where there is nobody
    /* Megfigyeles
    - nincs enter sem move
    */
    public static void Test4() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);

    }

    //4.2.1. Student enters gas room where there is nobody and the student doesn't have a mask
    /* Megfigyeles
    - nincs reacttogas es cansave
    */
    public static void Test5() {

        SetupStudentEntersRoomWhereNobody(RoomType.Gas);

        room2.Enter(student1);

    }

    //4.2.2. Student enters gas room where there is nobody and the student has a mask
    /* Megfigyeles
    - nincs reacttogas es cansave
    */
    public static void Test6() {

        SetupStudentEntersRoomWhereNobody(RoomType.Gas);
        student1.AddItem(new Mask());

        room2.Enter(student1);

    }

    //4.3 Student enters a Room where there is another Student
    /* Megfigyeles
    - ez jonak tunik
    */
    public static void Test7() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student2 = new Student(room2);

        room2.Enter(student1);

    }

    //4.4 Student enters a Room where there is a Teacher and the Student doesn't have a TVSZ
    /* Megfigyeles
    - ez jonak tunik
    */
    public static void Test8() {

        SetupStudentEntersRoomWhereTeacher();

        room2.Enter(student1);

    }

    //5. The Student enters a Room where there is a Teacher but the Student has a Rag
    /* Megfigyeles
    - setIsAlive hianyzik
    */
    public static void Test9() {

        SetupStudentEntersRoomWhereTeacher();
        student1.AddItem(new Rag());

        room2.Enter(student1);

    }

    //6. The Student enters a Room where there is no Teacher but the SlideRule is in the Room
    /* Megfigyeles
    - move nincs interact, freeze es use
    */
    public static void Test10() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        room2.AddItem(new SlideRule());

        room2.Enter(student1);

    }

    //7. The Student enters a Room where there is no Teacher. In the Room there are items and the Student has enough space in the inventory
    /* Megfigyeles
    - ez jonak tunik
    */
    public static void Test11() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        room2.AddItem(new Rag());
        room2.AddItem(new Mask());

        room2.Enter(student1);

    }

    //8. The inventory of a Student is not empty and he drops an item
    /* Megfigyeles
    - ez jonak tunik
    */
    public static void Test12() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Rag());
        student1.AddItem(new Mask());

        student1.DropItem();

    }

    //9.1.1 The Student only has one transistor he wants to pair it, but he can't
    /* Megfigyeles
    - pairTransistor es pair sincs
    */
    public static void Test13() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());

        student1.PairTransistor();

    }

    //9.1.2 The Student has two transistors and he pairs them
    /* Megfigyeles
    - !!! NEM FUT LE, KIAKAD A PROGRAM AGYA!!!
    */
    public static void Test14() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());
        student1.AddItem(new Transistor());

        student1.PairTransistor();

    }

    //9.2.1 The Student has two transistors but none of them are placed in the room
    /* Megfigyeles
    - !!! NEM FUT LE, a this.room is null miatt
    */
    public static void Test15() {

        SetupStudentEntersRoomWhereNobody(RoomType.Normal);
        student1.AddItem(new Transistor());
        student1.AddItem(new Transistor());

        student1.UseTransistor();

    }

    //9.2.2 ?? Mi értelme

    //10.1 A Door of the Magic Room disappears
    /* Megfigyeles
    - nincs managedoor es changeroom sem
    */
    public static void Test16() {

        SetupStudentEntersRoomWhereNobody(RoomType.Magic);
        MagicRoom magicRoom = (MagicRoom) room2;
        magicRoom.ManageDoors();

    }

    //10.2 A Door of the Magic Room appears
    /* Megfigyeles
    - nincs managedoor, changeroom es move
    */
    public static void Test17() {

        SetupStudentEntersRoomWhereNobody(RoomType.Magic);
        MagicRoom magicRoom = (MagicRoom) room2;
        magicRoom.ManageDoors();

    }

}
