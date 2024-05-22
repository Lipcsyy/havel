package Panels;

import Controller.GameController;
import Enums.EGameMode;
import GameManager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<GameConsole> gameConsoles = new ArrayList<GameConsole>();
    private ArrayList<InventoryConsole> inventoryConsoles = new ArrayList<InventoryConsole>();
    private MazeDisplay mazeDisplay;
    private JPanel mazeDisplayArea;
    private JPanel containerPanel;
    private GameController gameController;
    private Image backgroundImage;
    public GridBagConstraints gbc = new GridBagConstraints();

    public GamePanel(EGameMode gameMode){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true); // Enable keyboard focus for the panel
        requestFocusInWindow(); // Request keyboard focus when the panel is shown

        backgroundImage = new ImageIcon("./src/Images/background.jpg").getImage();

        SetKeyListener();

        // Initialize containerPanel and set its preferred size to one third of the screen width
        containerPanel = new JPanel();
        containerPanel.setPreferredSize(new Dimension((int)Math.round((float)SCREEN_WIDTH * (2.0f / 3.0f)), SCREEN_HEIGHT));
        containerPanel.setOpaque(false);
        containerPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        if(gameMode == EGameMode.SINGLEPLAYER){
            AddGameConsole(new GameConsole(700,280));
            AddInventoryConsole(new InventoryConsole(400,70));
        }
        else if(gameMode == EGameMode.MULTIPLAYER){
            AddGameConsole(new GameConsole(700,280));
            AddInventoryConsole(new InventoryConsole(400,70));
            AddGameConsole(new GameConsole(700,280));
            AddInventoryConsole(new InventoryConsole(400,70));
        }

        // Initialize mazeDisplayArea and set its preferred size to one third of the screen width
        mazeDisplayArea = new JPanel();
        mazeDisplayArea.setPreferredSize(new Dimension((int)Math.round((float) SCREEN_WIDTH / 3), SCREEN_HEIGHT));
        mazeDisplayArea.setOpaque(false);
        mazeDisplayArea.setLayout(new GridBagLayout());

        // Set the layout of the GamePanel to BorderLayout
        setLayout(new BorderLayout());

        add(containerPanel, BorderLayout.WEST);
        add(mazeDisplayArea, BorderLayout.EAST);

    }


    private void SetKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the pressed key is ESC (key code 27)
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Handle ESC key press here
                    GameFrame.layout.show(GameFrame.mainPanel, "PAUSE");
                }
            }
        });
    }

    public void AddGameConsole(GameConsole gameConsole){
        gameConsoles.add(gameConsole);
        containerPanel.add(gameConsoles.get(gameConsoles.size()-1), gbc); // Add the console to containerPanel
        gbc.gridy++;
    }

    public void AddInventoryConsole(InventoryConsole inventoryConsole){
        inventoryConsoles.add(inventoryConsole);
        containerPanel.add(inventoryConsoles.get(inventoryConsoles.size()-1), gbc); // Add the console to containerPanel
        gbc.gridy++;
    }

    public void InitializeGame(EGameMode gameMode) {

        mazeDisplayArea.removeAll();

        gameController = new GameController(gameMode, this);
        mazeDisplay = new MazeDisplay(gameController.gameManager.map, gameController);

        GridBagConstraints gbcMaze = new GridBagConstraints();
        gbcMaze.gridx = 0;
        gbcMaze.gridy = 0;
        gbcMaze.weightx = 1.0;
        gbcMaze.weighty = 1.0;
        gbcMaze.anchor = GridBagConstraints.CENTER;
        gbcMaze.fill = GridBagConstraints.NONE;

        mazeDisplayArea.add(mazeDisplay, gbcMaze); // Center the mazeDisplay
        gameController.StartGame();
    }

    public void InitializeLoadedGame(GameManager gameManager) {

        gameController = new GameController(gameManager, this);
        mazeDisplay = new MazeDisplay(gameController.gameManager.map, gameController);
        mazeDisplayArea.add(mazeDisplay);
        gameController.StartGame();
    }

    public ArrayList<GameConsole> GetGameConsoles() {
        return gameConsoles;
    }

    //get inventoryConsoles
    public ArrayList<InventoryConsole> GetInventoryConsoles(){
        return inventoryConsoles;
    }

    public void Render() {
        mazeDisplay.Render();
        for( InventoryConsole inventoryConsole : inventoryConsoles ) {
            inventoryConsole.Render();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}