package Views;
import Controller.GameController;
import Enums.EDirection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class RoomView extends JPanel {

    public boolean hasTopDoor;
    public boolean hasBottomDoor;
    public boolean hasLeftDoor;
    public boolean hasRightDoor;

    DoorView topDoor;
    DoorView bottomDoor;
    DoorView leftDoor;
    DoorView rightDoor;

    public ItemHolder itemHolder;
    public PlayerHolder playerHolder;

    public RoomView(boolean hasTopDoor, boolean hasBottomDoor, boolean hasLeftDoor, boolean hasRightDoor) {

        this.hasTopDoor = hasTopDoor;
        this.hasBottomDoor = hasBottomDoor;
        this.hasLeftDoor = hasLeftDoor;
        this.hasRightDoor = hasRightDoor;

        setBackground(Color.lightGray);
        setFocusable(true);
        setLayout(null); // No layout manager for absolute positioning

    }

    public void Initialize(int width, int height, PlayerView studentView, ArrayList<ItemView> itemViews,
                           ArrayList<PlayerView> playerViews) {

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

        add(new ItemHolder(itemViews, roomWidth, roomHeight));
        add(new PlayerHolder(studentView, playerViews, roomWidth, roomHeight));
    }

    public DoorView GetDoor(EDirection direction){
        if(direction == EDirection.WEST) return leftDoor;
        if(direction == EDirection.EAST) return rightDoor;
        if(direction == EDirection.NORTH) return topDoor;
        if (direction == EDirection.SOUTH) return bottomDoor;
        return topDoor;
    }


    public void Render(int roomWidth, int roomHeight, PlayerView studentView, ArrayList<ItemView> itemViews,
                       ArrayList<PlayerView> playerViews) {

        Initialize(roomWidth, roomHeight, studentView, itemViews, playerViews);

        if (hasTopDoor) {
            topDoor.Render();
            System.out.println("Rendering top door");
        }
        if (hasBottomDoor) {
            bottomDoor.Render();
            System.out.println("Rendering bottom door");
        }
        if (hasLeftDoor) {
            leftDoor.Render();
            System.out.println("Rendering left door");
        }
        if (hasRightDoor) {
            rightDoor.Render();
            System.out.println("Rendering right door");
        }
    }
}
