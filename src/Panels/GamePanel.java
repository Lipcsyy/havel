package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private ArrayList<GameConsole> gameConsoles = new ArrayList<GameConsole>();
    private ArrayList<InventoryConsole> inventoryConsoles = new ArrayList<InventoryConsole>();

    public GamePanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true); // Enable keyboard focus for the panel
        requestFocusInWindow(); // Request keyboard focus when the panel is shown
        setBackground(Color.GRAY);

        SetKeyListener();

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
        add(gameConsoles.get(gameConsoles.size()-1));
    }

    public void AddInventoryConsole(InventoryConsole inventoryConsole){
        inventoryConsoles.add(inventoryConsole);
        add(inventoryConsoles.get(inventoryConsoles.size()-1));
    }
}
