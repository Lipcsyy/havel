package Logger;
import Room.*;
import Item.*;
import Player.*;

import java.util.ArrayList;
import java.util.List;

enum TestPlayerType  {
    TEACHER,
    STUDENT
}

public class Tester {

    private Room CreateRoomWithPlayersInIt(List<Player> players) {

        Room room = new Room(5, new ArrayList<Item>(), null);

        if (players.isEmpty()) {
            return room ;
        }

        for (Player player : players) {
            room.AddPlayer(player);
            player.SetRoom(room);
        }

        return room;

    }

    public void Test1() {

        //Creating a players
        Room room1 = new Room( 5, new ArrayList<Item>(), null);
        Student student = new Student(room1);
        Room room2 = new Room( 5, new ArrayList<Item>(), null);
        Teacher teacher = new Teacher(room2);

        //Testing the interaction between student and teacher
        room2.Enter(student);

    }

}
