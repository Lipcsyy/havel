package Panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final JButton mainMenuButton = new JButton("Return to main menu");
    private final ArrayList<GameConsole> gameConsoles = new ArrayList<GameConsole>();
    private final ArrayList<InventoryConsole> inventoryConsoles = new ArrayList<InventoryConsole>();

    public GamePanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);
        setBackground(Color.GRAY);

        SetButtons();
    }

    public void SetButtons() {
        mainMenuButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MENU" );
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

    public void AddButton() {
        add(mainMenuButton);
    }
}
