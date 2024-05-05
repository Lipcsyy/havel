package Panels;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static JPanel mainPanel = new JPanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static GamePanel gamePanel = new GamePanel();
    public static LoaderPanel loaderPanel = new LoaderPanel();
    public GameFrame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        mainPanel.setLayout(new CardLayout());
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(loaderPanel, "LOADER");

        add(mainPanel);
        setTitle("Logarléc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
