package Panels;

import Buttons.MenuButton;
import Enums.EGameMode;
import GameManager.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoaderPanel extends JPanel {
    private final JButton mainMenuButton = new MenuButton("Return to main menu");
    public LoaderPanel(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);
        setBackground(Color.GRAY);

        SetButtons();

        add(mainMenuButton);
    }

    private void SetButtons() {
        mainMenuButton.addActionListener( event -> {
            GameFrame.layout.show(GameFrame.mainPanel, "MENU" );
        });
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoaderPanel extends JPanel {
    private JTable datFilesTable;
    private DefaultTableModel tableModel;
    private JPanel dataPanel;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    public static MenuButton deleteButton;
    public static MenuButton loadButton;
    public static MenuButton mainMenuButtonLoader = new MenuButton("Return to main menu");

    public LoaderPanel() {

        setPanelProperties();

        makeTable();
    }

    private void setPanelProperties() {

        // size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        // background
        this.setBackground(Color.GRAY);

    }

    private void makeTable() {

        // columns
        String[] columnNames = {"File Name", "Date of last modification"};

        // state of datamodel
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // initialize table with empty datamodel
        datFilesTable = new JTable(tableModel);
        datFilesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        datFilesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        datFilesTable.setFillsViewportHeight(true);
        datFilesTable.getTableHeader().setReorderingAllowed(false);

    }

    public void makeModel(){

        // remove everything in the panel
        removeAll();

        // initialize new table and datamodel, and filling up with actual data
        loadDatFiles();

        // gridbaglayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // new panel, including the scrollpane and the buttons
        makeDataPanel();

        // datapanel added
        add(dataPanel, gbc);
        // Buttons added
        gbc.gridy = 1;
        add(mainMenuButtonLoader, gbc);
    }

    private void loadDatFiles() {

        // delete the content of the model
        tableModel.setRowCount(0);

        // store the directory as a file
        File directory = new File("games");

        // add .dat files to the model
        if (directory.isDirectory()) {

            File[] datFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));

            if (datFiles != null) {
                for (File datFile : datFiles) {

                    long lastModified = datFile.lastModified();
                    String lastModifiedDate = formatLastModifiedDate(lastModified);
                    tableModel.addRow(new Object[]{datFile.getName(), lastModifiedDate});

                }
            }
        }
    }

    private void makeDataPanel() {

        dataPanel = new JPanel( new BorderLayout());

        scrollPane = new JScrollPane(datFilesTable);
        scrollPane.setPreferredSize(datFilesTable.getPreferredScrollableViewportSize());

        buttonPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(53, 60, 74));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        deleteButton = new MenuButton("Delete game");
        loadButton = new MenuButton("Load game");

        setButtons();

        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);

        dataPanel.add(scrollPane, BorderLayout.CENTER);
        dataPanel.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void setButtons() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = datFilesTable.getSelectedRow();

                if (selectedRow != -1) {

                    String fileName = (String) datFilesTable.getValueAt(selectedRow, 0);
                    tableModel.removeRow(selectedRow);
                    deleteFile(fileName);

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete!");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = datFilesTable.getSelectedRow();
                if (selectedRow != -1) {

                    String fileName = (String) datFilesTable.getValueAt(selectedRow, 0);

                    try {

                        GameManager loadedModel = readDatFile(fileName);

                        if(loadedModel.gameMode == EGameMode.SINGLEPLAYER){

                            GameFrame.previousPanel = GameFrame.singleGamePanel;
                            GameFrame.layout.show(GameFrame.mainPanel, "SINGLE" );
                            GameFrame.singleGamePanel.requestFocusInWindow(); // Request focus on the "SINGLE" panel
                            System.out.println("Single Player");
                            GameFrame.singleGamePanel.InitializeLoadedGame(loadedModel);

                        } else {

                            GameFrame.previousPanel = GameFrame.multiGamePanel;
                            GameFrame.layout.show(GameFrame.mainPanel, "MULTI" );
                            GameFrame.multiGamePanel.requestFocusInWindow(); // Request focus on the "MULTI" panel
                            GameFrame.multiGamePanel.InitializeLoadedGame(loadedModel);

                        }


                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to load!");
                }

            }
        });

        mainMenuButtonLoader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameFrame.layout.show(GameFrame.mainPanel, "MENU" );
            }
        });
    }

    private void deleteFile(String fileName) {

        // csináljunk egy fájl változót, ami a kívánt nevű fájlra mutat
        File fileToDelete = new File("./games/" + fileName);
        // töröljük
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        } else JOptionPane.showMessageDialog(null, "The file does not exist!");

    }

    private String formatLastModifiedDate(long lastModified) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(lastModified));

    }

    private GameManager readDatFile(String fileName) throws IOException, ClassNotFoundException {

        File fileToRead = new File("./games/" + fileName);

        InputStream is = new FileInputStream(fileToRead);
        ObjectInputStream ois = new ObjectInputStream( is );
        GameManager inputModel = (GameManager)ois.readObject();

        return inputModel;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
