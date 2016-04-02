package model;

import javax.swing.*;

public abstract class GuiModel 
{    
    public JFrame frame;

    public JMenuBar menubar;
    public JMenu fileMenu;
    public JMenuItem openFileMenuItem;
    public JMenu moduleMenu;
    public JMenuItem addModuleMenuItem;
    public JMenuItem exitMenuItem;
    
    public JPanel currentMediaPanel;
    public JScrollPane currentMediaScrollPane;
    public JList currentMedia;
    
    public JPanel controlPanel;
    public JButton playButton;
    public JButton stopButton;
    public JSlider seekSlider;
 
    public abstract void drawGui();
}
