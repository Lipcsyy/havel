package Views;

import javax.swing.*;
import java.awt.*;


public class RoomView extends JPanel {

    public boolean hasTopDoor;
    public boolean hasBottomDoor;
    public boolean hasLeftDoor;
    public boolean hasRightDoor;

    DoorView topDoor;
    DoorView bottomDoor;
    DoorView leftDoor;
    DoorView rightDoor;
    public RoomView(boolean hasTopDoor, boolean hasBottomDoor, boolean hasLeftDoor, boolean hasRightDoor) {
        this.hasTopDoor = hasTopDoor;
        this.hasBottomDoor = hasBottomDoor;
        this.hasLeftDoor = hasLeftDoor;
        this.hasRightDoor = hasRightDoor;

        //setPreferredSize(new Dimension(400, 400));
        setBackground(Color.lightGray);
        setLayout(null); // No layout manager for absolute positioning
    }
    public void Initialize(int width, int height) {
        // Room dimensions
        int roomWidth = width; //need the parent to set the size
        int roomHeight = height;
        this.setPreferredSize(new Dimension(roomWidth, roomHeight));

        // Door dimensions
        int doorThickness = 20;
        int doorLength = 60;

        // Add doors conditionally
        if (hasTopDoor) {
            topDoor = new DoorView();
            topDoor.setBounds((roomWidth - doorLength) / 2, 0, doorLength, doorThickness);
            topDoor.setSize(doorLength, doorThickness);
            add(topDoor);
        }

        if (hasBottomDoor) {
            bottomDoor = new DoorView();
            bottomDoor.setBounds((roomWidth - doorLength) / 2, roomHeight - doorThickness, doorLength, doorThickness);
            bottomDoor.setSize(doorLength, doorThickness);
            add(bottomDoor);
        }

        if (hasLeftDoor) {
            leftDoor = new DoorView();
            leftDoor.setBounds(0, (roomHeight - doorLength) / 2, doorThickness, doorLength);
            leftDoor.setSize(doorThickness, doorLength);
            add(leftDoor);
        }

        if (hasRightDoor) {
            rightDoor = new DoorView();
            rightDoor.setBounds(roomWidth - doorThickness, (roomHeight - doorLength) / 2, doorThickness, doorLength);
            rightDoor.setSize(doorThickness, doorLength);
            add(rightDoor);
        }
    }
}
