package Map;

import java.util.*;
import java.util.stream.Collectors;

import Enums.EDirection;
import Enums.ERooms;
import Room.*;
import GameManager.GameManager;
import Views.RoomView;

public class GameMap {
    private final int width;
    private final int height;
    private HashMap<Room, Set<Room>> adjacencyList = new HashMap<>();
    private final Random rand = new Random();

    private static final int N = 1, S = 2, E = 4, W = 8;
    private static final int[] DIRECTIONS = {N, S, E, W};

    private GameManager gameManager;

    public GameMap(int width, int height, GameManager gameManager){
        this.gameManager = gameManager;
        this.width = width;
        this.height = height;

        Random random = new Random();

//        //3 Rooms for debugging
//        Room cell1 = new Room((new Random()).nextInt(4, 5), gameManager);
//        gameManager.GetGameController().SetRoomView(cell1, new RoomView(ERooms.ROOM, false, false, false, false));
//        //cell1.SetTurnsLeftForEffect( Integer.MAX_VALUE );
//        cell1.setCoordinates(0, 0);
//        adjacencyList.put(cell1, new HashSet<>());
//
//        Room cell2 = new Room((new Random()).nextInt(4, 5), gameManager);
//        RoomView roomView = new RoomView(ERooms.ROOM, false, false, false, false);
//        gameManager.GetGameController().SetRoomView(cell2, roomView );
//        cell2.setCoordinates(1, 0);
//        adjacencyList.put(cell2, new HashSet<>());
//
//
//        Room cell3 = new Room((new Random()).nextInt(4, 5), gameManager);
//        gameManager.GetGameController().SetRoomView(cell3, new RoomView(ERooms.ROOM, false, false, false, false));
//        cell3.setCoordinates(2, 0);
//        adjacencyList.put(cell3, new HashSet<>());




        // Initialize the graph with all cells having no neighbors
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                double randomNumber = random.nextDouble(0,1);

                if( randomNumber < 0.0){
                    Room cell = new Room((new Random()).nextInt(4, 5), gameManager);
                    gameManager.GetGameController().SetRoomView(cell, new RoomView(ERooms.GASROOM));
                    cell.SetTurnsLeftForEffect( Integer.MAX_VALUE );
                    cell.setCoordinates(x, y);
                    adjacencyList.put(cell, new HashSet<>());
                } else if ( randomNumber < 0.0){
                    MagicRoom cell = new MagicRoom((new Random()).nextInt(4, 5), gameManager);
                    gameManager.GetGameController().SetRoomView(cell, new RoomView(ERooms.MAGICROOM));
                    cell.setCoordinates(x, y);
                    adjacencyList.put(cell, new HashSet<>());
                } else {
                    Room cell = new Room((new Random()).nextInt(4, 5), gameManager);
                    gameManager.GetGameController().SetRoomView(cell, new RoomView(ERooms.ROOM));
                    cell.setCoordinates(x, y);
                    adjacencyList.put(cell, new HashSet<>());
                }
            }
        }


    }

    public void generateMaze() {
        // Start with a random cell
        Room startCell = getRandomCell();
        startCell.in = true;
        addFrontier(startCell);

        while (!allCellsIn()) {

            Room frontierCell = removeRandomFrontierCell();
            List<Room> inNeighbors = getInNeighbors(frontierCell);

            if (!inNeighbors.isEmpty()) {
                Room neighbor = inNeighbors.get(rand.nextInt(inNeighbors.size()));
                connectCells(neighbor, frontierCell);
                addFrontier(frontierCell);
            }
        }
    }

    public Room getRandomCell() {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);
        return findCell(x, y);
    }

    private void addFrontier(Room cell) {
        int x = cell.x;
        int y = cell.y;
        for (int direction : DIRECTIONS) {
            int nx = x, ny = y;
            switch (direction) {
                case N: ny--; break;
                case S: ny++; break;
                case E: nx++; break;
                case W: nx--; break;
            }
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                Room neighbor = findCell(nx, ny);
                if (!neighbor.in && !adjacencyList.get(neighbor).contains(cell)) {
                    adjacencyList.get(neighbor).add(cell);
                    EDirection doorDirection = EDirection.NORTH; // Default value
                    if (direction == S) {
                        doorDirection = EDirection.NORTH;
                    } else if (direction == N) {
                        doorDirection = EDirection.SOUTH;
                    } else if (direction == W) {
                        doorDirection = EDirection.EAST;
                    } else if (direction == E) {
                        doorDirection = EDirection.WEST;
                    }
                    gameManager.GetGameController().GetRoomViewByRoom(neighbor).SetDoor(doorDirection);
                }
            }
        }
    }

    private List<Room> getInNeighbors(Room cell) {
        return adjacencyList.get(cell).stream().filter(c -> c.in).collect(Collectors.toList());
    }

    private void connectCells(Room a, Room b) {
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
        a.in = true;
        b.in = true;
    }

    private boolean allCellsIn() {
        return adjacencyList.keySet().stream().allMatch(c -> c.in);
    }

    private Room removeRandomFrontierCell() {
        List<Room> frontierCells = new ArrayList<>(adjacencyList.keySet().stream().filter(c -> !c.in && !adjacencyList.get(c).isEmpty()).toList());
        return frontierCells.remove(rand.nextInt(frontierCells.size()));
    }

    public Room findCell(int x, int y) {
        for (var cell : adjacencyList.keySet()) {
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null; // Should never happen
    }

    public int getWidth() {
        return width;
    }

    public HashMap<Room, Set<Room>> getAdjacencyList() {
        return adjacencyList;
    }

    public void displayMaze() {
        System.out.print(" ");
        for (int i = 0; i < width * 2 - 1; i++) {
            System.out.print("_");
        }
        System.out.println();

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                Room cell = findCell(x, y);
                int finalY = y;
                System.out.print(adjacencyList.get(cell).stream().anyMatch(c -> c.y == finalY + 1) ? " " : "_");
                if (x < width - 1) {
                    Room rightCell = findCell(x + 1, y);
                    System.out.print(adjacencyList.get(cell).contains(rightCell) ? " " : "|");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

    public Room GetRandomNeighbour(Room room) {
        List<Room> neighbours = new ArrayList<>(adjacencyList.get(room));
        if (neighbours.isEmpty()) {
            return null; // No neighbours available
        }
        int randomNumber = new Random().nextInt(neighbours.size());
        return neighbours.get(randomNumber);
    }

    public Room GetRoomByIndex(int index){
        return (Room)adjacencyList.keySet().toArray()[index];
    }

    public Room RoomGetRoomByCoordinates(int x, int y){
        return findCell(x, y);
    }
}