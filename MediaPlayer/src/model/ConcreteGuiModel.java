package model;

import java.io.File;
import javax.swing.*;

public class ConcreteGuiModel extends GuiModel {
    public ConcreteGuiModel() 
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
        
        displayCurrentMedia();
        
        controlPanel = new JPanel();
        playButton = new JButton();
        stopButton = new JButton();
        seekSlider = new JSlider();
        controlPanel.add(playButton);
        controlPanel.add(stopButton);
        controlPanel.add(seekSlider);
    }
    
    void displayCurrentMedia()
    {   
        currentMediaPanel = new JPanel();
        currentMedia = generateCurrentList();
        currentMediaScrollPane = new JScrollPane(currentMedia);
        currentMediaPanel.add(currentMediaScrollPane);
        
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
    
}
