package models;

import controllers.UpdateWorker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.caprica.vlcj.player.TrackDescription;


public class ConcreteGuiModel extends GuiModel
{
    private static final String PLAYER_CONTROLLER = "PlayerController";
    private static final String MODULE_CONTROLLER = "ModuleController";

    public ConcreteGuiModel() 
    {
        setupFrame();
        setupMenu();
        setupCurrentMediaPanel();
        setupControlPanel();
    }
    
    void setupFrame()
    {
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);   
    }
    void setupMenu()
    { 
        menubar = new JMenuBar();
        fileMenu = new JMenu("File");
        openFileMenuItem = new JMenuItem("Open File");
        openFileMenuItem.setActionCommand(PLAYER_CONTROLLER + ".openFile");
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand(PLAYER_CONTROLLER + ".exit");
        fileMenu.add(openFileMenuItem);
        fileMenu.add(exitMenuItem);
        menubar.add(fileMenu);
        moduleMenu = new JMenu("Modules");
        addModuleMenuItem = new JMenuItem("Add Module");
        addModuleMenuItem.setActionCommand(MODULE_CONTROLLER + ".addModule");
        moduleMenu.add(addModuleMenuItem);
        menubar.add(moduleMenu); 
    }
    void setupCurrentMediaPanel()
    {   
        currentMediaPanel = new JPanel(new GridLayout(1,4,4,4));
        currentMediaModel = new DefaultListModel();
        currentMediaModel = populateList();
        currentMedia = new JList(currentMediaModel);
        currentMediaScrollPane = new JScrollPane(currentMedia);
        currentMediaPanel.add(currentMediaScrollPane);      
    }   
    void setupControlPanel()
    {
        controlPanel = new JPanel(new GridLayout(2,1));
        controlButtonPanel = new JPanel(new GridLayout(1,2));
        playButton = new JButton("Play");
        playButton.setActionCommand(PLAYER_CONTROLLER + ".play");
        stopButton = new JButton("Stop");
        stopButton.setActionCommand(PLAYER_CONTROLLER + ".stop");
        controlSliderPanel = new JPanel(new GridLayout(1,1));
        seekSlider = new JSlider(0, 1000, 0);

        controlButtonPanel.add(playButton);
        controlButtonPanel.add(stopButton);
        controlSliderPanel.add(seekSlider);
  
        controlPanel.add(controlButtonPanel);
        controlPanel.add(controlSliderPanel);   
    }

    private static DefaultListModel populateList()
    {
        DefaultListModel currentMedia = new DefaultListModel();
        currentMedia.addElement("Now Playing");
        currentMedia.addElement("Track: ");
        currentMedia.addElement("Artist: ");
        currentMedia.addElement("Album: ");
        currentMedia.addElement("Length: ");
        return currentMedia;
    } 
    
    @Override
    public GuiModel drawGui()
    {
        frame.setJMenuBar(menubar);
        frame.getContentPane().add(currentMediaPanel, BorderLayout.WEST);
        frame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        frame.setVisible(true); 
        
        return this;
    }
}
