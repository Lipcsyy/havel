package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutExample extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel panel1, panel2;
    private JButton switchButton;

    public CardLayoutExample() {
        setTitle("CardLayout Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panels
        panel1 = new JPanel();
        panel1.setBackground(Color.BLUE);
        panel2 = new JPanel();
        panel2.setBackground(new Color(255, 0, 0, 128)); // Semi-transparent red panel

        // Create switch button
        switchButton = new JButton("Switch Panels");
        switchButton.addActionListener(this);

        // Create card panel and set layout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Add panels to card panel
        cardPanel.add(panel1, "Panel 1");
        cardPanel.add(panel2, "Panel 2");

        // Add components to content pane
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(switchButton, BorderLayout.SOUTH);

        setSize(300, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == switchButton) {
            // Switch to the next panel
            cardLayout.next(cardPanel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CardLayoutExample());
    }
}