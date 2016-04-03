package models;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class ConcreteGuiModel extends GuiModel {
    private static final String CONTROLLER = "PlayerController";
    
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
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(openFileMenuItem);
        fileMenu.add(exitMenuItem);
        menubar.add(fileMenu);
        moduleMenu = new JMenu("Modules");
        addModuleMenuItem = new JMenuItem("Add Module");
        moduleMenu.add(addModuleMenuItem);
        menubar.add(moduleMenu); 
    }
    void setupCurrentMediaPanel()
    {   
        currentMediaPanel = new JPanel(new GridLayout(1,4,4,4));
        currentMedia = generateCurrentList();
        currentMediaScrollPane = new JScrollPane(currentMedia);
        currentMediaPanel.add(currentMediaScrollPane);      
    }   
    void setupControlPanel()
    {
        controlPanel = new JPanel(new GridLayout(2,1));
        controlButtonPanel = new JPanel(new GridLayout(1,2));
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        playButton.setActionCommand(CONTROLLER + ".play");
        stopButton.setActionCommand(CONTROLLER + ".stop"); 
        controlButtonPanel.add(playButton);
        controlButtonPanel.add(stopButton);
        controlSliderPanel = new JPanel(new GridLayout(1,1));
        seekSlider = new JSlider();
        controlSliderPanel.add(seekSlider);
        controlPanel.add(controlButtonPanel);
        controlPanel.add(controlSliderPanel);
        
    }
    
    JList generateCurrentList()
    {
        DefaultListModel currentMediaModel = new DefaultListModel();
        currentMediaModel.addElement("Now Playing");
        currentMediaModel.addElement("Track: ");
        currentMediaModel.addElement("Artist: ");
        currentMediaModel.addElement("Album: ");
        currentMediaModel.addElement("Length: ");
        return new JList(currentMediaModel);
    }
    
    void setFileInfo(File file)
    {
        
    }
    
    @Override
    public GuiModel drawGui()
    {
        frame.getContentPane().add(menubar, BorderLayout.NORTH);
        frame.getContentPane().add(currentMediaPanel, BorderLayout.WEST);
        frame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        frame.setVisible(true); 
        
        return this;
    }
}
