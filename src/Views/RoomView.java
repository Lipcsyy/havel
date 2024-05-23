package Views;
import Controller.GameController;
import Enums.EDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Enums.ERooms;
import Room.*;

public class RoomView extends JPanel {
    //loadBackgroundImage(eRooms);
    public boolean hasTopDoor;
    public boolean hasBottomDoor;
    public boolean hasLeftDoor;
    public boolean hasRightDoor;

    transient private BufferedImage image;

    DoorView topDoor;
    DoorView bottomDoor;
    DoorView leftDoor;
    DoorView rightDoor;

    public ItemHolder itemHolder = new ItemHolder();
    public PlayerHolder playerHolder = new PlayerHolder();

    public RoomView(ERooms eRooms) {

        try {
            if (eRooms == ERooms.ROOM) {
                image = ImageIO.read(new File("./src/Images/room.png"));
            } else if (eRooms == ERooms.GASROOM) {
                image = ImageIO.read(new File("./src/Images/gasroom.png"));
            } else if (eRooms == ERooms.MAGICROOM) {
                image = ImageIO.read(new File("./src/Images/magicroom.png"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        this.hasTopDoor = false;
        this.hasBottomDoor = false;
        this.hasLeftDoor = false;
        this.hasRightDoor = false;

        setFocusable(true);
        setLayout(new BorderLayout());
        setOpaque(false);

        InitilizeDoors(60, 20);
    }

    /*
    public RoomView(ERooms eRooms, ItemHolder itemHolder, PlayerHolder playerHolder, boolean hasTopDoor, boolean hasBottomDoor, boolean hasLeftDoor, boolean hasRightDoor){

        this.hasTopDoor = hasTopDoor;
        this.hasBottomDoor = hasBottomDoor;
        this.hasLeftDoor = hasLeftDoor;
        this.hasRightDoor = hasRightDoor;

        try {
            if (eRooms == ERooms.ROOM) {
                image = ImageIO.read(new File("./src/Images/room.png"));
            } else if (eRooms == ERooms.GASROOM) {
                image = ImageIO.read(new File("./src/Images/gasroom.png"));
            } else if (eRooms == ERooms.MAGICROOM) {
                image = ImageIO.read(new File("./src/Images/magicroom.png"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        setFocusable(true);
        setLayout(new BorderLayout());
        setOpaque(false);

        InitilizeDoors(60, 20);

        this.itemHolder = itemHolder;
        this.playerHolder = playerHolder;

    }
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void Initialize(int width, int height, PlayerView studentView, ArrayList<ItemView> itemViews,
                           ArrayList<PlayerView> playerViews, Room room) {

        // Room dimensions
        int roomWidth = width; // need the parent to set the size
        int roomHeight = height;
        this.setPreferredSize(new Dimension(roomWidth, roomHeight));

        // Door dimensions
        int doorThickness = 20;
        int doorLength = 60;

        JPanel centerPanel = InitializeCenterPanel(roomWidth, roomHeight);

        System.out.println("Initialize (roomview)");


        // Add doors conditionally
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(doorLength, doorThickness));
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setPreferredSize(new Dimension(doorLength, doorThickness));
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(doorThickness, doorThickness));
        leftPanel.setOpaque(false);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(doorThickness, doorThickness));
        rightPanel.setOpaque(false);
        add(rightPanel, BorderLayout.EAST);

        // Add doors conditionally
        if (hasTopDoor) {
            if (topDoor == null) {
                //System.out.println("Adding top door");
                topDoor = new DoorView();
                topDoor.setPreferredSize(new Dimension(doorLength, doorThickness));
                topPanel.add(topDoor);
                add(topPanel, BorderLayout.NORTH);
            }
        }
        if (hasBottomDoor) {
            if (bottomDoor == null) {
                //System.out.println("Adding bottom door");
                bottomDoor = new DoorView();
                bottomDoor.setPreferredSize(new Dimension(doorLength, doorThickness));
                bottomPanel.add(bottomDoor);
                add(bottomPanel, BorderLayout.SOUTH);
            }
        }
        if (hasLeftDoor) {
            if (leftDoor == null) {
                //System.out.println("Adding left door");
                leftDoor = new DoorView();
                leftDoor.setPreferredSize(new Dimension(doorThickness, doorLength));
                leftPanel.add(leftDoor);
                add(leftPanel, BorderLayout.WEST);
            }
        }
        if (hasRightDoor) {
            if (rightDoor == null) {
                //System.out.println("Adding right door");
                rightDoor = new DoorView();
                rightDoor.setPreferredSize(new Dimension(doorThickness, doorLength));
                rightPanel.add(rightDoor);
                add(rightPanel, BorderLayout.EAST);
            }
        }

        // Add player and item holders to the center panel
        this.playerHolder = new PlayerHolder(studentView, playerViews, centerPanel.getWidth(), centerPanel.getHeight() / 2, room.GetCapacity());
        centerPanel.add(playerHolder);

        this.itemHolder = new ItemHolder(itemViews, room.GetItems().size());
        centerPanel.add(itemHolder);

        centerPanel.setOpaque(false);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void InitilizeDoors(int doorLength, int doorThickness) {

        //System.out.println("InitializeDoors");

        // Add doors conditionally
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(doorLength, doorThickness));
        topPanel.setOpaque(false);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setPreferredSize(new Dimension(doorLength, doorThickness));
        bottomPanel.setOpaque(false);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(doorThickness, doorThickness));
        leftPanel.setOpaque(false);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(doorThickness, doorThickness));
        rightPanel.setOpaque(false);
        add(rightPanel, BorderLayout.EAST);


        // Add doors conditionally
        if (hasTopDoor) {
            if (topDoor == null) {
                //System.out.println("Adding top door");
                topDoor = new DoorView();
                topDoor.setPreferredSize(new Dimension(doorLength, doorThickness));
                topPanel.add(topDoor);
                add(topPanel, BorderLayout.NORTH);

                topDoor.Render();
            }
        }
        if (hasBottomDoor) {
            if (bottomDoor == null) {
                //System.out.println("Adding bottom door");
                bottomDoor = new DoorView();
                bottomDoor.setPreferredSize(new Dimension(doorLength, doorThickness));
                bottomPanel.add(bottomDoor);
                add(bottomPanel, BorderLayout.SOUTH);

                bottomDoor.Render();
            }
        }
        if (hasLeftDoor) {
            if (leftDoor == null) {
                //System.out.println("Adding left door");
                leftDoor = new DoorView();
                leftDoor.setPreferredSize(new Dimension(doorThickness, doorLength));
                leftPanel.add(leftDoor);
                add(leftPanel, BorderLayout.WEST);

                leftDoor.Render();
            }
        }
        if (hasRightDoor) {
            if (rightDoor == null) {
                //System.out.println("Adding right door");
                rightDoor = new DoorView();
                rightDoor.setPreferredSize(new Dimension(doorThickness, doorLength));
                rightPanel.add(rightDoor);
                add(rightPanel, BorderLayout.EAST);

                rightDoor.Render();
            }
        }

    }

    private JPanel InitializeCenterPanel(int roomWidth, int roomHeight) {
        // Adding a center panel to hold the items and players
        int centerPanelWidth = roomWidth - 50;
        int centerPanelHeight = roomHeight - 50;

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setPreferredSize(new Dimension(centerPanelWidth, centerPanelHeight));
        centerPanel.setMaximumSize(new Dimension(centerPanelWidth, centerPanelHeight));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

        return centerPanel;
    }

    public void SetDoor(EDirection direction){
        if(direction == EDirection.WEST) hasLeftDoor = true;
        if(direction == EDirection.EAST) hasRightDoor = true;
        if(direction == EDirection.NORTH) hasTopDoor = true;
        if(direction == EDirection.SOUTH) hasBottomDoor = true;
    }

    public DoorView GetDoor(EDirection direction){
        if(direction == EDirection.WEST) return leftDoor;
        if(direction == EDirection.EAST) return rightDoor;
        if(direction == EDirection.NORTH) return topDoor;
        if (direction == EDirection.SOUTH) return bottomDoor;
        return topDoor;
    }

    public void Render(int roomWidth, int roomHeight, PlayerView studentView, ArrayList<ItemView> itemViews, ArrayList<PlayerView> playerViews, Room room) {

        //System.out.println("roomview.render");
        Initialize(roomWidth, roomHeight, studentView, itemViews, playerViews, room);


        if (hasTopDoor) {
            topDoor.Render();
            //System.out.println("Rendering top door");
        }
        if (hasBottomDoor) {
            bottomDoor.Render();
            //System.out.println("Rendering bottom door");
        }
        if (hasLeftDoor) {
            leftDoor.Render();
            //System.out.println("Rendering left door");
        }
        if (hasRightDoor) {
            rightDoor.Render();
            //System.out.println("Rendering right door");
        }

        revalidate();
        repaint();

        playerHolder.Render();
        itemHolder.Render();
    }

    public ItemHolder getItemHolder(){
        return itemHolder;
    }

    public PlayerHolder getPlayerHolder(){
        return playerHolder;
    }

    public void loadBackgroundImage(ERooms eRooms) {
        try {
            if (eRooms == ERooms.ROOM) {
                image = ImageIO.read(new File("./src/Images/room.png"));
            } else if (eRooms == ERooms.GASROOM) {
                image = ImageIO.read(new File("./src/Images/gasroom.png"));
            } else if (eRooms == ERooms.MAGICROOM) {
                image = ImageIO.read(new File("./src/Images/magicroom.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setBackgroundPicture( ERooms eRooms ) {
        try {
            if (eRooms == ERooms.ROOM) {
                image = ImageIO.read(new File("./src/Images/room.png"));
            } else if (eRooms == ERooms.GASROOM) {
                image = ImageIO.read(new File("./src/Images/gasroom.png"));
            } else if (eRooms == ERooms.MAGICROOM) {
                image = ImageIO.read(new File("./src/Images/magicroom.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }
}
