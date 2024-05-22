package Panels;
import Enums.EGameMode;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public static CardLayout layout;
    public static JPanel mainPanel;
    public static MenuPanel menuPanel;
    public static GamePanel singleGamePanel;
    public static GamePanel multiGamePanel;
    public static PausePanel pausePanel;

    //public static LoaderPanel loaderPanel = new LoaderPanel();
    public static JPanel previousPanel;

    public GameFrame(){
        layout = new CardLayout();
        mainPanel = new JPanel();
        menuPanel = new MenuPanel();
        singleGamePanel = new GamePanel(EGameMode.SINGLEPLAYER);
        multiGamePanel = new GamePanel(EGameMode.MULTIPLAYER);
        pausePanel = new PausePanel();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        /*singleGamePanel.AddGameConsole(new GameConsole(700,280));
        singleGamePanel.AddInventoryConsole(new InventoryConsole(400,70));

        multiGamePanel.AddGameConsole(new GameConsole(700,280));
        multiGamePanel.AddInventoryConsole(new InventoryConsole(400,70));
        multiGamePanel.AddGameConsole(new GameConsole(700,280));
        multiGamePanel.AddInventoryConsole(new InventoryConsole(400,70));*/

        mainPanel.setLayout(layout);

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(singleGamePanel, "SINGLE");
        mainPanel.add(multiGamePanel, "MULTI");
        mainPanel.add(pausePanel, "PAUSE");
        //mainPanel.add(loaderPanel, "LOADER");

        add(mainPanel);
        setTitle("Logarléc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
