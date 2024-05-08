package Panels;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public static CardLayout layout = new CardLayout();
    public static JPanel mainPanel = new JPanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static GamePanel singleGamePanel = new GamePanel();
    public static GamePanel multiGamePanel = new GamePanel();
    public static LoaderPanel loaderPanel = new LoaderPanel();
    public GameFrame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        mainPanel.setLayout(layout);
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(singleGamePanel, "SINGLE");
        mainPanel.add(multiGamePanel, "MULTI");
        mainPanel.add(loaderPanel, "LOADER");

        add(mainPanel);
        setTitle("Logarléc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
