package Panels;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static CardLayout layout = new CardLayout();
    public static JPanel mainPanel = new JPanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static GamePanel singleGamePanel = new GamePanel();
    public static GamePanel multiGamePanel = new GamePanel();
    public static PausePanel pausePanel = new PausePanel();
    public static LoaderPanel loaderPanel = new LoaderPanel();
    public static JPanel previousPanel;
    public GameFrame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        singleGamePanel.AddGameConsole(new GameConsole(1024,576));
        singleGamePanel.AddInventoryConsole(new InventoryConsole(1024,100));

        multiGamePanel.AddGameConsole(new GameConsole(700,300));
        multiGamePanel.AddInventoryConsole(new InventoryConsole(400,100));
        multiGamePanel.AddGameConsole(new GameConsole(700,300));
        multiGamePanel.AddInventoryConsole(new InventoryConsole(400,100));

        mainPanel.setLayout(layout);

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(singleGamePanel, "SINGLE");
        mainPanel.add(multiGamePanel, "MULTI");
        mainPanel.add(pausePanel, "PAUSE");
        mainPanel.add(loaderPanel, "LOADER");

        add(mainPanel);
        setTitle("Logarléc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
